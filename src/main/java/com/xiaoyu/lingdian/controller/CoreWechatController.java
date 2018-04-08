package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.CoreWechatService;
import com.xiaoyu.lingdian.entity.CoreWechat;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreWechatVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 小程序表
 */
@Controller
@RequestMapping(value = "/coreWechat")
@Api(value = "coreWechat", description = "小程序配置")
public class CoreWechatController extends BaseController {

    /**
     * 小程序表
     */
    @Autowired
    private CoreWechatService coreWechatService;

    /**
     * 修改
     *
     * @param crwctName        小程序名称
     * @param crwctAppid       appid
     * @param crwctAppsecret   appsecret
     * @param crwctPartner     商户号partner/mch_id
     * @param crwctPartnerkey  商户密钥partnerkey
     * @param crwctNotifyurl   回调URLnotifyurl
     * @param crwctRemarks     备注
     * @return
     */
    @RequestMapping(value = "/update/coreWechat", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateCoreWechat(
            @ApiParam(value = "小程序名称", required = true) @RequestParam(value = "crwctName", required = true) String crwctName,
            @ApiParam(value = "appid", required = true) @RequestParam(value = "crwctAppid", required = true) String crwctAppid,
            @ApiParam(value = "appsecret", required = true) @RequestParam(value = "crwctAppsecret", required = true) String crwctAppsecret,
            @ApiParam(value = "商户号partner/mch_id", required = false) @RequestParam(value = "crwctPartner", required = false) String crwctPartner,
            @ApiParam(value = "商户密钥partnerkey", required = false) @RequestParam(value = "crwctPartnerkey", required = false) String crwctPartnerkey,
            @ApiParam(value = "回调URLnotifyurl", required = false) @RequestParam(value = "crwctNotifyurl", required = false) String crwctNotifyurl,
            @ApiParam(value = "备注", required = false) @RequestParam(value = "crwctRemarks", required = false) String crwctRemarks,
            HttpServletResponse response) {
        logger.info("[CoreWechatController]:begin updateCoreWechat");
        CoreWechat coreWechat = new CoreWechat();
        coreWechat.setCrwctUuid(BaseConstant.WE_CHAT_UUID);
        coreWechat.setCrwctName(crwctName);
        coreWechat.setCrwctAppid(crwctAppid);
        coreWechat.setCrwctAppsecret(crwctAppsecret);
        coreWechat.setCrwctAccessTime(new Date());
        coreWechat.setCrwctPartner(crwctPartner);
        coreWechat.setCrwctPartnerkey(crwctPartnerkey);
        coreWechat.setCrwctNotifyurl(crwctNotifyurl);
        coreWechat.setCrwctUdate(new Date());
        coreWechat.setCrwctRemarks(crwctRemarks);
        coreWechatService.updateCoreWechat(coreWechat);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreWechatController]:end updateCoreWechat");
    }

    /**
     * 获取小程序配置信息
     *
     * @return
     */
    @ApiOperation(value = "获取小程序配置信息", httpMethod = "GET", notes = "获取小程序配置信息", response = CoreWechatVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreWechat(HttpServletResponse response) {
        logger.info("[CoreWechatController]:begin viewsCoreWechat");
        CoreWechat coreWechat = new CoreWechat();
        coreWechat.setCrwctUuid(BaseConstant.WE_CHAT_UUID);
        coreWechat = coreWechatService.getCoreWechat(coreWechat);
        if (null == coreWechat) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "小程序账号不存在!"), response);
            logger.info("[CoreWechatController]:end viewsCoreWechat");
            return;
        }

        CoreWechatVO coreWechatVO = new CoreWechatVO();
        coreWechatVO.convertPOToVO(coreWechat);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreWechatVO), response);
        logger.info("[CoreWechatController]:end viewsCoreWechat");
    }

}