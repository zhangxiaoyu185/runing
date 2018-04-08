package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.entity.*;
import com.xiaoyu.lingdian.entity.weixin.*;
import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.wx.WxPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.Date;

@Controller
@RequestMapping(value="/pay")
@Api(value = "pay", description = "支付回调")
public class PayController extends BaseController {

	/**
	 * 用户表
	 */
	@Autowired
	private CoreUserService coreUserService;

	/**
	 * 充值记录表
	 */
	@Autowired
	private CoreRechargeRecordService coreRechargeRecordService;

	/**
	 * 优惠券表
	 */
	@Autowired
	private CoreCouponService coreCouponService;

	/**
	 * 佣金记录表
	 */
	@Autowired
	private CoreCommissionRecordService coreCommissionRecordService;

	/**
	 * 系统设置表
	 */
	@Autowired
	private CoreSystemSetService coreSystemSetService;

	/**
	 * 回调函数
	 * 
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "支付回调", httpMethod = "POST", notes = "支付回调")
	@RequestMapping(value = "/notify", method = RequestMethod.POST)
	public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("小程序支付回调数据开始");
		String inputLine;
		String notityXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("接收到的报文：" + notityXml);
		PayResult wpr = WxPayUtil.parseXmlToList(notityXml);
		if (StringUtil.isEmpty(wpr.getOutTradeNo())) {
			return;
		}
		String outTradeNo = wpr.getOutTradeNo();
		if (wpr.getOutTradeNo().length() > 16) {
			outTradeNo = wpr.getOutTradeNo().substring(0, 16);
		}
		//查找充值记录
		CoreRechargeRecord coreRechargeRecord = new CoreRechargeRecord();
		coreRechargeRecord.setCrrerUuid(outTradeNo);
		coreRechargeRecord = coreRechargeRecordService.getCoreRechargeRecord(coreRechargeRecord);
		if (null == coreRechargeRecord) {
			resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
			return;
		}
		if (coreRechargeRecord.getCrrerStatus() != 2) { //不是在支付环节,防止重复回调
			resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
			return;
		}
		if ("SUCCESS".equals(wpr.getResultCode())) { // 支付成功
			CoreUser coreUser = new CoreUser();
			coreUser.setCrusrUuid(coreRechargeRecord.getCrrerUser());
			coreUser = coreUserService.getCoreUser(coreUser);
			if (null == coreUser) {
				logger.info("用户不存在！");
				resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
				return;
			}
			//更新充值记录状态为成功
			coreRechargeRecord.setCrrerStatus(1);
			this.coreRechargeRecordService.updateCoreRechargeRecord(coreRechargeRecord);
			//更新使用的优惠券状态
			if(!StringUtil.isEmpty(coreRechargeRecord.getCrrerUseCoupon())) {
				String[] uuids = coreRechargeRecord.getCrrerUseCoupon().split("\\|");
				for (int i = 0; i < uuids.length; i++) {
					CoreCoupon coreCoupon = this.coreCouponService.getCoreCouponByMyUse(uuids[i], coreRechargeRecord.getCrrerUser());
					if(null != coreCoupon) {
						coreCoupon.setCrcpnUseStatus(1);
						this.coreCouponService.updateCoreCoupon(coreCoupon);
					}
				}
			}
			//添加获得的优惠券
			CoreSystemSet coreSystemSet = new CoreSystemSet();
			coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
			coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
			if (null != coreSystemSet && null != coreSystemSet.getCrsstCoupon()) {
				CoreCoupon coreCoupon = new CoreCoupon();
				String uuid = RandomUtil.generateString(16);
				coreCoupon.setCrcpnUuid(uuid);
				coreCoupon.setCrcpnUser(coreUser.getCrusrInviter());
				coreCoupon.setCrcpnUseStatus(2);
				coreCoupon.setCrcpnReduce((int) Math.rint(coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstCoupon()));
				coreCoupon.setCrcpnCdate(new Date());
				coreCoupon.setCrcpnSendStatus(1);
				Date date = new Date();
				coreCoupon.setCrcpnStart(date);
				coreCoupon.setCrcpnEnd(DateUtil.addDay(date, 30));
				coreCouponService.insertCoreCoupon(coreCoupon);
			}
			//添加佣金纪录
			//获取上级
			if(!StringUtil.isEmpty(coreUser.getCrusrInviter())) {
				CoreUser newCoreUser = new CoreUser();
				newCoreUser.setCrusrUuid(coreUser.getCrusrInviter());
				newCoreUser = coreUserService.getCoreUser(newCoreUser);
				if (null != newCoreUser){
					if (null != coreSystemSet && null != coreSystemSet.getCrsstFirstIncome()) {
						Date date = new Date();
						CoreCommissionRecord coreCommissionRecord = new CoreCommissionRecord();
						String uuid = RandomUtil.generateString(16);
						coreCommissionRecord.setCrcrdUuid(uuid);
						coreCommissionRecord.setCrcrdUser(coreUser.getCrusrUuid());
						coreCommissionRecord.setCrcrdFee(coreRechargeRecord.getCrrerPayFee());
						coreCommissionRecord.setCrcrdDate(date);
						coreCommissionRecord.setCrcrdBeUser(coreUser.getCrusrInviter());
						coreCommissionRecord.setCrcrdBeFee(coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstFirstIncome());
						coreCommissionRecord.setCrcrdRatio(coreSystemSet.getCrsstFirstIncome());
						coreCommissionRecord.setCrcrdLevel(1);
						coreCommissionRecordService.insertCoreCommissionRecord(coreCommissionRecord);
						//更新获得佣金的用户的金额
						this.coreUserService.updateCoreUserByIncome(coreUser.getCrusrInviter(), coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstFirstIncome(), coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstFirstIncome()
						,coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstFirstIncome(), 0);
					}
					if(!StringUtil.isEmpty(newCoreUser.getCrusrInviter())) {
						CoreUser twoCoreUser = new CoreUser();
						twoCoreUser.setCrusrUuid(newCoreUser.getCrusrInviter());
						twoCoreUser = coreUserService.getCoreUser(twoCoreUser);
						if (null != twoCoreUser){
							if (null != coreSystemSet && null != coreSystemSet.getCrsstSecondIncome()) {
								Date date = new Date();
								CoreCommissionRecord coreCommissionRecord = new CoreCommissionRecord();
								String uuid = RandomUtil.generateString(16);
								coreCommissionRecord.setCrcrdUuid(uuid);
								coreCommissionRecord.setCrcrdUser(coreUser.getCrusrUuid());
								coreCommissionRecord.setCrcrdFee(coreRechargeRecord.getCrrerPayFee());
								coreCommissionRecord.setCrcrdDate(date);
								coreCommissionRecord.setCrcrdBeUser(newCoreUser.getCrusrInviter());
								coreCommissionRecord.setCrcrdBeFee(coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstSecondIncome());
								coreCommissionRecord.setCrcrdRatio(coreSystemSet.getCrsstSecondIncome());
								coreCommissionRecord.setCrcrdLevel(2);
								coreCommissionRecordService.insertCoreCommissionRecord(coreCommissionRecord);
								//更新获得佣金的用户的金额
								this.coreUserService.updateCoreUserByIncome(newCoreUser.getCrusrInviter(), coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstSecondIncome(), coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstSecondIncome()
										,coreRechargeRecord.getCrrerPayFee() * coreSystemSet.getCrsstSecondIncome(), 0);
							}
						}
					}
				}
			}
			resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
		} else {
			//更新充值记录状态为成功
			coreRechargeRecord.setCrrerStatus(2);
			this.coreRechargeRecordService.updateCoreRechargeRecord(coreRechargeRecord);
			resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[fail]]></return_msg></xml> ";
		}
		logger.info("小程序支付回调数据结束");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
		return;
	}

}