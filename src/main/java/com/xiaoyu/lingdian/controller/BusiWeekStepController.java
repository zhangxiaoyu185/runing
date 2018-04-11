package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.service.BusiWeekStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiWeekStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiWeekStepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 周步数表
 */
@Controller
@RequestMapping(value = "/busiWeekStep")
@Api(value = "busiWeekStep", description = "周步数相关操作")
public class BusiWeekStepController extends BaseController {

    /**
     * 周步数表
     */
    @Autowired
    private BusiWeekStepService busiWeekStepService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 添加
     *
     * @param bswspUser  所属用户
     * @param bswspCdate 创建时间
     * @param bswspWeek  所属周
     * @param bswspMonth 所属月
     * @param bswspStep  步数
     * @param bswspScore 分值
     * @param bswspOrd   序号
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiWeekStep", method = RequestMethod.POST)
    public void addBusiWeekStep(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bswspUser", required = true) String bswspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bswspCdate", required = true) Date bswspCdate,
            @ApiParam(value = "所属周", required = true) @RequestParam(value = "bswspWeek", required = true) String bswspWeek,
            @ApiParam(value = "所属月", required = true) @RequestParam(value = "bswspMonth", required = true) String bswspMonth,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bswspStep", required = true) Integer bswspStep,
            @ApiParam(value = "分值", required = true) @RequestParam(value = "bswspScore", required = true) Integer bswspScore,
            @ApiParam(value = "序号", required = true) @RequestParam(value = "bswspOrd", required = true) Integer bswspOrd,
            HttpServletResponse response) {
        logger.info("[BusiWeekStepController]:begin addBusiWeekStep");
        BusiWeekStep busiWeekStep = new BusiWeekStep();
        String uuid = RandomUtil.generateString(16);
        busiWeekStep.setBswspUuid(uuid);
        busiWeekStep.setBswspUser(bswspUser);
        busiWeekStep.setBswspCdate(bswspCdate);
        busiWeekStep.setBswspWeek(bswspWeek);
        busiWeekStep.setBswspMonth(bswspMonth);
        busiWeekStep.setBswspStep(bswspStep);
        busiWeekStep.setBswspScore(bswspScore);
        busiWeekStep.setBswspOrd(bswspOrd);

        busiWeekStepService.insertBusiWeekStep(busiWeekStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiWeekStepController]:end addBusiWeekStep");
    }

    /**
     * 修改
     *
     * @param bswspUuid  标识UUID
     * @param bswspUser  所属用户
     * @param bswspCdate 创建时间
     * @param bswspWeek  所属周
     * @param bswspMonth 所属月
     * @param bswspStep  步数
     * @param bswspScore 分值
     * @param bswspOrd   序号
     * @return
     */
    @RequestMapping(value = "/update/busiWeekStep", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiWeekStep(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bswspUuid", required = true) String bswspUuid,
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bswspUser", required = true) String bswspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bswspCdate", required = true) Date bswspCdate,
            @ApiParam(value = "所属周", required = true) @RequestParam(value = "bswspWeek", required = true) String bswspWeek,
            @ApiParam(value = "所属月", required = true) @RequestParam(value = "bswspMonth", required = true) String bswspMonth,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bswspStep", required = true) Integer bswspStep,
            @ApiParam(value = "分值", required = true) @RequestParam(value = "bswspScore", required = true) Integer bswspScore,
            @ApiParam(value = "序号", required = true) @RequestParam(value = "bswspOrd", required = true) Integer bswspOrd,
            HttpServletResponse response) {
        logger.info("[BusiWeekStepController]:begin updateBusiWeekStep");
        BusiWeekStep busiWeekStep = new BusiWeekStep();
        busiWeekStep.setBswspUuid(bswspUuid);
        busiWeekStep.setBswspUser(bswspUser);
        busiWeekStep.setBswspCdate(bswspCdate);
        busiWeekStep.setBswspWeek(bswspWeek);
        busiWeekStep.setBswspMonth(bswspMonth);
        busiWeekStep.setBswspStep(bswspStep);
        busiWeekStep.setBswspScore(bswspScore);
        busiWeekStep.setBswspOrd(bswspOrd);

        busiWeekStepService.updateBusiWeekStep(busiWeekStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiWeekStepController]:end updateBusiWeekStep");
    }

    /**
     * 周步数排行榜
     *
     * @param bswspOrd   上一次的序号
     * @return
     */
    @ApiOperation(value = "周步数排行榜", httpMethod = "POST", notes = "周步数排行榜", response = BusiWeekStepVO.class)
    @RequestMapping(value = "/find/week/chat", method = RequestMethod.POST)
    public void findBusiWeekStepForWeekChat(
            @ApiParam(value = "上一次的序号", required = false) @RequestParam(value = "bswspOrd", required = false) Integer bswspOrd,
            HttpServletResponse response) {
        logger.info("[BusiWeekStepController]:begin findBusiWeekStepForWeekChat");
        if(null == bswspOrd || bswspOrd == 0) { //序号不传就查询最新
            bswspOrd = Integer.MAX_VALUE;
        }
        BusiWeekStep oldbusiWeekStep = busiWeekStepService.getBusiWeekStepByOrd(bswspOrd);
        if(null == oldbusiWeekStep || null == oldbusiWeekStep.getBswspOrd()) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiWeekStepController]:end findBusiWeekStepForWeekChat");
            return;
        }
        List<BusiWeekStep> lists = busiWeekStepService.findBusiWeekStepForWeekChat(oldbusiWeekStep.getBswspOrd());
        if(CollectionUtils.isEmpty(lists)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiWeekStepController]:end findBusiWeekStepForWeekChat");
            return;
        }
        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiWeekStep busiWeekStep : lists) {
            hashUserUuids.add(busiWeekStep.getBswspUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiWeekStepVO> vos = new ArrayList<BusiWeekStepVO>();
        BusiWeekStepVO vo;
        for (BusiWeekStep busiWeekStep : lists) {
            vo = new BusiWeekStepVO();
            vo.convertPOToVO(busiWeekStep);
            vo.setBswspUserName(userMap.get(busiWeekStep.getBswspUser())==null?null:userMap.get(busiWeekStep.getBswspUser()).getCrusrName());
            vos.add(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("bswspOrd", oldbusiWeekStep.getBswspOrd());
        map.put("bswspWeek", oldbusiWeekStep.getBswspMonth()+"-"+oldbusiWeekStep.getBswspWeek());
        map.put("list", vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "周步数排行榜获取成功!", vos), response);
        logger.info("[BusiWeekStepController]:end findBusiWeekStepForWeekChat");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param name  用户昵称
     * @param bswspMonth 所属月
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取周步数分页列表", httpMethod = "POST", notes = "获取周步数分页列表", response = BusiWeekStepVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiWeekStepPage(
            @ApiParam(value = "用户昵称", required = false) @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "所属月", required = false) @RequestParam(value = "bswspMonth", required = false) String bswspMonth,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiWeekStepController]:begin findBusiWeekStepPage");
        BusiWeekStep busiWeekStep = new BusiWeekStep();
        busiWeekStep.setBswspMonth(bswspMonth);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiWeekStep> page = busiWeekStepService.findBusiWeekStepPage(busiWeekStep, name, pageNum, pageSize);
        Page<BusiWeekStepVO> pageVO = new Page<BusiWeekStepVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiWeekStepVO> vos = new ArrayList<BusiWeekStepVO>();
        BusiWeekStepVO vo;
        for (BusiWeekStep busiWeekStepPO : page.getResult()) {
            vo = new BusiWeekStepVO();
            vo.convertPOToVO(busiWeekStepPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiWeekStepController]:end findBusiWeekStepPage");
    }

}