package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.entity.*;
import com.xiaoyu.lingdian.entity.weixin.Constant;
import com.xiaoyu.lingdian.entity.weixin.PayOrder;
import com.xiaoyu.lingdian.entity.weixin.PayPackage;
import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.wx.WxPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreRechargeRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 充值记录表
 */
@Controller
@RequestMapping(value = "/coreRechargeRecord")
@Api(value = "coreAccount", description = "充值记录相关操作")
public class CoreRechargeRecordController extends BaseController {

    /**
     * 充值记录表
     */
    @Autowired
    private CoreRechargeRecordService coreRechargeRecordService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 小程序表
     */
    @Autowired
    private CoreWechatService coreWechatService;

    /**
     * 优惠券表
     */
    @Autowired
    private CoreCouponService coreCouponService;

    /**
     * 油卡表
     */
    @Autowired
    private BusiOilService busiOilService;

    /**
     * 充值去支付获取请求预支付id报文
     *
     * @param crrerUser        充值用户
     * @param crrerFee         充值原金额
     * @param crrerOil         油卡卡号
     * @param crrerInvoice     是否带发票（1是，2否）
     * @param crrerUseCoupon   使用的优惠券合集（|拼接）
     * @param crrerPayType     支付方式（1微信支付，2银联支付）
     * @return
     */
    @ApiOperation(value = "充值去支付获取请求预支付id报文", httpMethod = "POST", notes = "充值去支付获取请求预支付id报文")
    @RequestMapping(value = "/get/package", method = RequestMethod.POST)
    public void getPackageGoPay(
            @ApiParam(value = "充值用户", required = true) @RequestParam(value = "crrerUser", required = true) String crrerUser,
            @ApiParam(value = "充值原金额", required = true) @RequestParam(value = "crrerFee", required = true) Double crrerFee,
            @ApiParam(value = "油卡卡号", required = true) @RequestParam(value = "crrerOil", required = true) String crrerOil,
            @ApiParam(value = "是否带发票（1是，2否）", required = true) @RequestParam(value = "crrerInvoice", required = true) Integer crrerInvoice,
            @ApiParam(value = "使用的优惠券合集（|拼接）", required = false) @RequestParam(value = "crrerUseCoupon", required = false) String crrerUseCoupon,
            @ApiParam(value = "支付方式（1微信支付，2银联支付）", required = true) @RequestParam(value = "crrerPayType", required = true) Integer crrerPayType,
            HttpServletResponse response) {
        logger.info("[CoreRechargeRecordController]:begin getPackageGoPay");
        if (crrerFee <= 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "充值金额必须为正数！"), response);
            logger.info("[CoreRechargeRecordController]:end getPackageGoPay.");
            return;
        }

        CoreWechat coreWechat = new CoreWechat();
        coreWechat.setCrwctUuid(BaseConstant.WE_CHAT_UUID);
        coreWechat = coreWechatService.getCoreWechat(coreWechat);
        if (null == coreWechat) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "小程序未配置！"), response);
            logger.info("[CoreRechargeRecordController]:end getPackageGoPay.");
            return;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crrerUser);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户不存在！"), response);
            logger.info("[CoreRechargeRecordController]:end getPackageGoPay.");
            return;
        }

        Double crrerPayFee = crrerFee;
        String crrerUseCouponNew = "";
        if(!StringUtil.isEmpty(crrerUseCoupon)) {
            String[] uuids = crrerUseCoupon.split("\\|");
            for (int i = 0; i < uuids.length; i++) {
                CoreCoupon coreCoupon = this.coreCouponService.getCoreCouponByMyUse(uuids[i], crrerUser);
                if(null != coreCoupon) {
                    crrerPayFee = crrerPayFee - coreCoupon.getCrcpnReduce();
                    crrerUseCouponNew += uuids[i] + "|";
                }
            }
        }
        if(crrerPayFee <= 0) {
            crrerPayFee = 0.0;
            //插入充值记录表
            CoreRechargeRecord coreRechargeRecord = new CoreRechargeRecord();
            String uuid = RandomUtil.generateString(16);
            coreRechargeRecord.setCrrerUuid(uuid);
            coreRechargeRecord.setCrrerUser(crrerUser);
            coreRechargeRecord.setCrrerStatus(1);
            coreRechargeRecord.setCrrerFee(crrerFee);
            coreRechargeRecord.setCrrerPayFee(crrerPayFee);
            coreRechargeRecord.setCrrerDate(new Date());
            coreRechargeRecord.setCrrerOilCode(crrerOil);
            coreRechargeRecord.setCrrerInvoice(crrerInvoice);
            coreRechargeRecord.setCrrerUseCoupon(crrerUseCouponNew);
            coreRechargeRecord.setCrrerPayType(crrerPayType);
            coreRechargeRecord.setCrrerOrder(RandomUtil.generateString(16));
            coreRechargeRecord.setCrrerAgent("1");
            coreRechargeRecordService.insertCoreRechargeRecord(coreRechargeRecord);

            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "支付0元，支付成功"), response);
            logger.info("[CoreRechargeRecordController]:end getPackageGoPay");
        } else {
            //插入充值记录表
            CoreRechargeRecord coreRechargeRecord = new CoreRechargeRecord();
            String uuid = RandomUtil.generateString(16);
            coreRechargeRecord.setCrrerUuid(uuid);
            coreRechargeRecord.setCrrerUser(crrerUser);
            coreRechargeRecord.setCrrerStatus(2);
            coreRechargeRecord.setCrrerFee(crrerFee);
            coreRechargeRecord.setCrrerPayFee(crrerPayFee);
            coreRechargeRecord.setCrrerDate(new Date());
            coreRechargeRecord.setCrrerOilCode(crrerOil);
            coreRechargeRecord.setCrrerInvoice(crrerInvoice);
            coreRechargeRecord.setCrrerUseCoupon(crrerUseCoupon);
            coreRechargeRecord.setCrrerPayType(crrerPayType);
            coreRechargeRecord.setCrrerOrder(RandomUtil.generateString(16));
            coreRechargeRecord.setCrrerAgent("1");
            coreRechargeRecordService.insertCoreRechargeRecord(coreRechargeRecord);

            PayOrder payOrder = new PayOrder(coreWechat.getCrwctAppid(), coreWechat.getCrwctAppsecret(), "油卡充值",
                    coreUser.getCrusrOpenid(), "油卡充值", crrerPayFee, uuid,
                    coreWechat.getCrwctNotifyurl(), coreWechat.getCrwctPartner(), coreWechat.getCrwctPartnerkey(), Constant.DEFAULT_CHARSET);

            WxPayUtil wxPayUtil = new WxPayUtil();
            PayPackage finaPackage = wxPayUtil.getPackage(payOrder);

            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取package成功", finaPackage), response);
            logger.info("[CoreRechargeRecordController]:end getPackageGoPay");
        }
    }

    /**
     * 获取单个充值记录
     *
     * @param crrerUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个充值记录", httpMethod = "GET", notes = "获取单个充值记录", response = CoreRechargeRecordVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreRechargeRecord(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crrerUuid", required = true) String crrerUuid,
            HttpServletResponse response) {
        logger.info("[CoreRechargeRecordController]:begin viewsCoreRechargeRecord");
        CoreRechargeRecord coreRechargeRecord = new CoreRechargeRecord();
        coreRechargeRecord.setCrrerUuid(crrerUuid);
        coreRechargeRecord = coreRechargeRecordService.getCoreRechargeRecord(coreRechargeRecord);
        if (null == coreRechargeRecord) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "充值记录不存在!"), response);
            logger.info("[CoreRechargeRecordController]:end viewsCoreRechargeRecord");
            return;
        }

        CoreRechargeRecordVO coreRechargeRecordVO = new CoreRechargeRecordVO();
        coreRechargeRecordVO.convertPOToVO(coreRechargeRecord);

        HashSet<String> hashUserUuids = new HashSet<String>();
        hashUserUuids.add(coreRechargeRecord.getCrrerUser());
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);
        coreRechargeRecordVO.setCrrerUserMobile(userMap.get(coreRechargeRecord.getCrrerUser()) == null ? null : userMap.get(coreRechargeRecord.getCrrerUser()).getCrusrMobile());
        coreRechargeRecordVO.setCrrerUserName(userMap.get(coreRechargeRecord.getCrrerUser()) == null ? null : userMap.get(coreRechargeRecord.getCrrerUser()).getCrusrName());

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreRechargeRecordVO), response);
        logger.info("[CoreRechargeRecordController]:end viewsCoreRechargeRecord");
    }

    /**
     *  获取我所有的充值记录分页列表<Page>
     *
     * @param crrerUser        充值用户
     * @param pageNum          页码
     * @param pageSize         页数
     * @return
     */
    @ApiOperation(value = "获取我所有的充值记录分页列表", httpMethod = "GET", notes = "获取我所有的充值记录分页列表", response = CoreRechargeRecordVO.class)
    @RequestMapping(value = "/find/my/page", method = RequestMethod.GET)
    public void findCoreRechargeRecordPageMy(
            @ApiParam(value = "充值用户", required = true) @RequestParam(value = "crrerUser", required = true) String crrerUser,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreRechargeRecordController]:begin findCoreRechargeRecordPageMy");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreRechargeRecord> page = coreRechargeRecordService.findCoreRechargeRecordForMy(crrerUser, pageNum, pageSize);
        Page<CoreRechargeRecordVO> pageVO = new Page<CoreRechargeRecordVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreRechargeRecord coreRechargeRecord : page.getResult()) {
            hashUserUuids.add(coreRechargeRecord.getCrrerUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreRechargeRecordVO> vos = new ArrayList<CoreRechargeRecordVO>();
        CoreRechargeRecordVO vo;
        for (CoreRechargeRecord coreRechargeRecordPO : page.getResult()) {
            vo = new CoreRechargeRecordVO();
            vo.convertPOToVO(coreRechargeRecordPO);
            vo.setCrrerUserMobile(userMap.get(coreRechargeRecordPO.getCrrerUser()) == null ? null : userMap.get(coreRechargeRecordPO.getCrrerUser()).getCrusrMobile());
            vo.setCrrerUserName(userMap.get(coreRechargeRecordPO.getCrrerUser()) == null ? null : userMap.get(coreRechargeRecordPO.getCrrerUser()).getCrusrName());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我所有的充值记录分页列表成功!", pageVO), response);
        logger.info("[CoreRechargeRecordController]:end findCoreRechargeRecordPageMy");
    }

    /**
     * 后台获取充值记录分页列表<Page>
     *
     * @param mobile        充值用户手机号
     * @param crrerStatus      充值状态:1成功,0失败,2申请充值
     * @param crrerPayType     支付方式（1微信支付，2银联支付）
     * @param crrerOrder       交易单号
     * @param pageNum          页码
     * @param pageSize         页数
     * @return
     */
    @ApiOperation(value = "后台获取充值记录分页列表", httpMethod = "GET", notes = "后台获取充值记录分页列表", response = CoreRechargeRecordVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreRechargeRecordPage(
            @ApiParam(value = "充值用户手机号", required = false) @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "充值状态:1成功,0失败,2申请充值", required = false) @RequestParam(value = "crrerStatus", required = false) Integer crrerStatus,
            @ApiParam(value = "支付方式（1微信支付，2银联支付）", required = false) @RequestParam(value = "crrerPayType", required = false) Integer crrerPayType,
            @ApiParam(value = "交易单号", required = false) @RequestParam(value = "crrerOrder", required = false) String crrerOrder,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreRechargeRecordController]:begin findCoreRechargeRecordPage");
        CoreRechargeRecord newCoreRechargeRecord = new CoreRechargeRecord();
        newCoreRechargeRecord.setCrrerStatus(crrerStatus);
        newCoreRechargeRecord.setCrrerPayType(crrerPayType);
        newCoreRechargeRecord.setCrrerOrder(crrerOrder);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreRechargeRecord> page = coreRechargeRecordService.findCoreRechargeRecordPage(newCoreRechargeRecord, mobile, pageNum, pageSize);
        Page<CoreRechargeRecordVO> pageVO = new Page<CoreRechargeRecordVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreRechargeRecord coreRechargeRecord : page.getResult()) {
            hashUserUuids.add(coreRechargeRecord.getCrrerUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreRechargeRecordVO> vos = new ArrayList<CoreRechargeRecordVO>();
        CoreRechargeRecordVO vo;
        for (CoreRechargeRecord coreRechargeRecordPO : page.getResult()) {
            vo = new CoreRechargeRecordVO();
            vo.convertPOToVO(coreRechargeRecordPO);
            vo.setCrrerUserMobile(userMap.get(coreRechargeRecordPO.getCrrerUser()) == null ? null : userMap.get(coreRechargeRecordPO.getCrrerUser()).getCrusrMobile());
            vo.setCrrerUserName(userMap.get(coreRechargeRecordPO.getCrrerUser()) == null ? null : userMap.get(coreRechargeRecordPO.getCrrerUser()).getCrusrName());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreRechargeRecordController]:end findCoreRechargeRecordPage");
    }

}