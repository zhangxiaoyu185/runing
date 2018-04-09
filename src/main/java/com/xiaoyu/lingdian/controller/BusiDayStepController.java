package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.BusiDayStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDayStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiDayStepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 日步数表
 */
@Controller
@RequestMapping(value = "/busiDayStep")
@Api(value = "busiDayStep", description = "日步数相关操作")
public class BusiDayStepController extends BaseController {

    /**
     * 日步数表
     */
    @Autowired
    private BusiDayStepService busiDayStepService;

    /**
     * 添加
     *
     * @param bsdspUser  所属用户
     * @param bsdspCdate 创建时间
     * @param bsdspDay   所属日期
     * @param bsdspStep  步数
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiDayStep", method = RequestMethod.POST)
    public void addBusiDayStep(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bsdspUser", required = true) String bsdspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsdspCdate", required = true) Date bsdspCdate,
            @ApiParam(value = "所属日期", required = true) @RequestParam(value = "bsdspDay", required = true) String bsdspDay,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bsdspStep", required = true) Integer bsdspStep,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin addBusiDayStep");
        BusiDayStep busiDayStep = new BusiDayStep();
        String uuid = RandomUtil.generateString(16);
        busiDayStep.setBsdspUuid(uuid);
        busiDayStep.setBsdspUser(bsdspUser);
        busiDayStep.setBsdspCdate(bsdspCdate);
        busiDayStep.setBsdspDay(bsdspDay);
        busiDayStep.setBsdspStep(bsdspStep);

        busiDayStepService.insertBusiDayStep(busiDayStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiDayStepController]:end addBusiDayStep");
    }

    /**
     * 修改
     *
     * @param bsdspUuid  标识UUID
     * @param bsdspUser  所属用户
     * @param bsdspCdate 创建时间
     * @param bsdspDay   所属日期
     * @param bsdspStep  步数
     * @return
     */
    @RequestMapping(value = "/update/busiDayStep", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiDayStep(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsdspUuid", required = true) String bsdspUuid,
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bsdspUser", required = true) String bsdspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsdspCdate", required = true) Date bsdspCdate,
            @ApiParam(value = "所属日期", required = true) @RequestParam(value = "bsdspDay", required = true) String bsdspDay,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bsdspStep", required = true) Integer bsdspStep,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin updateBusiDayStep");
        BusiDayStep busiDayStep = new BusiDayStep();
        busiDayStep.setBsdspUuid(bsdspUuid);
        busiDayStep.setBsdspUser(bsdspUser);
        busiDayStep.setBsdspCdate(bsdspCdate);
        busiDayStep.setBsdspDay(bsdspDay);
        busiDayStep.setBsdspStep(bsdspStep);

        busiDayStepService.updateBusiDayStep(busiDayStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiDayStepController]:end updateBusiDayStep");
    }

    /**
     * 删除
     *
     * @param bsdspUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiDayStep(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsdspUuid", required = true) String bsdspUuid,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin deleteBusiDayStep");
        BusiDayStep busiDayStep = new BusiDayStep();
        busiDayStep.setBsdspUuid(bsdspUuid);

        busiDayStepService.deleteBusiDayStep(busiDayStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiDayStepController]:end deleteBusiDayStep");
    }

    /**
     * 批量删除
     *
     * @param bsdspUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiDayStep(
            @ApiParam(value = "日步数标识集合(|拼接)", required = true) @RequestParam(value = "bsdspUuids", required = true) String bsdspUuids,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin deleteBatchBusiDayStep");
        String[] uuids = bsdspUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiDayStepService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiDayStepController]:end deleteBatchBusiDayStep");
    }

    /**
     * 获取单个
     *
     * @param bsdspUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个", httpMethod = "POST", notes = "获取单个", response = BusiDayStepVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsBusiDayStep(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsdspUuid", required = true) String bsdspUuid,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin viewsBusiDayStep");
        BusiDayStep busiDayStep = new BusiDayStep();
        busiDayStep.setBsdspUuid(bsdspUuid);
        busiDayStep = busiDayStepService.getBusiDayStep(busiDayStep);
        if (null == busiDayStep) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "日步数不存在!"), response);
            logger.info("[BusiDayStepController]:end viewsBusiDayStep");
            return;
        }

        BusiDayStepVO busiDayStepVO = new BusiDayStepVO();
        busiDayStepVO.convertPOToVO(busiDayStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiDayStepVO), response);
        logger.info("[BusiDayStepController]:end viewsBusiDayStep");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取日步数列表", httpMethod = "POST", notes = "获取日步数列表", response = BusiDayStepVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.POST)
    public void findBusiDayStepList(
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin findBusiDayStepList");
        List<BusiDayStep> lists = busiDayStepService.findBusiDayStepList();
        List<BusiDayStepVO> vos = new ArrayList<BusiDayStepVO>();
        BusiDayStepVO vo;
        for (BusiDayStep busiDayStep : lists) {
            vo = new BusiDayStepVO();
            vo.convertPOToVO(busiDayStep);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiDayStepController]:end findBusiDayStepList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bsdspUser  所属用户
     * @param bsdspCdate 创建时间
     * @param bsdspDay   所属日期
     * @param bsdspStep  步数
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取日步数分页列表", httpMethod = "POST", notes = "获取日步数分页列表", response = BusiDayStepVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiDayStepPage(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bsdspUser", required = true) String bsdspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsdspCdate", required = true) Date bsdspCdate,
            @ApiParam(value = "所属日期", required = true) @RequestParam(value = "bsdspDay", required = true) String bsdspDay,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bsdspStep", required = true) Integer bsdspStep,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin findBusiDayStepPage");
        BusiDayStep busiDayStep = new BusiDayStep();
        busiDayStep.setBsdspUser(bsdspUser);
        busiDayStep.setBsdspCdate(bsdspCdate);
        busiDayStep.setBsdspDay(bsdspDay);
        busiDayStep.setBsdspStep(bsdspStep);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiDayStep> page = busiDayStepService.findBusiDayStepPage(busiDayStep, pageNum, pageSize);
        Page<BusiDayStepVO> pageVO = new Page<BusiDayStepVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiDayStepVO> vos = new ArrayList<BusiDayStepVO>();
        BusiDayStepVO vo;
        for (BusiDayStep busiDayStepPO : page.getResult()) {
            vo = new BusiDayStepVO();
            vo.convertPOToVO(busiDayStepPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiDayStepController]:end findBusiDayStepPage");
    }

}