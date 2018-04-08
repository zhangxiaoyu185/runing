package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.BusiAgent;
import com.xiaoyu.lingdian.service.BusiAgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.xiaoyu.lingdian.service.BusiAgentPayService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiAgentPay;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiAgentPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 代理商打款表
 */
@Controller
@RequestMapping(value = "/busiAgentPay")
@Api(value = "busiAgentPay", description = "代理商打款相关操作")
public class BusiAgentPayController extends BaseController {

    /**
     * 代理商打款表
     */
    @Autowired
    private BusiAgentPayService busiAgentPayService;

    /**
     * 代理商表
     */
    @Autowired
    private BusiAgentService busiAgentService;

    /**
     * 审核操作( 状态:1申请中2已驳回3已确认)
     *
     * @param bsapyUuid    标识UUID
     * @param bsapyStatus  状态:2驳回3确认
     * @return
     */
    @RequestMapping(value = "/update/busiAgentPay", method = RequestMethod.POST)
    @ApiOperation(value = "审核操作(", httpMethod = "POST", notes = "审核操作(")
    public void updateBusiAgentPay(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsapyUuid", required = true) String bsapyUuid,
            @ApiParam(value = "状态:2驳回3确认", required = true) @RequestParam(value = "bsapyStatus", required = true) Integer bsapyStatus,
            HttpServletResponse response) {
        logger.info("[BusiAgentPayController]:begin updateBusiAgentPay");
        if(bsapyStatus != 2 && bsapyStatus != 3) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请确认操作!"), response);
            logger.info("[BusiAgentPayController]:end updateBusiAgentPay");
            return;
        }
        BusiAgentPay busiAgentPay = new BusiAgentPay();
        busiAgentPay.setBsapyUuid(bsapyUuid);
        busiAgentPay = this.busiAgentPayService.getBusiAgentPay(busiAgentPay);
        if(null == busiAgentPay || busiAgentPay.getBsapyStatus() != 1) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "当前状态不正确,不能审核!"), response);
            logger.info("[BusiAgentPayController]:end updateBusiAgentPay");
            return;
        }
        busiAgentPay.setBsapyStatus(bsapyStatus);
        busiAgentPay.setBsapyUdate(new Date());
        busiAgentPayService.updateBusiAgentPay(busiAgentPay);
        if(3 == bsapyStatus){
            //确认,增加代理商的余额
            this.busiAgentService.updateBusiAgentByFee(busiAgentPay.getBsapyAgent(), busiAgentPay.getBsapyFee());
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "操作成功!"), response);
        logger.info("[BusiAgentPayController]:end updateBusiAgentPay");
    }

    /**
     * 获取单个
     *
     * @param bsapyUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个", httpMethod = "GET", notes = "获取单个", response = BusiAgentPayVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiAgentPay(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsapyUuid", required = true) String bsapyUuid,
            HttpServletResponse response) {
        logger.info("[BusiAgentPayController]:begin viewsBusiAgentPay");
        BusiAgentPay busiAgentPay = new BusiAgentPay();
        busiAgentPay.setBsapyUuid(bsapyUuid);
        busiAgentPay = busiAgentPayService.getBusiAgentPay(busiAgentPay);
        if (null == busiAgentPay) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "代理商打款记录不存在!"), response);
            logger.info("[BusiAgentPayController]:end viewsBusiAgentPay");
            return;
        }

        BusiAgentPayVO busiAgentPayVO = new BusiAgentPayVO();
        busiAgentPayVO.convertPOToVO(busiAgentPay);
        HashSet<String> hashAgentUuids = new HashSet<String>();
        if (null != busiAgentPayVO.getBsapyAgent()) {
            hashAgentUuids.add(busiAgentPayVO.getBsapyAgent());
        }

        List<String> agentUuids = new ArrayList<>(hashAgentUuids);
        Map<String, BusiAgent> agentMap = this.busiAgentService.findBusiAgentMapByUuidList(agentUuids);
        if (null != busiAgentPayVO.getBsapyAgent()) {
            busiAgentPayVO.setBsapyAgentCode(agentMap.get(busiAgentPayVO.getBsapyAgent()) == null ? null : agentMap.get(busiAgentPayVO.getBsapyAgent()).getBsaetCode());
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiAgentPayVO), response);
        logger.info("[BusiAgentPayController]:end viewsBusiAgentPay");
    }

    /**
     * 代理商的打款记录列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取代理商打款列表", httpMethod = "GET", notes = "获取代理商打款列表", response = BusiAgentPayVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findBusiAgentPayList(
            @ApiParam(value = "代理商标识", required = true) @RequestParam(value = "bsapyAgent", required = true) String bsapyAgent,
            HttpServletResponse response) {
        logger.info("[BusiAgentPayController]:begin findBusiAgentPayList");
        List<BusiAgentPay> lists = busiAgentPayService.findBusiAgentPayForListsByMy(bsapyAgent);
        List<BusiAgentPayVO> vos = new ArrayList<BusiAgentPayVO>();

        HashSet<String> hashAgentUuids = new HashSet<String>();
        for (BusiAgentPay newBusiAgentPay : lists) {
            if(null != newBusiAgentPay.getBsapyAgent()) {
                hashAgentUuids.add(newBusiAgentPay.getBsapyAgent());
            }
        }
        List<String> agentUuids = new ArrayList<>(hashAgentUuids);
        Map<String, BusiAgent> agentMap = this.busiAgentService.findBusiAgentMapByUuidList(agentUuids);

        BusiAgentPayVO vo;
        for (BusiAgentPay busiAgentPay : lists) {
            vo = new BusiAgentPayVO();
            vo.convertPOToVO(busiAgentPay);
            if(null != busiAgentPay.getBsapyAgent()) {
                vo.setBsapyAgentCode(agentMap.get(busiAgentPay.getBsapyAgent()) == null ? null : agentMap.get(busiAgentPay.getBsapyAgent()).getBsaetCode());
            }
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiAgentPayController]:end findBusiAgentPayList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bsapyAgent   代理商标识
     * @param bsapyOrder   线下汇款单号
     * @param bsapyStatus  状态:1申请中2已驳回3已确认
     * @param pageNum      页码
     * @param pageSize     页数
     * @return
     */
    @ApiOperation(value = "获取代理商打款分页列表", httpMethod = "GET", notes = "获取代理商打款分页列表", response = BusiAgentPayVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiAgentPayPage(
            @ApiParam(value = "代理商标识", required = false) @RequestParam(value = "bsapyAgent", required = false) String bsapyAgent,
            @ApiParam(value = "线下汇款单号", required = false) @RequestParam(value = "bsapyOrder", required = false) String bsapyOrder,
            @ApiParam(value = "状态:1申请中2已驳回3已确认", required = false) @RequestParam(value = "bsapyStatus", required = false) Integer bsapyStatus,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiAgentPayController]:begin findBusiAgentPayPage");
        BusiAgentPay busiAgentPay = new BusiAgentPay();
        busiAgentPay.setBsapyAgent(bsapyAgent);
        busiAgentPay.setBsapyOrder(bsapyOrder);
        busiAgentPay.setBsapyStatus(bsapyStatus);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiAgentPay> page = busiAgentPayService.findBusiAgentPayPage(busiAgentPay, pageNum, pageSize);
        Page<BusiAgentPayVO> pageVO = new Page<BusiAgentPayVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashAgentUuids = new HashSet<String>();
        for (BusiAgentPay newBusiAgentPay : page.getResult()) {
            if(null != newBusiAgentPay.getBsapyAgent()) {
                hashAgentUuids.add(newBusiAgentPay.getBsapyAgent());
            }
        }
        List<String> agentUuids = new ArrayList<>(hashAgentUuids);
        Map<String, BusiAgent> agentMap = this.busiAgentService.findBusiAgentMapByUuidList(agentUuids);

        List<BusiAgentPayVO> vos = new ArrayList<BusiAgentPayVO>();
        BusiAgentPayVO vo;
        for (BusiAgentPay busiAgentPayPO : page.getResult()) {
            vo = new BusiAgentPayVO();
            vo.convertPOToVO(busiAgentPayPO);
            if(null != busiAgentPayPO.getBsapyAgent()) {
                vo.setBsapyAgentCode(agentMap.get(busiAgentPayPO.getBsapyAgent()) == null ? null : agentMap.get(busiAgentPayPO.getBsapyAgent()).getBsaetCode());
            }
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiAgentPayController]:end findBusiAgentPayPage");
    }

}