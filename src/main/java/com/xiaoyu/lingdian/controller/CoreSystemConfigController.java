package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.ArrayList;

import com.xiaoyu.lingdian.tool.RandomUtil;

import javax.servlet.http.HttpServletResponse;

import com.xiaoyu.lingdian.core.mybatis.page.Page;

import org.springframework.stereotype.Controller;

import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.entity.CoreSystemConfig;
import com.xiaoyu.lingdian.service.CoreSystemConfigService;
import com.xiaoyu.lingdian.vo.CoreSystemConfigVO;

@Controller
@RequestMapping(value = "/coreSystemConfig")
@Api(value = "coreSystemConfig", description = "系统配置相关操作")
public class CoreSystemConfigController extends BaseController {

    /**
     * 系统配置表
     */
    @Autowired
    private CoreSystemConfigService coreSystemConfigService;

    /**
     * 添加
     *
     * @param crscgKey   配置KEY
     * @param crscgValue 配置值
     * @param crscgDesc  配置说明
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/coreSystemConfig", method = RequestMethod.POST)
    public void addCoreSystemConfig(
            @ApiParam(value = "配置KEY", required = true) @RequestParam(value = "crscgKey", required = true) String crscgKey,
            @ApiParam(value = "配置值", required = true) @RequestParam(value = "crscgValue", required = true) String crscgValue,
            @ApiParam(value = "配置说明", required = false) @RequestParam(value = "crscgDesc", required = false) String crscgDesc,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin addCoreSystemConfig");
        CoreSystemConfig coreSystemConfig = new CoreSystemConfig();
        coreSystemConfig.setCrscgKey(crscgKey);

        //判断这个KEY是否已存在
        CoreSystemConfig systemConfig = coreSystemConfigService.getCoreSystemConfigByKey(coreSystemConfig);
        if (null != systemConfig) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "KEY已存在!"), response);
            logger.info("[CoreSystemConfigController]:end addCoreSystemConfig");
            return;
        }

        String uuid = RandomUtil.generateString(16);
        coreSystemConfig.setCrscgUuid(uuid);
        coreSystemConfig.setCrscgValue(crscgValue);
        coreSystemConfig.setCrscgDesc(crscgDesc);

        coreSystemConfigService.insertCoreSystemConfig(coreSystemConfig);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreSystemConfigController]:end addCoreSystemConfig");
    }

    /**
     * 修改
     *
     * @param crscgUuid  标识UUID
     * @param crscgValue 配置值
     * @param crscgDesc  配置说明
     * @return
     */
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    @RequestMapping(value = "/update/coreSystemConfig", method = RequestMethod.POST)
    public void updateCoreSystemConfig(
            @ApiParam(value = "配置标识UUID", required = true) @RequestParam(value = "crscgUuid", required = true) String crscgUuid,
            @ApiParam(value = "配置值", required = true) @RequestParam(value = "crscgValue", required = true) String crscgValue,
            @ApiParam(value = "配置说明", required = false) @RequestParam(value = "crscgDesc", required = false) String crscgDesc,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin updateCoreSystemConfig");
        CoreSystemConfig coreSystemConfig = new CoreSystemConfig();
        coreSystemConfig.setCrscgUuid(crscgUuid);
        coreSystemConfig.setCrscgValue(crscgValue);
        coreSystemConfig.setCrscgDesc(crscgDesc);

        coreSystemConfigService.updateCoreSystemConfig(coreSystemConfig);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreSystemConfigController]:end updateCoreSystemConfig");
    }

    /**
     * 删除
     *
     * @param crscgUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreSystemConfig(
            @ApiParam(value = "配置标识UUID", required = true) @RequestParam(value = "crscgUuid", required = true) String crscgUuid,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin deleteCoreSystemConfig");
        CoreSystemConfig coreSystemConfig = new CoreSystemConfig();
        coreSystemConfig.setCrscgUuid(crscgUuid);

        coreSystemConfigService.deleteCoreSystemConfig(coreSystemConfig);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreSystemConfigController]:end deleteCoreSystemConfig");
    }

    /**
     * 批量删除
     *
     * @param crscgUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreSystemConfig(
            @ApiParam(value = "配置标识UUID集合(|拼接)", required = true) @RequestParam(value = "crscgUuids", required = true) String crscgUuids,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin deleteBatchCoreSystemConfig");
        String[] uuids = crscgUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            logger.info("[CoreSystemConfigController]:end deleteBatchCoreSystemConfig");
            return;
        }
        coreSystemConfigService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreSystemConfigController]:end deleteBatchCoreSystemConfig");
    }

    /**
     * 获取单个系统配置
     *
     * @param crscgUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个系统配置", httpMethod = "GET", notes = "获取单个系统配置", response = CoreSystemConfigVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreSystemConfig(
            @ApiParam(value = "系统配置UUID", required = true) @RequestParam(value = "crscgUuid", required = true) String crscgUuid,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin viewsCoreSystemConfig");
        CoreSystemConfig coreSystemConfig = new CoreSystemConfig();
        coreSystemConfig.setCrscgUuid(crscgUuid);
        coreSystemConfig = coreSystemConfigService.getCoreSystemConfig(coreSystemConfig);
        if (null == coreSystemConfig) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "系统配置不存在!"), response);
            logger.info("[CoreSystemConfigController]:end viewsCoreSystemConfig");
            return;
        }

        CoreSystemConfigVO coreSystemConfigVO = new CoreSystemConfigVO();
        coreSystemConfigVO.convertPOToVO(coreSystemConfig);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreSystemConfigVO), response);
        logger.info("[CoreSystemConfigController]:end viewsCoreSystemConfig");
    }

    /**
     * 根据key获取配置值
     *
     * @param crscgKey
     * @return
     */
    @ApiOperation(value = "根据key获取配置值", httpMethod = "GET", notes = "根据key获取配置值", response = CoreSystemConfigVO.class)
    @RequestMapping(value = "/views/by/key", method = RequestMethod.GET)
    public void viewsCoreSystemConfigByKey(
            @ApiParam(value = "配置key", required = true) @RequestParam(value = "crscgKey", required = true) String crscgKey,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin viewsCoreSystemConfigByKey");
        CoreSystemConfig coreSystemConfig = new CoreSystemConfig();
        coreSystemConfig.setCrscgKey(crscgKey);
        coreSystemConfig = coreSystemConfigService.getCoreSystemConfigByKey(coreSystemConfig);
        if (null == coreSystemConfig) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "系统配置不存在!"), response);
            logger.info("[CoreSystemConfigController]:end viewsCoreSystemConfigByKey");
            return;
        }

        CoreSystemConfigVO coreSystemConfigVO = new CoreSystemConfigVO();
        coreSystemConfigVO.convertPOToVO(coreSystemConfig);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取配置值成功", coreSystemConfigVO), response);
        logger.info("[CoreSystemConfigController]:end viewsCoreSystemConfigByKey");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取列表", httpMethod = "GET", notes = "获取列表", response = CoreSystemConfigVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findCoreSystemConfigList(
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin findCoreSystemConfigList");
        List<CoreSystemConfig> lists = coreSystemConfigService.findCoreSystemConfigList();
        List<CoreSystemConfigVO> vos = new ArrayList<CoreSystemConfigVO>();
        CoreSystemConfigVO vo;
        for (CoreSystemConfig coreSystemConfig : lists) {
            vo = new CoreSystemConfigVO();
            vo.convertPOToVO(coreSystemConfig);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreSystemConfigController]:end findCoreSystemConfigList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param crscgKey 配置key
     * @param pageNum  页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value = "获取配置分页列表", httpMethod = "GET", notes = "获取配置分页列表", response = CoreSystemConfigVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreSystemConfigPage(
            @ApiParam(value = "配置key", required = false) @RequestParam(value = "crscgKey", required = false) String crscgKey,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreSystemConfigController]:begin findCoreSystemConfigPage");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        CoreSystemConfig newCoreSystemConfig = new CoreSystemConfig();
        newCoreSystemConfig.setCrscgKey(crscgKey);
        Page<CoreSystemConfig> page = coreSystemConfigService.findCoreSystemConfigPage(newCoreSystemConfig, pageNum, pageSize);
        Page<CoreSystemConfigVO> pageVO = new Page<CoreSystemConfigVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreSystemConfigVO> vos = new ArrayList<CoreSystemConfigVO>();
        CoreSystemConfigVO vo;
        for (CoreSystemConfig coreSystemConfig : page.getResult()) {
            vo = new CoreSystemConfigVO();
            vo.convertPOToVO(coreSystemConfig);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreSystemConfigController]:end findCoreSystemConfigPage");
    }

}
