package com.xiaoyu.lingdian.controller.index;

import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.enums.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.BusiAdvertService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiAdvert;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiAdvertVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 油卡充值Banner表
 */
@Controller
@RequestMapping(value = "/busiAdvert")
@Api(value = "busiAdvert", description = "油卡充值Banner相关操作")
public class BusiAdvertController extends BaseController {

    /**
     * 油卡充值Banner表
     */
    @Autowired
    private BusiAdvertService busiAdvertService;

    /**
     * 添加
     *
     * @param bsaetLink 链接
     * @param bsaetPic  广告图
     * @param bsaetOrd  顺序
     * @param bsaetDesc 描述
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiAdvert", method = RequestMethod.POST)
    public void addBusiAdvert(
            @ApiParam(value = "链接", required = true) @RequestParam(value = "bsaetLink", required = true) String bsaetLink,
            @ApiParam(value = "广告图", required = true) @RequestParam(value = "bsaetPic", required = true) String bsaetPic,
            @ApiParam(value = "顺序", required = true) @RequestParam(value = "bsaetOrd", required = true) Integer bsaetOrd,
            @ApiParam(value = "描述", required = false) @RequestParam(value = "bsaetDesc", required = false) String bsaetDesc,
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin addBusiAdvert");
        BusiAdvert busiAdvert = new BusiAdvert();
        String uuid = RandomUtil.generateString(16);
        busiAdvert.setBsaetUuid(uuid);
        busiAdvert.setBsaetLink(bsaetLink);
        busiAdvert.setBsaetPic(bsaetPic);
        busiAdvert.setBsaetOrd(bsaetOrd);
        busiAdvert.setBsaetStatus(StatusEnum.ENABLE.getCode());
        busiAdvert.setBsaetDesc(bsaetDesc);
        busiAdvert.setBsaetCdate(new Date());
        busiAdvert.setBsaetUdate(new Date());

        busiAdvertService.insertBusiAdvert(busiAdvert);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiAdvertController]:end addBusiAdvert");
    }

    /**
     * 修改
     *
     * @param bsaetUuid 标识UUID
     * @param bsaetLink 链接
     * @param bsaetPic  广告图
     * @param bsaetOrd  顺序
     * @param bsaetDesc 描述
     * @return
     */
    @RequestMapping(value = "/update/busiAdvert", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiAdvert(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            @ApiParam(value = "链接", required = true) @RequestParam(value = "bsaetLink", required = true) String bsaetLink,
            @ApiParam(value = "广告图", required = true) @RequestParam(value = "bsaetPic", required = true) String bsaetPic,
            @ApiParam(value = "顺序", required = true) @RequestParam(value = "bsaetOrd", required = true) Integer bsaetOrd,
            @ApiParam(value = "描述", required = false) @RequestParam(value = "bsaetDesc", required = false) String bsaetDesc,
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin updateBusiAdvert");
        BusiAdvert busiAdvert = new BusiAdvert();
        busiAdvert.setBsaetUuid(bsaetUuid);
        busiAdvert.setBsaetLink(bsaetLink);
        busiAdvert.setBsaetPic(bsaetPic);
        busiAdvert.setBsaetOrd(bsaetOrd);
        busiAdvert.setBsaetDesc(bsaetDesc);
        busiAdvert.setBsaetUdate(new Date());

        busiAdvertService.updateBusiAdvert(busiAdvert);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiAdvertController]:end updateBusiAdvert");
    }

    /**
     * 删除
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiAdvert(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin deleteBusiAdvert");
        BusiAdvert busiAdvert = new BusiAdvert();
        busiAdvert.setBsaetUuid(bsaetUuid);

        busiAdvertService.deleteBusiAdvert(busiAdvert);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiAdvertController]:end deleteBusiAdvert");
    }

    /**
     * 批量删除
     *
     * @param bsaetUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiAdvert(
            @ApiParam(value = "油卡充值Banner标识集合(|拼接)", required = true) @RequestParam(value = "bsaetUuids", required = true) String bsaetUuids,
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin deleteBatchBusiAdvert");
        String[] uuids = bsaetUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiAdvertService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiAdvertController]:end deleteBatchBusiAdvert");
    }

    /**
     * 获取单个油卡充值Banner
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个油卡充值Banner", httpMethod = "GET", notes = "获取单个油卡充值Banner", response = BusiAdvertVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiAdvert(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin viewsBusiAdvert");
        BusiAdvert busiAdvert = new BusiAdvert();
        busiAdvert.setBsaetUuid(bsaetUuid);
        busiAdvert = busiAdvertService.getBusiAdvert(busiAdvert);
        if (null == busiAdvert) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "油卡充值Banner不存在!"), response);
            logger.info("[BusiAdvertController]:end viewsBusiAdvert");
            return;
        }

        BusiAdvertVO busiAdvertVO = new BusiAdvertVO();
        busiAdvertVO.convertPOToVO(busiAdvert);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个油卡充值Banner成功", busiAdvertVO), response);
        logger.info("[BusiAdvertController]:end viewsBusiAdvert");
    }

    /**
     * 获取所有油卡充值Banner列表<List>
     *
     * @return
     */
    @ApiOperation(value = "(前台用)获取所有油卡充值Banner列表", httpMethod = "GET", notes = "(前台用)获取所有油卡充值Banner列表", response = BusiAdvertVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findBusiAdvertList(
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin findBusiAdvertList");
        List<BusiAdvert> lists = busiAdvertService.findBusiAdvertList();
        List<BusiAdvertVO> vos = new ArrayList<BusiAdvertVO>();
        BusiAdvertVO vo;
        for (BusiAdvert busiAdvert : lists) {
            vo = new BusiAdvertVO();
            vo.convertPOToVO(busiAdvert);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiAdvertController]:end findBusiAdvertList");
    }

    /**
     * 获取油卡充值Banner分页列表<Page>
     *
     * @param pageNum  页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value = "获取油卡充值Banner分页列表", httpMethod = "GET", notes = "获取油卡充值Banner分页列表", response = BusiAdvertVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiAdvertPage(
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiAdvertController]:begin findBusiAdvertPage");
        BusiAdvert busiAdvert = new BusiAdvert();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiAdvert> page = busiAdvertService.findBusiAdvertPage(busiAdvert, pageNum, pageSize);
        Page<BusiAdvertVO> pageVO = new Page<BusiAdvertVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiAdvertVO> vos = new ArrayList<BusiAdvertVO>();
        BusiAdvertVO vo;
        for (BusiAdvert busiAdvertPO : page.getResult()) {
            vo = new BusiAdvertVO();
            vo.convertPOToVO(busiAdvertPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiAdvertController]:end findBusiAdvertPage");
    }

}