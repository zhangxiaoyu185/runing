package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.enums.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.xiaoyu.lingdian.service.BusiAgentService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiAgent;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiAgentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理商表
 */
@Controller
@RequestMapping(value = "/busiAgent")
@Api(value = "busiAgent", description = "代理商相关操作")
public class BusiAgentController extends BaseController {

    /**
     * 代理商表
     */
    @Autowired
    private BusiAgentService busiAgentService;

    /**
     * 添加
     *
     * @param bsaetFee  余额
     * @param bsaetCode 代理商名称
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiAgent", method = RequestMethod.POST)
    public void addBusiAgent(
            @ApiParam(value = "余额", required = true) @RequestParam(value = "bsaetFee", required = true) Double bsaetFee,
            @ApiParam(value = "代理商名称", required = true) @RequestParam(value = "bsaetCode", required = true) String bsaetCode,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin addBusiAgent");
        BusiAgent busiAgent = new BusiAgent();
        String uuid = RandomUtil.generateString(16);
        busiAgent.setBsaetUuid(uuid);
        busiAgent.setBsaetFee(bsaetFee);
        busiAgent.setBsaetCode(bsaetCode);
        busiAgent.setBsaetPwd(RandomUtil.generateMixString(64));
        busiAgent.setBsaetStatus(StatusEnum.ENABLE.getCode());
        busiAgentService.insertBusiAgent(busiAgent);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiAgentController]:end addBusiAgent");
    }

    /**
     * 重置密钥
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @RequestMapping(value = "/reset/pwd", method = RequestMethod.POST)
    @ApiOperation(value = "重置密钥", httpMethod = "POST", notes = "重置密钥")
    public void resetPwd(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin resetPwd");
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetUuid(bsaetUuid);
        busiAgent.setBsaetPwd(RandomUtil.generateMixString(64));
        busiAgentService.updateBusiAgent(busiAgent);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "重置密钥成功!"), response);
        logger.info("[BusiAgentController]:end resetPwd");
    }

    /**
     * 修改代理商名称
     *
     * @param bsaetUuid 标识UUID
     * @param bsaetCode 代理商名称
     * @return
     */
    @RequestMapping(value = "/update/busiAgent", method = RequestMethod.POST)
    @ApiOperation(value = "修改代理商名称", httpMethod = "POST", notes = "修改代理商名称")
    public void updateBusiAgent(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            @ApiParam(value = "代理商名称", required = true) @RequestParam(value = "bsaetCode", required = true) String bsaetCode,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin updateBusiAgent");
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetUuid(bsaetUuid);
        busiAgent.setBsaetCode(bsaetCode);
        busiAgentService.updateBusiAgent(busiAgent);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiAgentController]:end updateBusiAgent");
    }

    /**
     * 修改余额
     *
     * @param bsaetUuid 标识UUID
     * @param bsaetFee  余额
     * @return
     */
    @RequestMapping(value = "/update/fee", method = RequestMethod.POST)
    @ApiOperation(value = "修改余额", httpMethod = "POST", notes = "修改余额")
    public void updateFee(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            @ApiParam(value = "余额", required = true) @RequestParam(value = "bsaetFee", required = true) Double bsaetFee,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin updateFee");
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetUuid(bsaetUuid);
        busiAgent.setBsaetFee(bsaetFee);
        busiAgentService.updateBusiAgent(busiAgent);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiAgentController]:end updateFee");
    }

    /**
     * 删除
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiAgent(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin deleteBusiAgent");
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetUuid(bsaetUuid);
        busiAgent.setBsaetStatus(StatusEnum.DISABLE.getCode());
        busiAgentService.updateBusiAgent(busiAgent);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiAgentController]:end deleteBusiAgent");
    }

    /**
     * 批量删除
     *
     * @param bsaetUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiAgent(
            @ApiParam(value = "代理商标识集合(|拼接)", required = true) @RequestParam(value = "bsaetUuids", required = true) String bsaetUuids,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin deleteBatchBusiAgent");
        String[] uuids = bsaetUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiAgentService.updateBatchBusiAgentByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiAgentController]:end deleteBatchBusiAgent");
    }

    /**
     * 获取单个
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个", httpMethod = "GET", notes = "获取单个", response = BusiAgentVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiAgent(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin viewsBusiAgent");
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetUuid(bsaetUuid);
        busiAgent = busiAgentService.getBusiAgent(busiAgent);
        if (null == busiAgent) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "代理商不存在!"), response);
            logger.info("[BusiAgentController]:end viewsBusiAgent");
            return;
        }

        BusiAgentVO busiAgentVO = new BusiAgentVO();
        busiAgentVO.convertPOToVO(busiAgent);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiAgentVO), response);
        logger.info("[BusiAgentController]:end viewsBusiAgent");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取代理商列表", httpMethod = "GET", notes = "获取代理商列表", response = BusiAgentVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findBusiAgentList(
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin findBusiAgentList");
        List<BusiAgent> lists = busiAgentService.findBusiAgentList(null);
        List<BusiAgentVO> vos = new ArrayList<BusiAgentVO>();
        BusiAgentVO vo;
        for (BusiAgent busiAgent : lists) {
            vo = new BusiAgentVO();
            vo.convertPOToVO(busiAgent);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiAgentController]:end findBusiAgentList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bsaetCode 代理商名称
     * @param pageNum   页码
     * @param pageSize  页数
     * @return
     */
    @ApiOperation(value = "获取代理商分页列表", httpMethod = "GET", notes = "获取代理商分页列表", response = BusiAgentVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiAgentPage(
            @ApiParam(value = "代理商名称", required = false) @RequestParam(value = "bsaetCode", required = false) String bsaetCode,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiAgentController]:begin findBusiAgentPage");
        BusiAgent busiAgent = new BusiAgent();
        busiAgent.setBsaetCode(bsaetCode);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiAgent> page = busiAgentService.findBusiAgentPage(busiAgent, pageNum, pageSize);
        Page<BusiAgentVO> pageVO = new Page<BusiAgentVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiAgentVO> vos = new ArrayList<BusiAgentVO>();
        BusiAgentVO vo;
        for (BusiAgent busiAgentPO : page.getResult()) {
            vo = new BusiAgentVO();
            vo.convertPOToVO(busiAgentPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiAgentController]:end findBusiAgentPage");
    }

}