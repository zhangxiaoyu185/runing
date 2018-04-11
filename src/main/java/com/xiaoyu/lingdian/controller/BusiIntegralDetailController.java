package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.service.CoreUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.BusiIntegralDetailService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiIntegralDetail;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiIntegralDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 积分明细表
 */
@Controller
@RequestMapping(value = "/busiIntegralDetail")
@Api(value = "busiIntegralDetail", description = "积分明细相关操作")
public class BusiIntegralDetailController extends BaseController {

    /**
     * 积分明细表
     */
    @Autowired
    private BusiIntegralDetailService busiIntegralDetailService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 修改积分,添加积分明细
     *
     * @param bsidlDire   增减方向(1增2减)
     * @param bsidlNum    数值
     * @param bsidlRemark 备注
     * @param bsidlUser   被修改人
     * @param bsidlOper   操作人
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiIntegralDetail", method = RequestMethod.POST)
    public void addBusiIntegralDetail(
            @ApiParam(value = "增减方向(1增2减)", required = true) @RequestParam(value = "bsidlDire", required = true) Integer bsidlDire,
            @ApiParam(value = "数值", required = true) @RequestParam(value = "bsidlNum", required = true) Integer bsidlNum,
            @ApiParam(value = "备注", required = true) @RequestParam(value = "bsidlRemark", required = true) String bsidlRemark,
            @ApiParam(value = "被修改人", required = true) @RequestParam(value = "bsidlUser", required = true) String bsidlUser,
            @ApiParam(value = "操作人", required = false) @RequestParam(value = "bsidlOper", required = false) String bsidlOper,
            HttpServletResponse response) {
        logger.info("[BusiIntegralDetailController]:begin addBusiIntegralDetail");
        if(bsidlDire == 2) {
            coreUserService.updateCoreUserByIntegral(bsidlUser, -bsidlNum);
        } else {
            coreUserService.updateCoreUserByIntegral(bsidlUser, bsidlNum);
        }
        BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
        String uuid = RandomUtil.generateString(16);
        busiIntegralDetail.setBsidlUuid(uuid);
        busiIntegralDetail.setBsidlDire(bsidlDire);
        busiIntegralDetail.setBsidlNum(bsidlNum);
        busiIntegralDetail.setBsidlRemark(bsidlRemark);
        busiIntegralDetail.setBsidlUser(bsidlUser);
        busiIntegralDetail.setBsidlCdate(new Date());
        busiIntegralDetail.setBsidlOper(bsidlOper);
        busiIntegralDetailService.insertBusiIntegralDetail(busiIntegralDetail);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改积分成功!"), response);
        logger.info("[BusiIntegralDetailController]:end addBusiIntegralDetail");
    }

    /**
     * 获取单个
     *
     * @param bsidlUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个", httpMethod = "POST", notes = "获取单个", response = BusiIntegralDetailVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.POST)
    public void viewsBusiIntegralDetail(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsidlUuid", required = true) String bsidlUuid,
            HttpServletResponse response) {
        logger.info("[BusiIntegralDetailController]:begin viewsBusiIntegralDetail");
        BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
        busiIntegralDetail.setBsidlUuid(bsidlUuid);
        busiIntegralDetail = busiIntegralDetailService.getBusiIntegralDetail(busiIntegralDetail);
        if (null == busiIntegralDetail) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "积分明细不存在!"), response);
            logger.info("[BusiIntegralDetailController]:end viewsBusiIntegralDetail");
            return;
        }

        BusiIntegralDetailVO busiIntegralDetailVO = new BusiIntegralDetailVO();
        busiIntegralDetailVO.convertPOToVO(busiIntegralDetail);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiIntegralDetailVO), response);
        logger.info("[BusiIntegralDetailController]:end viewsBusiIntegralDetail");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bsidlDire   增减方向(1增2减)
     * @param name    用户昵称
     * @param bsidlRemark 备注
     * @param pageNum     页码
     * @param pageSize    页数
     * @return
     */
    @ApiOperation(value = "获取积分明细分页列表", httpMethod = "POST", notes = "获取积分明细分页列表", response = BusiIntegralDetailVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiIntegralDetailPage(
            @ApiParam(value = "增减方向(1增2减)", required = true) @RequestParam(value = "bsidlDire", required = true) Integer bsidlDire,
            @ApiParam(value = "用户昵称", required = true) @RequestParam(value = "name", required = true) String name,
            @ApiParam(value = "备注", required = true) @RequestParam(value = "bsidlRemark", required = true) String bsidlRemark,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiIntegralDetailController]:begin findBusiIntegralDetailPage");
        BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
        busiIntegralDetail.setBsidlDire(bsidlDire);
        busiIntegralDetail.setBsidlRemark(bsidlRemark);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiIntegralDetail> page = busiIntegralDetailService.findBusiIntegralDetailPage(busiIntegralDetail, name, pageNum, pageSize);
        Page<BusiIntegralDetailVO> pageVO = new Page<BusiIntegralDetailVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiIntegralDetailVO> vos = new ArrayList<BusiIntegralDetailVO>();
        BusiIntegralDetailVO vo;
        for (BusiIntegralDetail busiIntegralDetailPO : page.getResult()) {
            vo = new BusiIntegralDetailVO();
            vo.convertPOToVO(busiIntegralDetailPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiIntegralDetailController]:end findBusiIntegralDetailPage");
    }

}