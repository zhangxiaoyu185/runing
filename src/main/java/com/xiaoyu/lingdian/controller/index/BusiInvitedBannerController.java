package com.xiaoyu.lingdian.controller.index;

import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiInvitedBanner;
import com.xiaoyu.lingdian.enums.StatusEnum;
import com.xiaoyu.lingdian.service.BusiInvitedBannerService;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiInvitedBannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 邀请Banner表
 */
@Controller
@RequestMapping(value = "/busiInvitedBanner")
@Api(value = "busiInvitedBanner", description = "邀请Banner相关操作")
public class BusiInvitedBannerController extends BaseController {

    /**
     * 邀请Banner表
     */
    @Autowired
    private BusiInvitedBannerService busiInvitedBannerService;

    /**
     * 添加
     *
     * @param bsaetLink 链接
     * @param bsaetPic  封面图
     * @param bsaetOrd  顺序
     * @param bsaetDesc 描述
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiInvitedBanner", method = RequestMethod.POST)
    public void addBusiInvitedBanner(
            @ApiParam(value = "链接", required = true) @RequestParam(value = "bsaetLink", required = true) String bsaetLink,
            @ApiParam(value = "封面图", required = true) @RequestParam(value = "bsaetPic", required = true) String bsaetPic,
            @ApiParam(value = "顺序", required = true) @RequestParam(value = "bsaetOrd", required = true) Integer bsaetOrd,
            @ApiParam(value = "描述", required = false) @RequestParam(value = "bsaetDesc", required = false) String bsaetDesc,
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin addBusiInvitedBanner");
        BusiInvitedBanner busiInvitedBanner = new BusiInvitedBanner();
        String uuid = RandomUtil.generateString(16);
        busiInvitedBanner.setBsaetUuid(uuid);
        busiInvitedBanner.setBsaetLink(bsaetLink);
        busiInvitedBanner.setBsaetPic(bsaetPic);
        busiInvitedBanner.setBsaetOrd(bsaetOrd);
        busiInvitedBanner.setBsaetStatus(StatusEnum.ENABLE.getCode());
        busiInvitedBanner.setBsaetDesc(bsaetDesc);
        busiInvitedBanner.setBsaetCdate(new Date());
        busiInvitedBanner.setBsaetUdate(new Date());

        busiInvitedBannerService.insertBusiInvitedBanner(busiInvitedBanner);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiInvitedBannerController]:end addBusiInvitedBanner");
    }

    /**
     * 修改
     *
     * @param bsaetUuid 标识UUID
     * @param bsaetLink 链接
     * @param bsaetPic  封面图
     * @param bsaetOrd  顺序
     * @param bsaetDesc 描述
     * @return
     */
    @RequestMapping(value = "/update/busiInvitedBanner", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiInvitedBanner(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            @ApiParam(value = "链接", required = true) @RequestParam(value = "bsaetLink", required = true) String bsaetLink,
            @ApiParam(value = "封面图", required = true) @RequestParam(value = "bsaetPic", required = true) String bsaetPic,
            @ApiParam(value = "顺序", required = true) @RequestParam(value = "bsaetOrd", required = true) Integer bsaetOrd,
            @ApiParam(value = "描述", required = false) @RequestParam(value = "bsaetDesc", required = false) String bsaetDesc,
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin updateBusiInvitedBanner");
        BusiInvitedBanner busiInvitedBanner = new BusiInvitedBanner();
        busiInvitedBanner.setBsaetUuid(bsaetUuid);
        busiInvitedBanner.setBsaetLink(bsaetLink);
        busiInvitedBanner.setBsaetPic(bsaetPic);
        busiInvitedBanner.setBsaetOrd(bsaetOrd);
        busiInvitedBanner.setBsaetDesc(bsaetDesc);
        busiInvitedBanner.setBsaetUdate(new Date());

        busiInvitedBannerService.updateBusiInvitedBanner(busiInvitedBanner);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiInvitedBannerController]:end updateBusiInvitedBanner");
    }

    /**
     * 删除
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiInvitedBanner(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin deleteBusiInvitedBanner");
        BusiInvitedBanner busiInvitedBanner = new BusiInvitedBanner();
        busiInvitedBanner.setBsaetUuid(bsaetUuid);

        busiInvitedBannerService.deleteBusiInvitedBanner(busiInvitedBanner);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiInvitedBannerController]:end deleteBusiInvitedBanner");
    }

    /**
     * 批量删除
     *
     * @param bsaetUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiInvitedBanner(
            @ApiParam(value = "邀请Banner标识集合(|拼接)", required = true) @RequestParam(value = "bsaetUuids", required = true) String bsaetUuids,
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin deleteBatchBusiInvitedBanner");
        String[] uuids = bsaetUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiInvitedBannerService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiInvitedBannerController]:end deleteBatchBusiInvitedBanner");
    }

    /**
     * 获取单个邀请Banner
     *
     * @param bsaetUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个邀请Banner", httpMethod = "GET", notes = "获取单个邀请Banner", response = BusiInvitedBannerVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiInvitedBanner(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsaetUuid", required = true) String bsaetUuid,
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin viewsBusiInvitedBanner");
        BusiInvitedBanner busiInvitedBanner = new BusiInvitedBanner();
        busiInvitedBanner.setBsaetUuid(bsaetUuid);
        busiInvitedBanner = busiInvitedBannerService.getBusiInvitedBanner(busiInvitedBanner);
        if (null == busiInvitedBanner) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "邀请Banner不存在!"), response);
            logger.info("[BusiInvitedBannerController]:end viewsBusiInvitedBanner");
            return;
        }

        BusiInvitedBannerVO busiInvitedBannerVO = new BusiInvitedBannerVO();
        busiInvitedBannerVO.convertPOToVO(busiInvitedBanner);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个邀请Banner成功", busiInvitedBannerVO), response);
        logger.info("[BusiInvitedBannerController]:end viewsBusiInvitedBanner");
    }

    /**
     * 获取所有邀请Banner列表<List>
     *
     * @return
     */
    @ApiOperation(value = "(前台用)获取所有邀请Banner列表", httpMethod = "GET", notes = "(前台用)获取所有邀请Banner列表", response = BusiInvitedBannerVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findBusiInvitedBannerList(
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin findBusiInvitedBannerList");
        List<BusiInvitedBanner> lists = busiInvitedBannerService.findBusiInvitedBannerList();
        List<BusiInvitedBannerVO> vos = new ArrayList<BusiInvitedBannerVO>();
        BusiInvitedBannerVO vo;
        for (BusiInvitedBanner busiInvitedBanner : lists) {
            vo = new BusiInvitedBannerVO();
            vo.convertPOToVO(busiInvitedBanner);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiInvitedBannerController]:end findBusiInvitedBannerList");
    }

    /**
     * 获取邀请Banner分页列表<Page>
     *
     * @param pageNum  页码
     * @param pageSize 页数
     * @return
     */
    @ApiOperation(value = "获取邀请Banner分页列表", httpMethod = "GET", notes = "获取邀请Banner分页列表", response = BusiInvitedBannerVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiInvitedBannerPage(
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiInvitedBannerController]:begin findBusiInvitedBannerPage");
        BusiInvitedBanner busiInvitedBanner = new BusiInvitedBanner();
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiInvitedBanner> page = busiInvitedBannerService.findBusiInvitedBannerPage(busiInvitedBanner, pageNum, pageSize);
        Page<BusiInvitedBannerVO> pageVO = new Page<BusiInvitedBannerVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiInvitedBannerVO> vos = new ArrayList<BusiInvitedBannerVO>();
        BusiInvitedBannerVO vo;
        for (BusiInvitedBanner busiInvitedBannerPO : page.getResult()) {
            vo = new BusiInvitedBannerVO();
            vo.convertPOToVO(busiInvitedBannerPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiInvitedBannerController]:end findBusiInvitedBannerPage");
    }

}