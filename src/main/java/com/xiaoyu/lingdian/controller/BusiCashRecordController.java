package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.CoreShortMessage;
import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreShortMessageService;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.vo.BusiInvitedRelationVO;
import com.xiaoyu.lingdian.vo.UserPromoterInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.service.BusiCashRecordService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiCashRecord;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiCashRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户提现记录表
 */
@Controller
@RequestMapping(value = "/busiCashRecord")
@Api(value = "busiCashRecord", description = "用户提现记录相关操作")
public class BusiCashRecordController extends BaseController {

    /**
     * 用户提现记录表
     */
    @Autowired
    private BusiCashRecordService busiCashRecordService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 短信日志记录表
     */
    @Autowired
    private CoreShortMessageService coreShortMessageService;

    /**
     * 去提现界面
     *
     * @param bspioUser 所属用户
     * @return
     */
    @ApiOperation(value = "(前台调用)去提现界面", httpMethod = "GET", notes = "(前台调用)去提现界面")
    @RequestMapping(value="/go/promoterInfo", method=RequestMethod.GET)
    public void goPromoterInfo (
            @ApiParam(value = "提现人", required = false) @RequestParam(value = "bspioUser", required = false) String bspioUser,
            HttpServletResponse response) {
        logger.info("[BusiCashRecordController]:begin goPromoterInfo");
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(bspioUser);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该用户不存在!"), response);
            logger.info("[BusiCashRecordController]:end goPromoterInfo");
            return;
        }

        UserPromoterInfoVO userPromoterInfoVO = new UserPromoterInfoVO();
        userPromoterInfoVO.convertPOToVO(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取提现信息成功", userPromoterInfoVO), response);
        logger.info("[BusiCashRecordController]:end goPromoterInfo");
    }

    /**
     * 申请提现,判断验证码,添加提现记录(默认为已申请),冻结收益,1已申请2待打款3已打款4已驳回
     *
     * @param bscrdAmount 提取金额
     * @param bscrdExtracted 提取人
     * @param bscrdMobile 手机号
     * @param mobileCode 手机验证码
     * @param bscrdAccountName 银行账户名称
     * @param bscrdAccountNo 银行账号
     * @param bscrdBankName 银行开户行
     * @return
     */
    @RequestMapping(value="/add/busiCashRecord", method=RequestMethod.POST)
    @ApiOperation(value = "(前台调用)申请提现", httpMethod = "POST", notes = "(前台调用)申请提现")
    public void addBusiCashRecord (
            @ApiParam(value = "提取金额", required = true) @RequestParam(value = "bscrdAmount", required = true) Double bscrdAmount,
            @ApiParam(value = "提取人", required = true) @RequestParam(value = "bscrdExtracted", required = true) String bscrdExtracted,
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "bscrdMobile", required = true) String bscrdMobile,
            @ApiParam(value = "手机验证码", required = true) @RequestParam(value = "mobileCode", required = true) String mobileCode,
            @ApiParam(value = "银行账户名称", required = true) @RequestParam(value = "bscrdAccountName", required = true) String bscrdAccountName,
            @ApiParam(value = "银行账号", required = true) @RequestParam(value = "bscrdAccountNo", required = true) String bscrdAccountNo,
            @ApiParam(value = "银行开户行", required = true) @RequestParam(value = "bscrdBankName", required = true) String bscrdBankName,
            HttpServletResponse response) {
        logger.info("[BusiCashRecordController]:begin addBusiCashRecord");
        if (0.0 == bscrdAmount) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "提取金额不能为空!"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(bscrdExtracted);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该用户不存在!"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }
        if(!bscrdMobile.equals(coreUser.getCrusrMobile())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "提现申请手机号和预留手机号不符!"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }
        //判断提现金额大小
        if(coreUser.getCrusrEnableIncome() < bscrdAmount){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "您的可用收益不足!"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }

        //判断验证码
        CoreShortMessage coreShortMessage = coreShortMessageService.getCoreShortMessageByMobile(bscrdMobile);
        if (null == coreShortMessage) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请先发送短信验证码！"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }

        Date date = new Date();

        long secondLong = DateUtil.secondDiff(date, coreShortMessage.getCrmceTimeout());
        if (secondLong < 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该短信验证码已过期,请重新发送！"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }
        if (!mobileCode.equalsIgnoreCase(coreShortMessage.getCrmceContent())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "短信验证码错误！"), response);
            logger.info("[BusiCashRecordController]:end addBusiCashRecord");
            return;
        }

        //添加提现记录(默认为已申请)
        BusiCashRecord busiCashRecord = new BusiCashRecord();
        String uuid = RandomUtil.generateString(16);
        busiCashRecord.setBscrdUuid(uuid);
        busiCashRecord.setBscrdAmount(String.valueOf(bscrdAmount));
        busiCashRecord.setBscrdExtracted(bscrdExtracted);
        busiCashRecord.setBscrdStatus(1);
        busiCashRecord.setBscrdCdate(new Date());
        busiCashRecord.setBscrdUdate(new Date());
        busiCashRecord.setBscrdMobile(bscrdMobile);
        busiCashRecord.setBscrdAccountName(bscrdAccountName);
        busiCashRecord.setBscrdAccountNo(bscrdAccountNo);
        busiCashRecord.setBscrdBankName(bscrdBankName);
        busiCashRecordService.insertBusiCashRecord(busiCashRecord);

        //冻结收益
        coreUserService.updateCoreUserByIncome(bscrdExtracted, 0, 0, -bscrdAmount, bscrdAmount);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "申请提现成功!"),response);
        logger.info("[BusiCashRecordController]:end addBusiCashRecord");
    }

    /**
     * 审核
     *
     * @param bscrdUuid 标识UUID
     * @param bscrdStatus 状态:1已申请2待打款3已打款4已驳回
     * @return
     */
    @RequestMapping(value="/update/busiCashRecord", method=RequestMethod.POST)
    @ApiOperation(value = "审核", httpMethod = "POST", notes = "审核")
    public void updateBusiCashRecord (
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bscrdUuid", required = true) String bscrdUuid,
            @ApiParam(value = "状态:1已申请2待打款3已打款4已驳回", required = true) @RequestParam(value = "bscrdStatus", required = true) Integer bscrdStatus,
            HttpServletResponse response) {
        logger.info("[BusiCashRecordController]:begin updateBusiCashRecord");
        BusiCashRecord busiCashRecord = new BusiCashRecord();
        busiCashRecord.setBscrdUuid(bscrdUuid);
        BusiCashRecord cashRecord = busiCashRecordService.getBusiCashRecord(busiCashRecord);
        if (null == cashRecord) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "提现记录不存在!"), response);
            logger.info("[BusiCashRecordController]:end updateBusiCashRecord");
            return;
        }
        if(2 != bscrdStatus && 3 != bscrdStatus && 4 != bscrdStatus) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "当前状态不正确!"), response);
            logger.info("[BusiCashRecordController]:end updateBusiCashRecord");
            return;
        }
        if(2 == bscrdStatus) {
            if (1 != cashRecord.getBscrdStatus()) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "当前状态不正确!"), response);
                logger.info("[BusiCashRecordController]:end updateBusiCashRecord");
                return;
            }
        }
        if(3 == bscrdStatus) {
            if (2 != cashRecord.getBscrdStatus()) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "当前状态不正确!"), response);
                logger.info("[BusiCashRecordController]:end updateBusiCashRecord");
                return;
            }
        }
        if(4 == bscrdStatus) {
            if (2 != cashRecord.getBscrdStatus() && 1 != cashRecord.getBscrdStatus()) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "当前状态不正确!"), response);
                logger.info("[BusiCashRecordController]:end updateBusiCashRecord");
                return;
            }
        }

        busiCashRecord.setBscrdStatus(bscrdStatus);
        busiCashRecord.setBscrdUdate(new Date());
        busiCashRecordService.updateBusiCashRecord(busiCashRecord);

        if (3 == bscrdStatus) { //已打款把冻结去除
            coreUserService.updateCoreUserByIncome(cashRecord.getBscrdExtracted(), 0, 0, 0, -Double.valueOf(cashRecord.getBscrdAmount()));
        }
        if (4 == bscrdStatus) { //已驳回把冻结收益去除,增加可用收益
            coreUserService.updateCoreUserByIncome(cashRecord.getBscrdExtracted(), 0, 0, Double.valueOf(cashRecord.getBscrdAmount()), -Double.valueOf(cashRecord.getBscrdAmount()));
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "审核成功!"),response);
        logger.info("[BusiCashRecordController]:end updateBusiCashRecord");
    }

    /**
     * 获取单个提现记录
     *
     * @param bscrdUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个提现记录", httpMethod = "GET", notes = "获取单个提现记录", response = BusiCashRecordVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiCashRecord(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bscrdUuid", required = true) String bscrdUuid,
            HttpServletResponse response) {
        logger.info("[BusiCashRecordController]:begin viewsBusiCashRecord");
        BusiCashRecord busiCashRecord = new BusiCashRecord();
        busiCashRecord.setBscrdUuid(bscrdUuid);
        busiCashRecord = busiCashRecordService.getBusiCashRecord(busiCashRecord);
        if (null == busiCashRecord) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户提现记录不存在!"), response);
            logger.info("[BusiCashRecordController]:end viewsBusiCashRecord");
            return;
        }

        BusiCashRecordVO busiCashRecordVO = new BusiCashRecordVO();
        busiCashRecordVO.convertPOToVO(busiCashRecord);

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(busiCashRecord.getBscrdExtracted());
        coreUser = coreUserService.getCoreUser(coreUser);
        if(null != coreUser){
            busiCashRecordVO.setBscrdExtractedName(coreUser.getCrusrName());
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiCashRecordVO), response);
        logger.info("[BusiCashRecordController]:end viewsBusiCashRecord");
    }

    /**
     * 我的提现记录<Page>
     *
     * @param bscrdExtracted 提取人
     * @param bscrdStatus 提现状态:1已申请2交易成功3交易失败
     * @param pageNum 页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value = "(前台调用)获取用户提现记录列表", httpMethod = "GET", notes = "(前台调用)获取用户提现记录列表", response = BusiCashRecordVO.class)
    @RequestMapping(value="/my/find/by/cnd", method=RequestMethod.GET)
    public void findBusiCashRecordPageByMy (
            @ApiParam(value = "提取人", required = true) @RequestParam(value = "bscrdExtracted", required = true) String bscrdExtracted,
            @ApiParam(value = "状态:1已申请2待打款3已打款4已驳回", required = true) @RequestParam(value = "bscrdStatus", required = true) Integer bscrdStatus,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiCashRecordController]:begin findBusiCashRecordPageByMy");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiCashRecord> page = busiCashRecordService.findBusiCashRecordForPagesByMy(bscrdExtracted, bscrdStatus, pageNum, pageSize);
        Page<BusiCashRecordVO> pageVO = new Page<BusiCashRecordVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        List<BusiCashRecordVO> vos = new ArrayList<BusiCashRecordVO>();
        BusiCashRecordVO vo;
        for (BusiCashRecord busiCashRecord : page.getResult()) {
            vo = new BusiCashRecordVO();
            vo.convertPOToVO(busiCashRecord);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
        logger.info("[BusiCashRecordController]:end findBusiCashRecordPageByMy");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bscrdMobile      手机号
     * @param pageNum          页码
     * @param pageSize         页数
     * @return
     */
    @ApiOperation(value = "获取用户提现记录分页列表", httpMethod = "GET", notes = "获取用户提现记录分页列表", response = BusiCashRecordVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiCashRecordPage(
            @ApiParam(value = "手机号", required = false) @RequestParam(value = "bscrdMobile", required = false) String bscrdMobile,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiCashRecordController]:begin findBusiCashRecordPage");
        BusiCashRecord newbusiCashRecord = new BusiCashRecord();
        newbusiCashRecord.setBscrdMobile(bscrdMobile);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiCashRecord> page = busiCashRecordService.findBusiCashRecordPage(newbusiCashRecord, pageNum, pageSize);
        Page<BusiCashRecordVO> pageVO = new Page<BusiCashRecordVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiCashRecord busiCashRecord : page.getResult()) {
            hashUserUuids.add(busiCashRecord.getBscrdExtracted());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiCashRecordVO> vos = new ArrayList<BusiCashRecordVO>();
        BusiCashRecordVO vo;
        for (BusiCashRecord busiCashRecord : page.getResult()) {
            vo = new BusiCashRecordVO();
            vo.convertPOToVO(busiCashRecord);
            vo.setBscrdExtractedName(userMap.get(busiCashRecord.getBscrdExtracted())==null?null:userMap.get(busiCashRecord.getBscrdExtracted()).getCrusrName());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiCashRecordController]:end findBusiCashRecordPage");
    }

}