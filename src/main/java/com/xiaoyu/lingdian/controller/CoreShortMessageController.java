package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.SendSMSUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.CoreShortMessageService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreShortMessage;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreShortMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.xiaoyu.lingdian.entity.CoreShortMessage;
import com.xiaoyu.lingdian.entity.CoreSystemSet;
import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreShortMessageService;
import com.xiaoyu.lingdian.service.CoreSystemSetService;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.vo.CoreShortMessageVO;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 短信日志记录表
 */
@Controller
@RequestMapping(value = "/coreShortMessage")
@Api(value = "coreShortMessage", description = "短信日志相关操作")
public class CoreShortMessageController extends BaseController {

    /**
     * 短信日志记录表
     */
    @Autowired
    private CoreShortMessageService coreShortMessageService;

    /**
     * 系统设置表
     */
    @Autowired
    private CoreSystemSetService coreSystemSetService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 完善信息发送短信
     *
     * @param crmceUser 所属用户
     * @param crmceMobile 所属用户手机号
     * @return
     */
    @ApiOperation(value = "(前台调用)完善信息发送短信", httpMethod = "POST", notes = "(前台调用)完善信息发送短信")
    @RequestMapping(value="/complete/info", method=RequestMethod.POST)
    public void completeInfoSendSms(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "crmceUser", required = true) String crmceUser,
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "crmceMobile", required = true) String crmceMobile,
            HttpServletResponse response) {
        logger.info("[CoreShortMessageController]:begin completeInfoSendSms");
        if(!StringUtil.isMobile(crmceMobile)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "手机号格式错误！"), response);
            logger.info("[CoreShortMessageController]:end completeInfoSendSms");
            return;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crmceUser);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属用户不存在!"), response);
            logger.info("[CoreShortMessageController]:end completeInfoSendSms");
            return;
        }

        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (null == coreSystemSet) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请先配置短信账户信息!"), response);
            logger.info("[CoreShortMessageController]:end completeInfoSendSms");
            return;
        }

        //判断今日短信发送是否超过限制
        int count = coreShortMessageService.getCoreShortMessageCountByMobile(crmceMobile);
        if (count >= 5) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "今日发送短信数量已超过限制5条,请明日再操作!"), response);
            logger.info("[CoreShortMessageController]:end completeInfoSendSms");
            return;
        }

        Date date = new Date();
        String uuid = RandomUtil.generateString(16);
        String code = RandomUtil.generateMixNum(6);
        boolean flag = false;
        try {
            flag = SendSMSUtil.completeInfo(coreSystemSet.getCrsstMessagePath(), coreSystemSet.getCrsstMessageKey(), crmceMobile, code, coreSystemSet.getCrsstMessageLoginname(), coreSystemSet.getCrsstMessagePwd());
        } catch (Exception e) {
            logger.info("发送完善信息验证码失败！");
        }
        if (!flag) {
            CoreShortMessage coreShortMessage = new CoreShortMessage();
            coreShortMessage.setCrmceUuid(uuid);
            coreShortMessage.setCrmceMobile(crmceMobile);
            coreShortMessage.setCrmceContent(code);
            coreShortMessage.setCrmceTime(date);
            coreShortMessage.setCrmceTimeout(DateUtil.addMinute(date, 10));
            coreShortMessage.setCrmceStatus(0);
            coreShortMessage.setCrmceType(1);

            coreShortMessageService.insertCoreShortMessage(coreShortMessage);

            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "发送完善信息验证码失败!"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }

        CoreShortMessage coreShortMessage = new CoreShortMessage();
        coreShortMessage.setCrmceUuid(uuid);
        coreShortMessage.setCrmceMobile(crmceMobile);
        coreShortMessage.setCrmceContent(code);
        coreShortMessage.setCrmceTime(date);
        coreShortMessage.setCrmceTimeout(DateUtil.addMinute(date, 10));
        coreShortMessage.setCrmceStatus(1);
        coreShortMessage.setCrmceType(1);

        coreShortMessageService.insertCoreShortMessage(coreShortMessage);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "发送完善信息验证码成功!"),response);
        logger.info("[CoreShortMessageController]:end completeInfoSendSms");
    }

    /**
     * 申请提现发送短信
     *
     * @param crmceUser 所属用户
     * @param crmceMobile 所属用户手机号
     * @return
     */
    @ApiOperation(value = "(前台调用)申请提现发送短信", httpMethod = "POST", notes = "(前台调用)申请提现发送短信")
    @RequestMapping(value="/apply/cash", method=RequestMethod.POST)
    public void forgetSendSms(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "crmceUser", required = true) String crmceUser,
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "crmceMobile", required = true) String crmceMobile,
            HttpServletResponse response) {
        logger.info("[CoreShortMessageController]:begin applyCash");
        if(!StringUtil.isMobile(crmceMobile)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "手机号格式错误！"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crmceUser);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "所属用户不存在!"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }
        if (!crmceMobile.equals(coreUser.getCrusrMobile())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "发送手机号与用户手机号不匹配!"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }

        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (null == coreSystemSet) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请先配置短信账户信息!"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }

        //判断今日短信发送是否超过限制
        int count = coreShortMessageService.getCoreShortMessageCountByMobile(crmceMobile);
        if (count >= 5) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "今日发送短信数量已超过限制5条,请明日再操作!"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }

        Date date = new Date();
        String uuid = RandomUtil.generateString(16);
        String code = RandomUtil.generateMixNum(6);
        boolean flag = false;
        try {
            flag = SendSMSUtil.sendCash(coreSystemSet.getCrsstMessagePath(), coreSystemSet.getCrsstMessageKey(), crmceMobile, code, coreSystemSet.getCrsstMessageLoginname(), coreSystemSet.getCrsstMessagePwd());
        } catch (Exception e) {
            logger.info("发送提现短信失败！");
        }
        if (!flag) {
            CoreShortMessage coreShortMessage = new CoreShortMessage();
            coreShortMessage.setCrmceUuid(uuid);
            coreShortMessage.setCrmceMobile(crmceMobile);
            coreShortMessage.setCrmceContent(code);
            coreShortMessage.setCrmceTime(date);
            coreShortMessage.setCrmceTimeout(DateUtil.addMinute(date, 10));
            coreShortMessage.setCrmceStatus(0);
            coreShortMessage.setCrmceType(1);

            coreShortMessageService.insertCoreShortMessage(coreShortMessage);

            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "发送提现验证码失败!"), response);
            logger.info("[CoreShortMessageController]:end applyCash");
            return;
        }

        CoreShortMessage coreShortMessage = new CoreShortMessage();
        coreShortMessage.setCrmceUuid(uuid);
        coreShortMessage.setCrmceMobile(crmceMobile);
        coreShortMessage.setCrmceContent(code);
        coreShortMessage.setCrmceTime(date);
        coreShortMessage.setCrmceTimeout(DateUtil.addMinute(date, 10));
        coreShortMessage.setCrmceStatus(1);
        coreShortMessage.setCrmceType(1);

        coreShortMessageService.insertCoreShortMessage(coreShortMessage);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "发送提现验证码成功!"),response);
        logger.info("[CoreShortMessageController]:end applyCash");
    }

    /**
     * 获取单个短信验证码
     *
     * @param crmceUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个短信验证码", httpMethod = "GET", notes = "获取单个短信验证码", response = CoreShortMessageVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreShortMessage(
            @ApiParam(value = "短信标识UUID", required = true) @RequestParam(value = "crmceUuid", required = true) String crmceUuid,
            HttpServletResponse response) {
        logger.info("[CoreShortMessageController]:begin viewsCoreShortMessage");
        CoreShortMessage coreShortMessage = new CoreShortMessage();
        coreShortMessage.setCrmceUuid(crmceUuid);
        coreShortMessage = coreShortMessageService.getCoreShortMessage(coreShortMessage);
        if (null == coreShortMessage) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "短信日志记录不存在!"), response);
            logger.info("[CoreShortMessageController]:end viewsCoreShortMessage");
            return;
        }

        CoreShortMessageVO coreShortMessageVO = new CoreShortMessageVO();
        coreShortMessageVO.convertPOToVO(coreShortMessage);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreShortMessageVO), response);
        logger.info("[CoreShortMessageController]:end viewsCoreShortMessage");
    }

    /**
     * 获取短信验证码分页列表<Page>
     *
     * @param crmceMobile 手机号
     * @param pageNum     页码
     * @param pageSize    页数
     * @return
     */
    @ApiOperation(value = "获取短信验证码分页列表", httpMethod = "GET", notes = "获取短信验证码分页列表", response = CoreShortMessageVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreShortMessagePage(
            @ApiParam(value = "手机号", required = false) @RequestParam(value = "crmceMobile", required = false) String crmceMobile,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreShortMessageController]:begin findCoreShortMessagePage");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        CoreShortMessage newCoreShortMessage = new CoreShortMessage();
        newCoreShortMessage.setCrmceMobile(crmceMobile);
        Page<CoreShortMessage> page = coreShortMessageService.findCoreShortMessagePage(newCoreShortMessage, pageNum, pageSize);
        Page<CoreShortMessageVO> pageVO = new Page<CoreShortMessageVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreShortMessageVO> vos = new ArrayList<CoreShortMessageVO>();
        CoreShortMessageVO vo;
        for (CoreShortMessage coreShortMessage : page.getResult()) {
            vo = new CoreShortMessageVO();
            vo.convertPOToVO(coreShortMessage);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreShortMessageController]:end findCoreShortMessagePage");
    }

}