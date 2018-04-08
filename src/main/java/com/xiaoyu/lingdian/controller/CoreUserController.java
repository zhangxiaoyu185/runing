package com.xiaoyu.lingdian.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.entity.*;
import com.xiaoyu.lingdian.entity.weixin.Constant;
import com.xiaoyu.lingdian.entity.weixin.OpenidAndAccessToken;
import com.xiaoyu.lingdian.entity.weixin.SopenidAndUnionid;
import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.encrypt.MD5Util;
import com.xiaoyu.lingdian.tool.http.HttpUrlConnectionUtil;
import com.xiaoyu.lingdian.tool.wx.WeixinSUtil;
import com.xiaoyu.lingdian.tool.wx.WeixinUtil;
import com.xiaoyu.lingdian.vo.BusiInvitedRelationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreUserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户表
 */
@Controller
@RequestMapping(value = "/coreUser")
@Api(value = "coreUser", description = "用户相关操作")
public class CoreUserController extends BaseController {

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 邀请关系表
     */
    @Autowired
    private BusiInvitedRelationService busiInvitedRelationService;

    /**
     * 短信日志记录表
     */
    @Autowired
    private CoreShortMessageService coreShortMessageService;

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
     * 业务附件表
     */
    @Autowired
    private CoreAttachmentService coreAttachmentService;

    /**
     * 系统设置表
     */
    @Autowired
    private CoreSystemSetService coreSystemSetService;

    /**
     * 获取号码归属地
     *
     * @param mobileTel 手机号码
     * @return
     */
    @RequestMapping(value = "/mobile/attach", method = RequestMethod.GET)
    @ApiOperation(value = "(前台调用)获取号码归属地", httpMethod = "GET", notes = "(前台调用)获取号码归属地")
    public void getMobileAttach(
            @ApiParam(value = "手机号码", required = true) @RequestParam(value = "mobileTel", required = true) String mobileTel,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin getMobileAttach");
        if (!StringUtil.isMobile(mobileTel)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "手机号码不符合规范!"), response);
            logger.info("[CoreUserController]:end getMobileAttach");
            return;
        }

        String requestUrl = "http://v.showji.com/Locating/showji.com2016234999234.aspx?m=" + mobileTel + "&output=json&callback=querycallback&timestamp=" + StringUtil.create_timestamp();
        String message = "号段不存在";
        String strReturn = HttpUrlConnectionUtil.sendGet(requestUrl, null);
        if (!StringUtil.isEmpty(strReturn)) {
            strReturn = strReturn.replace("querycallback(", "").replace(")", "");
            JSONObject addressObject = (JSONObject) JSONObject.parse(strReturn);
            if ("True".equals(addressObject.getString("QueryResult"))) {
                message = addressObject.getString("Province") + addressObject.getString("City") + addressObject.getString("Corp");
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取号码归属地成功", message), response);
        logger.info("[CoreUserController]:end getMobileAttach");
    }

    /**
     * 注册打开小程序的时候，后台根据openid判断是注册还是登陆进行相应操作，成功后返回用户信息；
     *
     * @param openid            授权OPENID
     * @param crusrWxNickname   微信用户的昵称
     * @param crusrWxSex        微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     * @param crusrWxHeadimgurl 微信用户头像
     * @param shareUcode        邀请人邀请码
     * @param response
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ApiOperation(value = "(前台调用)注册登陆", httpMethod = "POST", notes = "(前台调用)注册登陆")
    public void index(
            @ApiParam(value = "授权OPENID", required = true) @RequestParam(value = "openid", required = true) String openid,
            @ApiParam(value = "微信用户的昵称", required = true) @RequestParam(value = "crusrWxNickname", required = true) String crusrWxNickname,
            @ApiParam(value = "微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知", required = true) @RequestParam(value = "crusrWxSex", required = true) String crusrWxSex,
            @ApiParam(value = "微信用户头像", required = false) @RequestParam(value = "crusrWxHeadimgurl", required = false) String crusrWxHeadimgurl,
            @ApiParam(value = "邀请人邀请码", required = false) @RequestParam(value = "shareUcode", required = false) String shareUcode,
            HttpServletResponse response) {
        logger.info("[CoreUserController.index]:begin index");
        CoreUser coreUser = coreUserService.getCoreUserByOpenId(openid);
        String regEx = "[\u4e00-\u9fa5\\w]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(crusrWxNickname);
        StringBuffer sbf = new StringBuffer();
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                sbf.append(m.group(i).trim());
            }
        }
        crusrWxNickname = sbf.toString();
        if (!crusrWxSex.equals("0") && !crusrWxSex.equals("1") && !crusrWxSex.equals("2")) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "微信用户的性别类型不正确！"), response);
            logger.info("[CoreUserController.index]:end index");
        }
        Date date = new Date();
        String uuid;
        if (null == coreUser || StringUtil.isEmpty(coreUser.getCrusrUuid())) { //注册
            coreUser = new CoreUser();
            uuid = RandomUtil.generateString(16);
            coreUser.setCrusrUuid(uuid);
            coreUser.setCrusrCdate(date);
            coreUser.setCrusrUdate(date);
            coreUser.setCrusrLastTime(date);
            coreUser.setCrusrName(crusrWxNickname);
            coreUser.setCrusrStatus(StatusEnum.ENABLE.getCode());
            String inviteCode = RandomUtil.generateMixString(17);
            coreUser.setCrusrInviteCode(inviteCode);
            coreUser.setCrusrGender(Integer.valueOf(crusrWxSex));
            coreUser.setCrusrWxSex(crusrWxSex);
            coreUser.setCrusrOpenid(openid);
            coreUser.setCrusrWxNickname(crusrWxNickname);
            coreUser.setCrusrWxHeadimgurl(crusrWxHeadimgurl);
            coreUser.setCrusrAgent("1");
            coreUser.setIsComplete(2);
            coreUser.setCrusrType(3); //未知

            String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
            //获取附件目录
            CoreSystemSet coreSystemSet = new CoreSystemSet();
            coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
            coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
            if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
                baseDir = coreSystemSet.getCrsstAttachmentDir();
            }
            String path = baseDir + "9/" + uuid + ".png";
            //生成邀请码
            WeixinSUtil.getSWxaCodeUnlimit(path, inviteCode, "pages/Home/Register", this.coreWechatService.getAccessToken(BaseConstant.WE_CHAT_UUID));
            //插入数据库中
            CoreAttachment coreAttachment = new CoreAttachment();
            String attachUuid = RandomUtil.generateString(16);
            coreAttachment.setCratmUuid(attachUuid);
            coreAttachment.setCratmCdate(new Date());
            coreAttachment.setCratmFileName(uuid + ".png");
            coreAttachment.setCratmDir("9");
            coreAttachment.setCratmExtension(".png");
            coreAttachment.setCratmType(9);
            coreAttachmentService.insertCoreAttachment(coreAttachment);
            coreUser.setCrusrInvitedCode(attachUuid);

            if (!StringUtil.isEmpty(shareUcode)) { //有邀请码，存关联关系
                //根据邀请码查询用户信息
                CoreUser inviteUser = coreUserService.getCoreUserByInviteCode(shareUcode);
                if (null != inviteUser) {
                    coreUser.setCrusrInviter(inviteUser.getCrusrUuid());
                    //更新用户的下级用户人数
                    coreUserService.updateCoreUserByDownCount(inviteUser.getCrusrUuid());
                    if (!StringUtil.isEmpty(inviteUser.getCrusrInviter())) {
                        coreUserService.updateCoreUserByDownCount(inviteUser.getCrusrInviter());
                    }

                    BusiInvitedRelation busiInvitedRelation = new BusiInvitedRelation();
                    busiInvitedRelation.setBsirnUuid(RandomUtil.generateString(16));
                    busiInvitedRelation.setBsirnInvited(inviteUser.getCrusrUuid());
                    busiInvitedRelation.setBsirnBeInvited(uuid);
                    busiInvitedRelation.setBsirnCode(shareUcode);
                    busiInvitedRelation.setBsirnIdate(new Date());
                    busiInvitedRelationService.insertBusiInvitedRelation(busiInvitedRelation);
                }
            }
            coreUserService.insertCoreUser(coreUser);
        } else {
            //登陆
            //判断是否有邀请二维码
            uuid = coreUser.getCrusrUuid();
            if (StringUtil.isEmpty(coreUser.getCrusrInvitedCode())) {
                String baseDir = BaseConstant.ATTACHMENT_DIR_PATH;
                //获取附件目录
                CoreSystemSet coreSystemSet = new CoreSystemSet();
                coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
                coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
                if (coreSystemSet != null && !StringUtil.isEmpty(coreSystemSet.getCrsstAttachmentDir())) {
                    baseDir = coreSystemSet.getCrsstAttachmentDir();
                }
                String path = baseDir + "9/" + coreUser.getCrusrUuid() + ".png";
                //生成邀请码
                WeixinSUtil.getSWxaCodeUnlimit(path, coreUser.getCrusrInviteCode(), "pages/Home/Register", this.coreWechatService.getAccessToken(BaseConstant.WE_CHAT_UUID));
                //插入数据库中
                CoreAttachment coreAttachment = new CoreAttachment();
                String attachUuid = RandomUtil.generateString(16);
                coreAttachment.setCratmUuid(attachUuid);
                coreAttachment.setCratmCdate(new Date());
                coreAttachment.setCratmFileName(coreUser.getCrusrUuid() + ".png");
                coreAttachment.setCratmDir("9");
                coreAttachment.setCratmExtension(".png");
                coreAttachment.setCratmType(9);
                coreAttachmentService.insertCoreAttachment(coreAttachment);
                coreUser.setCrusrInvitedCode(attachUuid);
            }
            //更新最后登录时间
            Date now = new Date();
            coreUser.setCrusrUdate(now);
            coreUser.setCrusrLastTime(now);
            coreUserService.updateCoreUserByOpenid(coreUser);
        }
        CoreUserVO vo = new CoreUserVO();
        vo.convertPOToVO(coreUser);
        vo.setCrusrCouponSum(this.coreCouponService.findCoreCouponForMySum(uuid));

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "操作成功！", vo), response);
        logger.info("[CoreUserController.index]:end index");
    }

    /**
     * 传code获取openid，后台返回openid
     *
     * @param code     登录凭证code
     * @param response
     */
    @RequestMapping(value = "/get/openid", method = RequestMethod.GET)
    @ApiOperation(value = "(前台调用)获取openid", httpMethod = "GET", notes = "(前台调用)获取openid")
    public void getOpenid(
            @ApiParam(value = "登录凭证code", required = true) @RequestParam(value = "code", required = true) String code,
            HttpServletResponse response) {
        logger.info("[CoreUserController.getOpenid]:begin getOpenid.");
        CoreWechat coreWechat = new CoreWechat();
        coreWechat.setCrwctUuid(BaseConstant.WE_CHAT_UUID);
        coreWechat = coreWechatService.getCoreWechat(coreWechat);
        if (null == coreWechat) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "小程序账号不存在!"), response);
            logger.info("[CoreUserController.getOpenid]:end getOpenid");
            return;
        }

        SopenidAndUnionid sopenidAndUnionid = WeixinSUtil.getSopenidAndUnionid(coreWechat.getCrwctAppid(), coreWechat.getCrwctAppsecret(), null, code);
        if (null == sopenidAndUnionid || null == sopenidAndUnionid.getOpenid()) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "获取失败!"), response);
            logger.info("[CoreUserController.getOpenid]:end getOpenid");
            return;
        } else {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取成功", sopenidAndUnionid.getOpenid()), response);
            logger.info("[CoreUserController.getOpenid]:end getOpenid.");
        }
    }

    /**
     * 完善信息(增加手机号和完善信息短信验证码)
     *
     * @param crusrUuid            标识UUID
     * @param crusrName            持卡人姓名/公司名
     * @param crusrMobile          手机号
     * @param code                 短信验证码
     * @param crusrWxHeadimgurl    微信用户头像
     * @param crusrGender          性别:1男,2女,0其它
     * @param crusrIdCard          身份证号
     * @param crusrAccountName     银行账号名称
     * @param crusrAccountNo       银行账号
     * @param crusrBankName        银行开户行
     * @param crusrDrivingPic      行驶证照片正面
     * @param crusrDrivingPicOther 行驶证照片反面
     * @param crusrCarNo 车牌
     * @param crusrDrivingNo 行驶证号
     * @param crusrBusinessNo 营业执照号
     * @return crusrType 身份（1个人2公司3未知）
     */
    @RequestMapping(value = "/update/coreUser", method = RequestMethod.POST)
    @ApiOperation(value = "(前台调用)完善信息", httpMethod = "POST", notes = "(前台调用)完善信息")
    public void updateCoreUser(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crusrUuid", required = true) String crusrUuid,
            @ApiParam(value = "持卡人姓名/公司名", required = true) @RequestParam(value = "crusrName", required = true) String crusrName,
            @ApiParam(value = "微信用户头像", required = false) @RequestParam(value = "crusrWxHeadimgurl", required = false) String crusrWxHeadimgurl,
            @ApiParam(value = "手机号", required = false) @RequestParam(value = "crusrMobile", required = false) String crusrMobile,
            @ApiParam(value = "短信验证码", required = false) @RequestParam(value = "code", required = false) String code,
            @ApiParam(value = "性别:1男,2女", required = false) @RequestParam(value = "crusrGender", required = false) Integer crusrGender,
            @ApiParam(value = "身份证号", required = false) @RequestParam(value = "crusrIdCard", required = false) String crusrIdCard,
            @ApiParam(value = "银行账号名称", required = false) @RequestParam(value = "crusrAccountName", required = false) String crusrAccountName,
            @ApiParam(value = "银行账号", required = false) @RequestParam(value = "crusrAccountNo", required = false) String crusrAccountNo,
            @ApiParam(value = "银行开户行", required = false) @RequestParam(value = "crusrBankName", required = false) String crusrBankName,
            @ApiParam(value = "行驶证照片正面", required = false) @RequestParam(value = "crusrDrivingPic", required = false) String crusrDrivingPic,
            @ApiParam(value = "行驶证照片反面", required = false) @RequestParam(value = "crusrDrivingPicOther", required = false) String crusrDrivingPicOther,
            @ApiParam(value = "车牌", required = false) @RequestParam(value = "crusrCarNo", required = false) String crusrCarNo,
            @ApiParam(value = "行驶证号", required = false) @RequestParam(value = "crusrDrivingNo", required = false) String crusrDrivingNo,
            @ApiParam(value = "营业执照号", required = false) @RequestParam(value = "crusrBusinessNo", required = false) String crusrBusinessNo,
            @ApiParam(value = "身份（1个人2公司3未知）", required = true) @RequestParam(value = "crusrType", required = true) Integer crusrType,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin updateCoreUser");
        if (!StringUtil.isEmpty(crusrMobile)) {
            if (!StringUtil.isMobile(crusrMobile)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "手机号格式错误！"), response);
                logger.info("[CoreUserController.updateCoreUser]:end updateCoreUser");
                return;
            }
            if (StringUtil.isEmpty(code)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "完善手机号必填验证码！"), response);
                logger.info("[CoreUserController.updateCoreUser]:end updateCoreUser");
                return;
            }
            //判断验证码
            CoreShortMessage coreShortMessage = coreShortMessageService.getCoreShortMessageByMobile(crusrMobile);
            if (null == coreShortMessage) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请先发送短信验证码！"), response);
                logger.info("[CoreUserController.updateCoreUser]:end updateCoreUser");
                return;
            }

            Date date = new Date();

            long secondLong = DateUtil.secondDiff(date, coreShortMessage.getCrmceTimeout());
            if (secondLong < 0) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "该短信验证码已过期,请重新发送！"), response);
                logger.info("[CoreUserController.updateCoreUser]:end updateCoreUser");
                return;
            }
            if (!code.equalsIgnoreCase(coreShortMessage.getCrmceContent())) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "短信验证码错误！"), response);
                logger.info("[CoreUserController.updateCoreUser]:end updateCoreUser");
                return;
            }
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        coreUser.setCrusrName(crusrName);
        coreUser.setCrusrWxHeadimgurl(crusrWxHeadimgurl);
        coreUser.setCrusrUdate(new Date());
        if(null != crusrGender) {
            coreUser.setCrusrGender(crusrGender);
        }
        coreUser.setCrusrIdCard(crusrIdCard);
        coreUser.setCrusrMobile(crusrMobile);
        coreUser.setCrusrAccountNo(crusrAccountNo);
        coreUser.setCrusrBankName(crusrBankName);
        coreUser.setCrusrAccountName(crusrAccountName);
        coreUser.setCrusrDrivingPic(crusrDrivingPic);
        coreUser.setCrusrDrivingPicOther(crusrDrivingPicOther);
        coreUser.setIsComplete(1);
        coreUser.setCrusrType(crusrType);
        coreUser.setCrusrCarNo(crusrCarNo);
        coreUser.setCrusrBusinessNo(crusrBusinessNo);
        coreUser.setCrusrDrivingNo(crusrDrivingNo);
        coreUserService.updateCoreUser(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "完善信息成功!"), response);
        logger.info("[CoreUserController]:end updateCoreUser");
    }

    /**
     * 删除
     *
     * @param crusrUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreUser(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crusrUuid", required = true) String crusrUuid,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin deleteCoreUser");
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        coreUser.setCrusrStatus(StatusEnum.DISABLE.getCode());
        coreUser.setCrusrUdate(new Date());
        coreUserService.updateCoreUser(coreUser);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreUserController]:end deleteCoreUser");
    }

    /**
     * 批量删除
     *
     * @param crusrUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreUser(
            @ApiParam(value = "用户标识集合(|拼接)", required = true) @RequestParam(value = "crusrUuids", required = true) String crusrUuids,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin deleteBatchCoreUser");
        String[] uuids = crusrUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        coreUserService.updateBatchCoreUserByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreUserController]:end deleteBatchCoreUser");
    }

    /**
     * 获取单个用户
     *
     * @param crusrUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个用户", httpMethod = "GET", notes = "获取单个用户", response = CoreUserVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreUser(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crusrUuid", required = true) String crusrUuid,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin viewsCoreUser");
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户不存在!"), response);
            logger.info("[CoreUserController]:end viewsCoreUser");
            return;
        }

        CoreUserVO coreUserVO = new CoreUserVO();
        coreUserVO.convertPOToVO(coreUser);
        coreUserVO.setCrusrCouponSum(this.coreCouponService.findCoreCouponForMySum(coreUser.getCrusrUuid()));

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreUserVO), response);
        logger.info("[CoreUserController]:end viewsCoreUser");
    }

    /**
     * 获取用户列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取用户列表", httpMethod = "GET", notes = "获取用户列表", response = CoreUserVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findCoreUserList(
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserList");
        List<CoreUser> lists = coreUserService.findCoreUserList(null, null);
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUser : lists) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUser);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreUserController]:end findCoreUserList");
    }

    /**
     * 获取用户分页列表<Page>
     *
     * @param crusrName       持卡人姓名/公司名
     * @param crusrMobile     手机号码
     * @param crusrIdCard     身份证号
     * @param crusrInviteCode 邀请码
     * @param crusrAgent      所属代理商平台(1本平台)
     * @param crusrCarNo      车牌
     * @param crusrType       身份（1个人2公司3未知）
     * @param pageNum         页码
     * @param pageSize        页数
     * @return
     */
    @ApiOperation(value = "获取用户分页列表", httpMethod = "GET", notes = "获取用户分页列表", response = CoreUserVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreUserPage(
            @ApiParam(value = "持卡人姓名/公司名", required = false) @RequestParam(value = "crusrName", required = false) String crusrName,
            @ApiParam(value = "手机号码", required = false) @RequestParam(value = "crusrMobile", required = false) String crusrMobile,
            @ApiParam(value = "身份证号", required = false) @RequestParam(value = "crusrIdCard", required = false) String crusrIdCard,
            @ApiParam(value = "邀请码", required = false) @RequestParam(value = "crusrInviteCode", required = false) String crusrInviteCode,
            @ApiParam(value = "所属代理商平台(1本平台)", required = false) @RequestParam(value = "crusrAgent", required = false) String crusrAgent,
            @ApiParam(value = "车牌", required = false) @RequestParam(value = "crusrCarNo", required = false) String crusrCarNo,
            @ApiParam(value = "身份（1个人2公司3未知）", required = false) @RequestParam(value = "crusrType", required = false) Integer crusrType,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserPage");
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrName(crusrName);
        coreUser.setCrusrMobile(crusrMobile);
        coreUser.setCrusrIdCard(crusrIdCard);
        coreUser.setCrusrInviteCode(crusrInviteCode);
        coreUser.setCrusrAgent(crusrAgent);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreUser> page = coreUserService.findCoreUserPage(coreUser, pageNum, pageSize);
        Page<CoreUserVO> pageVO = new Page<CoreUserVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUserPO : page.getResult()) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUserPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreUserController]:end findCoreUserPage");
    }

    /**
     * 获取我的U友数量
     *
     * @param user 用户标志
     * @return
     */
    @ApiOperation(value = "获取我的U友数量", httpMethod = "GET", notes = "获取我的U友数量")
    @RequestMapping(value = "/get/my/ucount", method = RequestMethod.GET)
    public void getMyUcount(
            @ApiParam(value = "用户标志", required = true) @RequestParam(value = "user", required = true) String user,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin getMyUcount");
//        int one = coreUserService.getCountUserOneOrTwo(user, null);
//        int two = 0;
//        //获取一级U友的集合
//        List<CoreUser> oneUserList = coreUserService.findCoreUserByMyOneOrTwo(user, null);
//        HashSet<String> hashUserUuids = new HashSet<String>();
//        for (CoreUser coreUser : oneUserList) {
//            hashUserUuids.add(coreUser.getCrusrUuid());
//        }
//        List<String> userUuids = new ArrayList<>(hashUserUuids);
//        if(!CollectionUtils.isEmpty(userUuids)) {
//            two = coreUserService.getCountUserOneOrTwo(null, userUuids);
//        }
//        int sum = one + two;
//
//        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我的U友数量成功!", sum), response);

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(user);
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null == coreUser) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户不存在!"), response);
            logger.info("[CoreUserController]:end getMyUcount");
            return;
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我的U友数量成功!", coreUser.getCrusrDownCount()), response);
        logger.info("[CoreUserController]:end getMyUcount");
    }

    /**
     * 我的一级U友分页列表<Page>
     *
     * @param user     用户标志
     * @param pageNum  页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value = "我的一级U友分页列表", httpMethod = "GET", notes = "我的一级U友分页列表", response = CoreUserVO.class)
    @RequestMapping(value = "/find/by/one", method = RequestMethod.GET)
    public void findCoreUserByMyOne(
            @ApiParam(value = "用户标志", required = true) @RequestParam(value = "user", required = true) String user,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserByMyOne");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreUser> page = coreUserService.findCoreUserByMyOneOrTwo(user, null, pageNum, pageSize);
        Page<CoreUserVO> pageVO = new Page<CoreUserVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUserPO : page.getResult()) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUserPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "我的一级U友分页列表列表获取成功!", pageVO), response);
        logger.info("[CoreUserController]:end findCoreUserByMyOne");
    }

    /**
     * 我的二级U友分页列表<Page>
     *
     * @param user     用户标志
     * @param pageNum  页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value = "我的二级U友分页列表", httpMethod = "GET", notes = "我的二级U友分页列表", response = CoreUserVO.class)
    @RequestMapping(value = "/find/by/two", method = RequestMethod.GET)
    public void findCoreUserByMyTwo(
            @ApiParam(value = "用户标志", required = true) @RequestParam(value = "user", required = true) String user,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserByMyTwo");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        //获取一级U友的集合
        List<CoreUser> oneUserList = coreUserService.findCoreUserByMyOneOrTwo(user, null);
        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreUser coreUser : oneUserList) {
            hashUserUuids.add(coreUser.getCrusrUuid());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        if (CollectionUtils.isEmpty(userUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "我的二级U友分页列表列表获取成功!", new Page<>(1, 10, 0, new ArrayList<CoreUserVO>())), response);
            logger.info("[CoreUserController]:end findCoreUserByMyTwo");
        }
        Page<CoreUser> page = coreUserService.findCoreUserByMyOneOrTwo(null, userUuids, pageNum, pageSize);
        Page<CoreUserVO> pageVO = new Page<CoreUserVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreUserVO> vos = new ArrayList<CoreUserVO>();
        CoreUserVO vo;
        for (CoreUser coreUserPO : page.getResult()) {
            vo = new CoreUserVO();
            vo.convertPOToVO(coreUserPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "我的二级U友分页列表列表获取成功!", pageVO), response);
        logger.info("[CoreUserController]:end findCoreUserByMyTwo");
    }

    /**
     * 获取单个邀请好友关系
     *
     * @param bsirnUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/invited/views", method = RequestMethod.GET)
    @ApiOperation(value = "获取单个邀请关系", httpMethod = "GET", notes = "获取单个邀请关系", response = BusiInvitedRelationVO.class)
    public void viewsBusiInvitedRelation(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsirnUuid", required = true) String bsirnUuid,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin viewsBusiInvitedRelation");
        BusiInvitedRelation busiInvitedRelation = new BusiInvitedRelation();
        busiInvitedRelation.setBsirnUuid(bsirnUuid);
        busiInvitedRelation = busiInvitedRelationService.getBusiInvitedRelation(busiInvitedRelation);
        if (null == busiInvitedRelation) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "邀请好友关系不存在!"), response);
            logger.info("[CoreUserController]:end viewsBusiInvitedRelation");
            return;
        }

        BusiInvitedRelationVO busiInvitedRelationVO = new BusiInvitedRelationVO();
        busiInvitedRelationVO.convertPOToVO(busiInvitedRelation);

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(busiInvitedRelation.getBsirnInvited());
        coreUser = coreUserService.getCoreUser(coreUser);
        if (null != coreUser) {
            busiInvitedRelationVO.setBsirnInvitedName(coreUser.getCrusrName());
            busiInvitedRelationVO.setBsirnInvitedHead(coreUser.getCrusrWxHeadimgurl());
            busiInvitedRelationVO.setBsirnInvitedMobile(coreUser.getCrusrMobile());
        }

        CoreUser user = new CoreUser();
        user.setCrusrUuid(busiInvitedRelation.getBsirnBeInvited());
        user = coreUserService.getCoreUser(user);
        if (null != user) {
            busiInvitedRelationVO.setBsirnBeInvitedName(user.getCrusrName());
            busiInvitedRelationVO.setBsirnBeInvitedHead(user.getCrusrWxHeadimgurl());
            busiInvitedRelationVO.setBsirnBeInvitedMobile(coreUser.getCrusrMobile());
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个邀请好友关系成功", busiInvitedRelationVO), response);
        logger.info("[CoreUserController]:end viewsBusiInvitedRelation");
    }

    /**
     * 我的邀请人列表
     *
     * @param bsirnInvited
     * @return
     */
    @ApiOperation(value = "(前台调用)我的邀请人列表", httpMethod = "GET", notes = "(前台调用)我的邀请人列表", response = BusiInvitedRelationVO.class)
    @RequestMapping(value = "/invited/my/invited/list", method = RequestMethod.GET)
    public void myImvitedInvitedList(
            @ApiParam(value = "邀请人", required = true) @RequestParam(value = "bsirnInvited", required = true) String bsirnInvited,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin myImvitedInvitedList");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiInvitedRelation> page = busiInvitedRelationService.findBusiInvitedRelationPage(bsirnInvited, pageNum, pageSize);
        Page<BusiInvitedRelationVO> pageVO = new Page<BusiInvitedRelationVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiInvitedRelation busiInvitedRelation : page.getResult()) {
            hashUserUuids.add(busiInvitedRelation.getBsirnInvited());
            hashUserUuids.add(busiInvitedRelation.getBsirnBeInvited());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiInvitedRelationVO> vos = new ArrayList<BusiInvitedRelationVO>();
        BusiInvitedRelationVO vo;
        for (BusiInvitedRelation busiInvitedRelation : page.getResult()) {
            vo = new BusiInvitedRelationVO();
            vo.convertPOToVO(busiInvitedRelation);
            vo.setBsirnBeInvitedName(userMap.get(busiInvitedRelation.getBsirnBeInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnBeInvited()).getCrusrName());
            vo.setBsirnBeInvitedHead(userMap.get(busiInvitedRelation.getBsirnBeInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnBeInvited()).getCrusrWxHeadimgurl());
            vo.setBsirnBeInvitedMobile(userMap.get(busiInvitedRelation.getBsirnBeInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnBeInvited()).getCrusrMobile());
            vo.setBsirnInvitedName(userMap.get(busiInvitedRelation.getBsirnInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnInvited()).getCrusrName());
            vo.setBsirnInvitedHead(userMap.get(busiInvitedRelation.getBsirnInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnInvited()).getCrusrWxHeadimgurl());
            vo.setBsirnInvitedMobile(userMap.get(busiInvitedRelation.getBsirnInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnInvited()).getCrusrMobile());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我的邀请人列表成功", pageVO), response);
        logger.info("[CoreUserController]:end myImvitedInvitedList");
    }

    /**
     * 后台获取邀请好友关系列表<Page>
     *
     * @param bsirnCode 邀请码
     * @param mobile    邀请人手机号
     * @param pageNum   页码
     * @param pageSize  页数
     * @return
     */
    @ApiOperation(value = "获取邀请关系分页列表", httpMethod = "GET", notes = "获取邀请关系分页列表", response = BusiInvitedRelationVO.class)
    @RequestMapping(value = "/invited/find/by/cnd", method = RequestMethod.GET)
    public void findBusiInvitedRelationPage(
            @ApiParam(value = "邀请码", required = false) @RequestParam(value = "bsirnCode", required = false) String bsirnCode,
            @ApiParam(value = "邀请人手机号", required = false) @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findBusiInvitedRelationPage");
        BusiInvitedRelation newBusiInvitedRelation = new BusiInvitedRelation();
        newBusiInvitedRelation.setBsirnCode(bsirnCode);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiInvitedRelation> page = busiInvitedRelationService.findBusiInvitedRelationPage(newBusiInvitedRelation, mobile, pageNum, pageSize);
        Page<BusiInvitedRelationVO> pageVO = new Page<BusiInvitedRelationVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiInvitedRelation busiInvitedRelation : page.getResult()) {
            hashUserUuids.add(busiInvitedRelation.getBsirnInvited());
            hashUserUuids.add(busiInvitedRelation.getBsirnBeInvited());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiInvitedRelationVO> vos = new ArrayList<BusiInvitedRelationVO>();
        BusiInvitedRelationVO vo;
        for (BusiInvitedRelation busiInvitedRelation : page.getResult()) {
            vo = new BusiInvitedRelationVO();
            vo.convertPOToVO(busiInvitedRelation);
            vo.setBsirnBeInvitedName(userMap.get(busiInvitedRelation.getBsirnBeInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnBeInvited()).getCrusrName());
            vo.setBsirnBeInvitedHead(userMap.get(busiInvitedRelation.getBsirnBeInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnBeInvited()).getCrusrWxHeadimgurl());
            vo.setBsirnBeInvitedMobile(userMap.get(busiInvitedRelation.getBsirnBeInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnBeInvited()).getCrusrMobile());
            vo.setBsirnInvitedName(userMap.get(busiInvitedRelation.getBsirnInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnInvited()).getCrusrName());
            vo.setBsirnInvitedHead(userMap.get(busiInvitedRelation.getBsirnInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnInvited()).getCrusrWxHeadimgurl());
            vo.setBsirnInvitedMobile(userMap.get(busiInvitedRelation.getBsirnInvited()) == null ? null : userMap.get(busiInvitedRelation.getBsirnInvited()).getCrusrMobile());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreUserController]:end findBusiInvitedRelationPage");
    }

}