package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.BusiDayStep;
import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.tool.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.service.BusiMonthStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiMonthStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiMonthStepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 月步数表
 */
@Controller
@RequestMapping(value = "/busiMonthStep")
@Api(value = "busiMonthStep", description = "月步数相关操作")
public class BusiMonthStepController extends BaseController {

    /**
     * 月步数表
     */
    @Autowired
    private BusiMonthStepService busiMonthStepService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 添加
     *
     * @param bsmspUser  所属用户
     * @param bsmspCdate 创建时间
     * @param bsmspMonth 所属月
     * @param bsmspStep  步数
     * @param bsmspScore 分值
     * @param bsmspOrd   序号
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiMonthStep", method = RequestMethod.POST)
    public void addBusiMonthStep(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bsmspUser", required = true) String bsmspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsmspCdate", required = true) Date bsmspCdate,
            @ApiParam(value = "所属月", required = true) @RequestParam(value = "bsmspMonth", required = true) String bsmspMonth,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bsmspStep", required = true) Integer bsmspStep,
            @ApiParam(value = "分值", required = true) @RequestParam(value = "bsmspScore", required = true) Integer bsmspScore,
            @ApiParam(value = "序号", required = true) @RequestParam(value = "bsmspOrd", required = true) Integer bsmspOrd,
            HttpServletResponse response) {
        logger.info("[BusiMonthStepController]:begin addBusiMonthStep");
        BusiMonthStep busiMonthStep = new BusiMonthStep();
        String uuid = RandomUtil.generateString(16);
        busiMonthStep.setBsmspUuid(uuid);
        busiMonthStep.setBsmspUser(bsmspUser);
        busiMonthStep.setBsmspCdate(bsmspCdate);
        busiMonthStep.setBsmspMonth(bsmspMonth);
        busiMonthStep.setBsmspStep(bsmspStep);
        busiMonthStep.setBsmspScore(bsmspScore);
        busiMonthStep.setBsmspOrd(bsmspOrd);

        busiMonthStepService.insertBusiMonthStep(busiMonthStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiMonthStepController]:end addBusiMonthStep");
    }

    /**
     * 修改
     *
     * @param bsmspUuid  标识UUID
     * @param bsmspUser  所属用户
     * @param bsmspCdate 创建时间
     * @param bsmspMonth 所属月
     * @param bsmspStep  步数
     * @param bsmspScore 分值
     * @param bsmspOrd   序号
     * @return
     */
    @RequestMapping(value = "/update/busiMonthStep", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiMonthStep(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsmspUuid", required = true) String bsmspUuid,
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "bsmspUser", required = true) String bsmspUser,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsmspCdate", required = true) Date bsmspCdate,
            @ApiParam(value = "所属月", required = true) @RequestParam(value = "bsmspMonth", required = true) String bsmspMonth,
            @ApiParam(value = "步数", required = true) @RequestParam(value = "bsmspStep", required = true) Integer bsmspStep,
            @ApiParam(value = "分值", required = true) @RequestParam(value = "bsmspScore", required = true) Integer bsmspScore,
            @ApiParam(value = "序号", required = true) @RequestParam(value = "bsmspOrd", required = true) Integer bsmspOrd,
            HttpServletResponse response) {
        logger.info("[BusiMonthStepController]:begin updateBusiMonthStep");
        BusiMonthStep busiMonthStep = new BusiMonthStep();
        busiMonthStep.setBsmspUuid(bsmspUuid);
        busiMonthStep.setBsmspUser(bsmspUser);
        busiMonthStep.setBsmspCdate(bsmspCdate);
        busiMonthStep.setBsmspMonth(bsmspMonth);
        busiMonthStep.setBsmspStep(bsmspStep);
        busiMonthStep.setBsmspScore(bsmspScore);
        busiMonthStep.setBsmspOrd(bsmspOrd);

        busiMonthStepService.updateBusiMonthStep(busiMonthStep);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiMonthStepController]:end updateBusiMonthStep");
    }

    /**
     * 月步数排行榜
     *
     * @param bsmspOrd   上一次的序号
     * @return
     */
    @ApiOperation(value = "月步数排行榜", httpMethod = "POST", notes = "月步数排行榜", response = BusiMonthStepVO.class)
    @RequestMapping(value = "/find/month/chat", method = RequestMethod.POST)
    public void findBusiMonthStepForMonthChat(
            @ApiParam(value = "上一次的序号", required = false) @RequestParam(value = "bsmspOrd", required = false) Integer bsmspOrd,
            HttpServletResponse response) {
        logger.info("[BusiMonthStepController]:begin findBusiMonthStepForMonthChat");
        if(null == bsmspOrd || bsmspOrd == 0) { //序号不传就查询最新
            bsmspOrd = Integer.MAX_VALUE;
        }
        BusiMonthStep oldbusiMonthStep = busiMonthStepService.getBusiMonthStepByOrd(bsmspOrd);
        if(null == oldbusiMonthStep || null == oldbusiMonthStep.getBsmspOrd()) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiMonthStepController]:end findBusiMonthStepForMonthChat");
            return;
        }
        List<BusiMonthStep> lists = busiMonthStepService.findBusiMonthStepForMonthChat(oldbusiMonthStep.getBsmspOrd());
        if(CollectionUtils.isEmpty(lists)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiMonthStepController]:end findBusiMonthStepForMonthChat");
            return;
        }
        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiMonthStep busiMonthStep : lists) {
            hashUserUuids.add(busiMonthStep.getBsmspUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiMonthStepVO> vos = new ArrayList<BusiMonthStepVO>();
        BusiMonthStepVO vo;
        for (BusiMonthStep busiMonthStep : lists) {
            vo = new BusiMonthStepVO();
            vo.convertPOToVO(busiMonthStep);
            vo.setBsmspUserName(userMap.get(busiMonthStep.getBsmspUser())==null?null:userMap.get(busiMonthStep.getBsmspUser()).getCrusrName());
            vos.add(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("bsmspOrd", oldbusiMonthStep.getBsmspOrd());
        map.put("bsmspMonth", oldbusiMonthStep.getBsmspMonth());
        map.put("list", vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "月步数排行榜获取成功!", map), response);
        logger.info("[BusiMonthStepController]:end findBusiMonthStepForMonthChat");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param name  用户昵称
     * @param bsmspMonth 所属月
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取月步数分页列表", httpMethod = "POST", notes = "获取月步数分页列表", response = BusiMonthStepVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiMonthStepPage(
            @ApiParam(value = "用户昵称", required = false) @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "所属月", required = false) @RequestParam(value = "bsmspMonth", required = false) String bsmspMonth,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiMonthStepController]:begin findBusiMonthStepPage");
        BusiMonthStep busiMonthStep = new BusiMonthStep();
        busiMonthStep.setBsmspMonth(bsmspMonth);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiMonthStep> page = busiMonthStepService.findBusiMonthStepPage(busiMonthStep, name, pageNum, pageSize);
        Page<BusiMonthStepVO> pageVO = new Page<BusiMonthStepVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiMonthStepVO> vos = new ArrayList<BusiMonthStepVO>();
        BusiMonthStepVO vo;
        for (BusiMonthStep busiMonthStepPO : page.getResult()) {
            vo = new BusiMonthStepVO();
            vo.convertPOToVO(busiMonthStepPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiMonthStepController]:end findBusiMonthStepPage");
    }

}