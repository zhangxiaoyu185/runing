package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.xiaoyu.lingdian.service.CoreSystemSetService;
import com.xiaoyu.lingdian.entity.CoreSystemSet;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreSystemSetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

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
     * @param crsstDayContent 每日一言内容
     * @param crsstMessageDomain    项目域名
     * @param crsstAttachmentDir    附件存放目录
     * @return
     */
    @RequestMapping(value = "/update/coreSystemSet", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateCoreSystemSet(
             @ApiParam(value = "每日一言内容", required = true) @RequestParam(value = "crsstDayContent", required = true) String crsstDayContent,
            @ApiParam(value = "项目域名", required = false) @RequestParam(value = "crsstMessageDomain", required = false) String crsstMessageDomain,
            @ApiParam(value = "附件存放目录", required = false) @RequestParam(value = "crsstAttachmentDir", required = false) String crsstAttachmentDir,
            HttpServletResponse response) {
        logger.info("[CoreSystemSetController]:begin updateCoreSystemSet");
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet.setCrsstMessageDomain(crsstMessageDomain);
        coreSystemSet.setCrsstAttachmentDir(crsstAttachmentDir);
        coreSystemSet.setCrsstDayContent(crsstDayContent);
        coreSystemSetService.updateCoreSystemSet(coreSystemSet);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreSystemSetController]:end updateCoreSystemSet");
    }

    /**
     * 获取每日一言
     *
     * @return
     */
    @ApiOperation(value = "获取每日一言", httpMethod = "GET", notes = "获取每日一言", response = CoreSystemSetVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreSystemSet(HttpServletResponse response) {
        logger.info("[CoreSystemSetController]:begin viewsCoreSystemSet");
        CoreSystemSet coreSystemSet = new CoreSystemSet();
        coreSystemSet.setCrsstUuid(BaseConstant.SYSTEM_SET_UUID);
        coreSystemSet = coreSystemSetService.getCoreSystemSet(coreSystemSet);
        if (null == coreSystemSet) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "每日一言未配置!"), response);
            logger.info("[CoreSystemSetController]:end viewsCoreSystemSet");
            return;
        }

        CoreSystemSetVO coreSystemSetVO = new CoreSystemSetVO();
        coreSystemSetVO.convertPOToVO(coreSystemSet);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreSystemSetVO), response);
        logger.info("[CoreSystemSetController]:end viewsCoreSystemSet");
    }

}