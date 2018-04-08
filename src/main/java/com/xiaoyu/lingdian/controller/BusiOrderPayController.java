package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;

import com.xiaoyu.lingdian.service.BusiOrderPayService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiOrderPay;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiOrderPayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付记录表
 */
@Controller
@RequestMapping(value = "/busiOrderPay")
@Api(value = "busiOrderPay", description = "支付记录相关操作")
public class BusiOrderPayController extends BaseController {

    /**
     * 支付记录表
     */
    @Autowired
    private BusiOrderPayService busiOrderPayService;

    /**
     * 添加
     *
     * @param bsopyOrder      交易单号
     * @param bsopyClientIp   客户端地址
     * @param bsopyPayChannel 支付渠道
     * @param bsopyTradeType  交易类型
     * @param bsopyUser       用户标识
     * @param bsopyReturnUrl  同步通知地址
     * @param bsopyPayParams  支付返回参数（返回用于前端页面支付参数）
     * @param bsopyResultCode 业务结果
     * @param bsopyErrorCode  错误代码
     * @param bsopyErrorMsg   错误描述
     * @param bsopyOutTradeNo 第三方单号
     * @param bsopyPayResult  支付结果
     * @param bsopyPayDate    支付时间
     * @param bsopyNotifyDate 通知时间
     * @param bsopyCdate      创建时间
     * @param bsopyUdate      更新时间
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/busiOrderPay", method = RequestMethod.POST)
    public void addBusiOrderPay(
            @ApiParam(value = "交易单号", required = true) @RequestParam(value = "bsopyOrder", required = true) String bsopyOrder,
            @ApiParam(value = "客户端地址", required = true) @RequestParam(value = "bsopyClientIp", required = true) String bsopyClientIp,
            @ApiParam(value = "支付渠道", required = true) @RequestParam(value = "bsopyPayChannel", required = true) String bsopyPayChannel,
            @ApiParam(value = "交易类型", required = true) @RequestParam(value = "bsopyTradeType", required = true) String bsopyTradeType,
            @ApiParam(value = "用户标识", required = true) @RequestParam(value = "bsopyUser", required = true) String bsopyUser,
            @ApiParam(value = "同步通知地址", required = true) @RequestParam(value = "bsopyReturnUrl", required = true) String bsopyReturnUrl,
            @ApiParam(value = "支付返回参数（返回用于前端页面支付参数）", required = true) @RequestParam(value = "bsopyPayParams", required = true) String bsopyPayParams,
            @ApiParam(value = "业务结果", required = true) @RequestParam(value = "bsopyResultCode", required = true) String bsopyResultCode,
            @ApiParam(value = "错误代码", required = true) @RequestParam(value = "bsopyErrorCode", required = true) String bsopyErrorCode,
            @ApiParam(value = "错误描述", required = true) @RequestParam(value = "bsopyErrorMsg", required = true) String bsopyErrorMsg,
            @ApiParam(value = "第三方单号", required = true) @RequestParam(value = "bsopyOutTradeNo", required = true) String bsopyOutTradeNo,
            @ApiParam(value = "支付结果", required = true) @RequestParam(value = "bsopyPayResult", required = true) String bsopyPayResult,
            @ApiParam(value = "支付时间", required = true) @RequestParam(value = "bsopyPayDate", required = true) Date bsopyPayDate,
            @ApiParam(value = "通知时间", required = true) @RequestParam(value = "bsopyNotifyDate", required = true) Date bsopyNotifyDate,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsopyCdate", required = true) Date bsopyCdate,
            @ApiParam(value = "更新时间", required = true) @RequestParam(value = "bsopyUdate", required = true) Date bsopyUdate,
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin addBusiOrderPay");
        BusiOrderPay busiOrderPay = new BusiOrderPay();
        String uuid = RandomUtil.generateString(16);
        busiOrderPay.setBsopyUuid(uuid);
        busiOrderPay.setBsopyOrder(bsopyOrder);
        busiOrderPay.setBsopyClientIp(bsopyClientIp);
        busiOrderPay.setBsopyPayChannel(bsopyPayChannel);
        busiOrderPay.setBsopyTradeType(bsopyTradeType);
        busiOrderPay.setBsopyUser(bsopyUser);
        busiOrderPay.setBsopyReturnUrl(bsopyReturnUrl);
        busiOrderPay.setBsopyPayParams(bsopyPayParams);
        busiOrderPay.setBsopyResultCode(bsopyResultCode);
        busiOrderPay.setBsopyErrorCode(bsopyErrorCode);
        busiOrderPay.setBsopyErrorMsg(bsopyErrorMsg);
        busiOrderPay.setBsopyOutTradeNo(bsopyOutTradeNo);
        busiOrderPay.setBsopyPayResult(bsopyPayResult);
        busiOrderPay.setBsopyPayDate(bsopyPayDate);
        busiOrderPay.setBsopyNotifyDate(bsopyNotifyDate);
        busiOrderPay.setBsopyCdate(bsopyCdate);
        busiOrderPay.setBsopyUdate(bsopyUdate);

        busiOrderPayService.insertBusiOrderPay(busiOrderPay);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[BusiOrderPayController]:end addBusiOrderPay");
    }

    /**
     * 修改
     *
     * @param bsopyUuid       标识UUID
     * @param bsopyOrder      交易单号
     * @param bsopyClientIp   客户端地址
     * @param bsopyPayChannel 支付渠道
     * @param bsopyTradeType  交易类型
     * @param bsopyUser       用户标识
     * @param bsopyReturnUrl  同步通知地址
     * @param bsopyPayParams  支付返回参数（返回用于前端页面支付参数）
     * @param bsopyResultCode 业务结果
     * @param bsopyErrorCode  错误代码
     * @param bsopyErrorMsg   错误描述
     * @param bsopyOutTradeNo 第三方单号
     * @param bsopyPayResult  支付结果
     * @param bsopyPayDate    支付时间
     * @param bsopyNotifyDate 通知时间
     * @param bsopyCdate      创建时间
     * @param bsopyUdate      更新时间
     * @return
     */
    @RequestMapping(value = "/update/busiOrderPay", method = RequestMethod.POST)
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public void updateBusiOrderPay(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsopyUuid", required = true) String bsopyUuid,
            @ApiParam(value = "交易单号", required = true) @RequestParam(value = "bsopyOrder", required = true) String bsopyOrder,
            @ApiParam(value = "客户端地址", required = true) @RequestParam(value = "bsopyClientIp", required = true) String bsopyClientIp,
            @ApiParam(value = "支付渠道", required = true) @RequestParam(value = "bsopyPayChannel", required = true) String bsopyPayChannel,
            @ApiParam(value = "交易类型", required = true) @RequestParam(value = "bsopyTradeType", required = true) String bsopyTradeType,
            @ApiParam(value = "用户标识", required = true) @RequestParam(value = "bsopyUser", required = true) String bsopyUser,
            @ApiParam(value = "同步通知地址", required = true) @RequestParam(value = "bsopyReturnUrl", required = true) String bsopyReturnUrl,
            @ApiParam(value = "支付返回参数（返回用于前端页面支付参数）", required = true) @RequestParam(value = "bsopyPayParams", required = true) String bsopyPayParams,
            @ApiParam(value = "业务结果", required = true) @RequestParam(value = "bsopyResultCode", required = true) String bsopyResultCode,
            @ApiParam(value = "错误代码", required = true) @RequestParam(value = "bsopyErrorCode", required = true) String bsopyErrorCode,
            @ApiParam(value = "错误描述", required = true) @RequestParam(value = "bsopyErrorMsg", required = true) String bsopyErrorMsg,
            @ApiParam(value = "第三方单号", required = true) @RequestParam(value = "bsopyOutTradeNo", required = true) String bsopyOutTradeNo,
            @ApiParam(value = "支付结果", required = true) @RequestParam(value = "bsopyPayResult", required = true) String bsopyPayResult,
            @ApiParam(value = "支付时间", required = true) @RequestParam(value = "bsopyPayDate", required = true) Date bsopyPayDate,
            @ApiParam(value = "通知时间", required = true) @RequestParam(value = "bsopyNotifyDate", required = true) Date bsopyNotifyDate,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsopyCdate", required = true) Date bsopyCdate,
            @ApiParam(value = "更新时间", required = true) @RequestParam(value = "bsopyUdate", required = true) Date bsopyUdate,
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin updateBusiOrderPay");
        BusiOrderPay busiOrderPay = new BusiOrderPay();
        busiOrderPay.setBsopyUuid(bsopyUuid);
        busiOrderPay.setBsopyOrder(bsopyOrder);
        busiOrderPay.setBsopyClientIp(bsopyClientIp);
        busiOrderPay.setBsopyPayChannel(bsopyPayChannel);
        busiOrderPay.setBsopyTradeType(bsopyTradeType);
        busiOrderPay.setBsopyUser(bsopyUser);
        busiOrderPay.setBsopyReturnUrl(bsopyReturnUrl);
        busiOrderPay.setBsopyPayParams(bsopyPayParams);
        busiOrderPay.setBsopyResultCode(bsopyResultCode);
        busiOrderPay.setBsopyErrorCode(bsopyErrorCode);
        busiOrderPay.setBsopyErrorMsg(bsopyErrorMsg);
        busiOrderPay.setBsopyOutTradeNo(bsopyOutTradeNo);
        busiOrderPay.setBsopyPayResult(bsopyPayResult);
        busiOrderPay.setBsopyPayDate(bsopyPayDate);
        busiOrderPay.setBsopyNotifyDate(bsopyNotifyDate);
        busiOrderPay.setBsopyCdate(bsopyCdate);
        busiOrderPay.setBsopyUdate(bsopyUdate);

        busiOrderPayService.updateBusiOrderPay(busiOrderPay);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiOrderPayController]:end updateBusiOrderPay");
    }

    /**
     * 删除
     *
     * @param bsopyUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiOrderPay(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsopyUuid", required = true) String bsopyUuid,
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin deleteBusiOrderPay");
        BusiOrderPay busiOrderPay = new BusiOrderPay();
        busiOrderPay.setBsopyUuid(bsopyUuid);

        busiOrderPayService.deleteBusiOrderPay(busiOrderPay);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiOrderPayController]:end deleteBusiOrderPay");
    }

    /**
     * 批量删除
     *
     * @param bsopyUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiOrderPay(
            @ApiParam(value = "支付记录标识集合(|拼接)", required = true) @RequestParam(value = "bsopyUuids", required = true) String bsopyUuids,
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin deleteBatchBusiOrderPay");
        String[] uuids = bsopyUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiOrderPayService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiOrderPayController]:end deleteBatchBusiOrderPay");
    }

    /**
     * 获取单个
     *
     * @param bsopyUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个", httpMethod = "GET", notes = "获取单个", response = BusiOrderPayVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiOrderPay(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsopyUuid", required = true) String bsopyUuid,
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin viewsBusiOrderPay");
        BusiOrderPay busiOrderPay = new BusiOrderPay();
        busiOrderPay.setBsopyUuid(bsopyUuid);
        busiOrderPay = busiOrderPayService.getBusiOrderPay(busiOrderPay);
        if (null == busiOrderPay) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "支付记录不存在!"), response);
            logger.info("[BusiOrderPayController]:end viewsBusiOrderPay");
            return;
        }

        BusiOrderPayVO busiOrderPayVO = new BusiOrderPayVO();
        busiOrderPayVO.convertPOToVO(busiOrderPay);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiOrderPayVO), response);
        logger.info("[BusiOrderPayController]:end viewsBusiOrderPay");
    }

    /**
     * 获取列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取支付记录列表", httpMethod = "GET", notes = "获取支付记录列表", response = BusiOrderPayVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findBusiOrderPayList(
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin findBusiOrderPayList");
        List<BusiOrderPay> lists = busiOrderPayService.findBusiOrderPayList();
        List<BusiOrderPayVO> vos = new ArrayList<BusiOrderPayVO>();
        BusiOrderPayVO vo;
        for (BusiOrderPay busiOrderPay : lists) {
            vo = new BusiOrderPayVO();
            vo.convertPOToVO(busiOrderPay);
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[BusiOrderPayController]:end findBusiOrderPayList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param bsopyOrder      交易单号
     * @param bsopyClientIp   客户端地址
     * @param bsopyPayChannel 支付渠道
     * @param bsopyTradeType  交易类型
     * @param bsopyUser       用户标识
     * @param bsopyReturnUrl  同步通知地址
     * @param bsopyPayParams  支付返回参数（返回用于前端页面支付参数）
     * @param bsopyResultCode 业务结果
     * @param bsopyErrorCode  错误代码
     * @param bsopyErrorMsg   错误描述
     * @param bsopyOutTradeNo 第三方单号
     * @param bsopyPayResult  支付结果
     * @param bsopyPayDate    支付时间
     * @param bsopyNotifyDate 通知时间
     * @param bsopyCdate      创建时间
     * @param bsopyUdate      更新时间
     * @param pageNum         页码
     * @param pageSize        页数
     * @return
     */
    @ApiOperation(value = "获取支付记录分页列表", httpMethod = "GET", notes = "获取支付记录分页列表", response = BusiOrderPayVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiOrderPayPage(
            @ApiParam(value = "交易单号", required = true) @RequestParam(value = "bsopyOrder", required = true) String bsopyOrder,
            @ApiParam(value = "客户端地址", required = true) @RequestParam(value = "bsopyClientIp", required = true) String bsopyClientIp,
            @ApiParam(value = "支付渠道", required = true) @RequestParam(value = "bsopyPayChannel", required = true) String bsopyPayChannel,
            @ApiParam(value = "交易类型", required = true) @RequestParam(value = "bsopyTradeType", required = true) String bsopyTradeType,
            @ApiParam(value = "用户标识", required = true) @RequestParam(value = "bsopyUser", required = true) String bsopyUser,
            @ApiParam(value = "同步通知地址", required = true) @RequestParam(value = "bsopyReturnUrl", required = true) String bsopyReturnUrl,
            @ApiParam(value = "支付返回参数（返回用于前端页面支付参数）", required = true) @RequestParam(value = "bsopyPayParams", required = true) String bsopyPayParams,
            @ApiParam(value = "业务结果", required = true) @RequestParam(value = "bsopyResultCode", required = true) String bsopyResultCode,
            @ApiParam(value = "错误代码", required = true) @RequestParam(value = "bsopyErrorCode", required = true) String bsopyErrorCode,
            @ApiParam(value = "错误描述", required = true) @RequestParam(value = "bsopyErrorMsg", required = true) String bsopyErrorMsg,
            @ApiParam(value = "第三方单号", required = true) @RequestParam(value = "bsopyOutTradeNo", required = true) String bsopyOutTradeNo,
            @ApiParam(value = "支付结果", required = true) @RequestParam(value = "bsopyPayResult", required = true) String bsopyPayResult,
            @ApiParam(value = "支付时间", required = true) @RequestParam(value = "bsopyPayDate", required = true) Date bsopyPayDate,
            @ApiParam(value = "通知时间", required = true) @RequestParam(value = "bsopyNotifyDate", required = true) Date bsopyNotifyDate,
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "bsopyCdate", required = true) Date bsopyCdate,
            @ApiParam(value = "更新时间", required = true) @RequestParam(value = "bsopyUdate", required = true) Date bsopyUdate,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiOrderPayController]:begin findBusiOrderPayPage");
        BusiOrderPay busiOrderPay = new BusiOrderPay();
        busiOrderPay.setBsopyOrder(bsopyOrder);
        busiOrderPay.setBsopyClientIp(bsopyClientIp);
        busiOrderPay.setBsopyPayChannel(bsopyPayChannel);
        busiOrderPay.setBsopyTradeType(bsopyTradeType);
        busiOrderPay.setBsopyUser(bsopyUser);
        busiOrderPay.setBsopyReturnUrl(bsopyReturnUrl);
        busiOrderPay.setBsopyPayParams(bsopyPayParams);
        busiOrderPay.setBsopyResultCode(bsopyResultCode);
        busiOrderPay.setBsopyErrorCode(bsopyErrorCode);
        busiOrderPay.setBsopyErrorMsg(bsopyErrorMsg);
        busiOrderPay.setBsopyOutTradeNo(bsopyOutTradeNo);
        busiOrderPay.setBsopyPayResult(bsopyPayResult);
        busiOrderPay.setBsopyPayDate(bsopyPayDate);
        busiOrderPay.setBsopyNotifyDate(bsopyNotifyDate);
        busiOrderPay.setBsopyCdate(bsopyCdate);
        busiOrderPay.setBsopyUdate(bsopyUdate);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiOrderPay> page = busiOrderPayService.findBusiOrderPayPage(busiOrderPay, pageNum, pageSize);
        Page<BusiOrderPayVO> pageVO = new Page<BusiOrderPayVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiOrderPayVO> vos = new ArrayList<BusiOrderPayVO>();
        BusiOrderPayVO vo;
        for (BusiOrderPay busiOrderPayPO : page.getResult()) {
            vo = new BusiOrderPayVO();
            vo.convertPOToVO(busiOrderPayPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiOrderPayController]:end findBusiOrderPayPage");
    }

}