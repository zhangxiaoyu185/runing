package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.xiaoyu.lingdian.service.CoreSystemSetService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreSystemSet;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreSystemSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统设置表
 */
@Controller
@RequestMapping(value = "/coreSystemSet")
@Api(value = "coreSystemSet", description = "系统设置相关操作")
public class CoreSystemSetController extends BaseController {

    /**
     * 系统设置表
     */
    @Autowired
    private CoreSystemSetService coreSystemSetService;

    /**
     * 修改
     *
     * @param crsstInviteLink       邀请链接前缀
     * @param crsstFirstIncome      一级佣金比
     * @param crsstSecondIncome     二级佣金比
     * @param crsstCoupon           返还优惠券比例
     * @param crsstMessagePath      短信接口路径
     * @param crsstMessageLoginname 短信账户名
     * @param crsstMessagePwd       短信密码
     * @param crsstMessageKey       短信KEY
     * @param crsstMessageDomain    项目域名
     * @param crsstAttachmentDir    附件存放目录
     * @return
     */
    @RequestMapping(value = "/update/coreSystemSet", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateCoreSystemSet(
            @ApiParam(value = "邀请链接前缀", required = true) @RequestParam(value = "crsstInviteLink", required = true) String crsstInviteLink,
            @ApiParam(value = "一级佣金比", required = true) @RequestParam(value = "crsstFirstIncome", required = true) Double crsstFirstIncome,
            @ApiParam(value = "二级佣金比", required = true) @RequestParam(value = "crsstSecondIncome", required = true) Double crsstSecondIncome,
            @ApiParam(value = "返还优惠券比例", required = true) @RequestParam(value = "crsstCoupon", required = true) Integer crsstCoupon,
            @ApiParam(value = "短信接口路径", required = true) @RequestParam(value = "crsstMessagePath", required = true) String crsstMessagePath,
            @ApiParam(value = "短信账户名", required = true) @RequestParam(value = "crsstMessageLoginname", required = true) String crsstMessageLoginname,
            @ApiParam(value = "短信密码", required = true) @RequestParam(value = "crsstMessagePwd", required = true) String crsstMessagePwd,
            @ApiParam(value = "短信KEY", required = true) @RequestParam(value = "crsstMessageKey", required = true) String crsstMessageKey,
            @ApiParam(value = "项目域名", required = true) @RequestParam(value = "crsstMessageDomain", required = true) String crsstMessageDomain,
            @ApiParam(value = "附件存放目录", required = true) @RequestParam(value = "crsstAttachmentDir", required = true) String crsstAttachmentDir,
            HttpServletResponse response) {
        logger.info("[CoreSystemSetController]:begin updateCoreSystemSet");
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet.setCrsstInviteLink(crsstInviteLink);
        coreSystemSet.setCrsstFirstIncome(crsstFirstIncome);
        coreSystemSet.setCrsstSecondIncome(crsstSecondIncome);
        coreSystemSet.setCrsstCoupon(crsstCoupon);
        coreSystemSet.setCrsstMessagePath(crsstMessagePath);
        coreSystemSet.setCrsstMessageLoginname(crsstMessageLoginname);
        coreSystemSet.setCrsstMessagePwd(crsstMessagePwd);
        coreSystemSet.setCrsstMessageKey(crsstMessageKey);
        coreSystemSet.setCrsstMessageDomain(crsstMessageDomain);
        coreSystemSet.setCrsstAttachmentDir(crsstAttachmentDir);

        coreSystemSetService.updateCoreSystemSet(coreSystemSet);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreSystemSetController]:end updateCoreSystemSet");
    }


    /**
     * 获取单个系统设置
     *
     * @return
     */
    @ApiOperation(value = "获取单个系统设置", httpMethod = "GET", notes = "获取单个系统设置", response = CoreSystemSetVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreSystemSet(HttpServletResponse response) {
        logger.info("[CoreSystemSetController]:begin viewsCoreSystemSet");
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (null == coreSystemSet) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "系统设置不存在!"), response);
            logger.info("[CoreSystemSetController]:end viewsCoreSystemSet");
            return;
        }

        CoreSystemSetVO coreSystemSetVO = new CoreSystemSetVO();
        coreSystemSetVO.convertPOToVO(coreSystemSet);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreSystemSetVO), response);
        logger.info("[CoreSystemSetController]:end viewsCoreSystemSet");
    }

}