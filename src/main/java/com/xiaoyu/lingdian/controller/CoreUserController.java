package com.xiaoyu.lingdian.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.entity.*;
import com.xiaoyu.lingdian.entity.weixin.SopenidAndUnionid;
import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.tool.http.HttpUrlConnectionUtil;
import com.xiaoyu.lingdian.tool.wx.WeixinSUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
     * 小程序表
     */
    @Autowired
    private CoreWechatService coreWechatService;

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
     * @param response
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ApiOperation(value = "(前台调用)注册登陆", httpMethod = "POST", notes = "(前台调用)注册登陆")
    public void index(
            @ApiParam(value = "授权OPENID", required = true) @RequestParam(value = "openid", required = true) String openid,
            @ApiParam(value = "微信用户的昵称", required = true) @RequestParam(value = "crusrWxNickname", required = true) String crusrWxNickname,
            @ApiParam(value = "微信用户的性别，值为1时是男性，值为2时是女性，值为0时是未知", required = true) @RequestParam(value = "crusrWxSex", required = true) String crusrWxSex,
            @ApiParam(value = "微信用户头像", required = false) @RequestParam(value = "crusrWxHeadimgurl", required = false) String crusrWxHeadimgurl,
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
            coreUser.setCrusrGender(Integer.valueOf(crusrWxSex));
            coreUser.setCrusrWxSex(crusrWxSex);
            coreUser.setCrusrOpenid(openid);
            coreUser.setCrusrWxNickname(crusrWxNickname);
            coreUser.setCrusrWxHeadimgurl(crusrWxHeadimgurl);
            coreUserService.insertCoreUser(coreUser);
        } else {
            //更新最后登录时间
            Date now = new Date();
            coreUser.setCrusrUdate(now);
            coreUser.setCrusrLastTime(now);
            coreUser.setCrusrName(crusrWxNickname);
            coreUser.setCrusrGender(Integer.valueOf(crusrWxSex));
            coreUser.setCrusrWxSex(crusrWxSex);
            coreUser.setCrusrWxNickname(crusrWxNickname);
            coreUser.setCrusrWxHeadimgurl(crusrWxHeadimgurl);
            coreUserService.updateCoreUserByOpenid(coreUser);
        }
        CoreUserVO vo = new CoreUserVO();
        vo.convertPOToVO(coreUser);

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
     * 完善信息
     *
     * @param crusrUuid            标识UUID
     * @param crusrName            姓名
     * @param crusrMobile          手机号
     * @param crusrGender          性别:1男,2女,0其它
     * @param crusrDept          所属部门
     */
    @RequestMapping(value = "/update/coreUser", method = RequestMethod.POST)
    @ApiOperation(value = "(前台调用)完善信息", httpMethod = "POST", notes = "(前台调用)完善信息")
    public void updateCoreUser(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crusrUuid", required = true) String crusrUuid,
            @ApiParam(value = "姓名", required = true) @RequestParam(value = "crusrName", required = true) String crusrName,
            @ApiParam(value = "手机号", required = false) @RequestParam(value = "crusrMobile", required = false) String crusrMobile,
            @ApiParam(value = "性别:1男,2女", required = false) @RequestParam(value = "crusrGender", required = false) Integer crusrGender,
            @ApiParam(value = "所属部门", required = true) @RequestParam(value = "crusrDept", required = true) String crusrDept,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin updateCoreUser");
        if (!StringUtil.isEmpty(crusrMobile)) {
            if (!StringUtil.isMobile(crusrMobile)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "手机号格式错误！"), response);
                logger.info("[CoreUserController.updateCoreUser]:end updateCoreUser");
                return;
            }
        }

        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrUuid(crusrUuid);
        coreUser.setCrusrName(crusrName);
        coreUser.setCrusrUdate(new Date());
        if(null != crusrGender) {
            coreUser.setCrusrGender(crusrGender);
        }
        coreUser.setCrusrMobile(crusrMobile);
        coreUser.setCrusrDept(crusrDept);
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
     * @param crusrName       姓名
     * @param crusrMobile     手机号码
     * @param pageNum         页码
     * @param pageSize        页数
     * @return
     */
    @ApiOperation(value = "获取用户分页列表", httpMethod = "GET", notes = "获取用户分页列表", response = CoreUserVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreUserPage(
            @ApiParam(value = "姓名", required = false) @RequestParam(value = "crusrName", required = false) String crusrName,
            @ApiParam(value = "手机号码", required = false) @RequestParam(value = "crusrMobile", required = false) String crusrMobile,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreUserController]:begin findCoreUserPage");
        CoreUser coreUser = new CoreUser();
        coreUser.setCrusrName(crusrName);
        coreUser.setCrusrMobile(crusrMobile);
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

}