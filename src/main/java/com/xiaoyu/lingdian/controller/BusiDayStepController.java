package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.service.BusiDayStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDayStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiDayStepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

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
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

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
     * 日步数排行榜<List>
     *
     * @param bsdspDay   所属日期
     * @return
     */
    @ApiOperation(value = "日步数排行榜", httpMethod = "POST", notes = "日步数排行榜", response = BusiDayStepVO.class)
    @RequestMapping(value = "/find/day/chat", method = RequestMethod.POST)
    public void findBusiDayStepForDayChat(
            @ApiParam(value = "上一次的日期(yyyy-MM-dd)", required = false) @RequestParam(value = "bsdspDay", required = false) String bsdspDay,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin findBusiDayStepForDayChat");
        if(StringUtil.isEmpty(bsdspDay)) { //日期不传就查询最新
            Date date = DateUtil.addDay(new Date(), 1);
            bsdspDay = DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, date);
        }
        BusiDayStep oldbusiDayStep = busiDayStepService.getBusiDayStepByOrd(bsdspDay);
        if(null == oldbusiDayStep || StringUtil.isEmpty(oldbusiDayStep.getBsdspDay())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiDayStepController]:end findBusiDayStepForDayChat");
            return;
        }
        List<BusiDayStep> lists = busiDayStepService.findBusiDayStepForDayChat(oldbusiDayStep.getBsdspDay());
        if(CollectionUtils.isEmpty(lists)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiDayStepController]:end findBusiDayStepForDayChat");
            return;
        }
        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiDayStep busiDayStepPO : lists) {
            hashUserUuids.add(busiDayStepPO.getBsdspUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiDayStepVO> vos = new ArrayList<BusiDayStepVO>();
        BusiDayStepVO vo;
        for (BusiDayStep busiDayStep : lists) {
            vo = new BusiDayStepVO();
            vo.convertPOToVO(busiDayStep);
            vo.setBsdspUserName(userMap.get(busiDayStep.getBsdspUser())==null?null:userMap.get(busiDayStep.getBsdspUser()).getCrusrName());
            vos.add(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("bsdspDay", oldbusiDayStep.getBsdspDay());
        map.put("list", vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "日步数排行榜获取成功!", vos), response);
        logger.info("[BusiDayStepController]:end findBusiDayStepForDayChat");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param name  用户昵称
     * @param bsdspDay   所属日期
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取日步数分页列表", httpMethod = "POST", notes = "获取日步数分页列表", response = BusiDayStepVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiDayStepPage(
            @ApiParam(value = "用户昵称", required = false) @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "所属日期", required = false) @RequestParam(value = "bsdspDay", required = false) String bsdspDay,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin findBusiDayStepPage");
        BusiDayStep busiDayStep = new BusiDayStep();
        busiDayStep.setBsdspDay(bsdspDay);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiDayStep> page = busiDayStepService.findBusiDayStepPage(busiDayStep, name, pageNum, pageSize);
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