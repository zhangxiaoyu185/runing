package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.service.*;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 数据统计
 */
@Controller
@RequestMapping(value = "/total")
@Api(value = "total", description = "数据统计相关操作")
public class TotalDataController extends BaseController {

    /**
     * 佣金记录表
     */
    @Autowired
    private CoreCommissionRecordService coreCommissionRecordService;

    /**
     * 用户提现记录表
     */
    @Autowired
    private BusiCashRecordService busiCashRecordService;

    /**
     * 充值记录表
     */
    @Autowired
    private CoreRechargeRecordService coreRechargeRecordService;

    /**
     * 优惠券表
     */
    @Autowired
    private CoreCouponService coreCouponService;

    /**
     * 数据统计
     *
     * @return
     */
    @ApiOperation(value = "数据统计", httpMethod = "GET", notes = "数据统计", response = CoreUserVO.class)
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public void totalData(
            HttpServletResponse response) {
        logger.info("[TotalDataController]:begin totalData");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sysYUseCount", this.coreCouponService.getCoreCouponCountByTotal(1, 1));
        map.put("sysYUseSum", this.coreCouponService.getCoreCouponSumByTotal(1, 1));
        map.put("sysKUseCount", this.coreCouponService.getCoreCouponCountByTotal(1, 2));
        map.put("sysKUseSum", this.coreCouponService.getCoreCouponSumByTotal(1, 2));
        map.put("sysYGUseCount", this.coreCouponService.getCoreCouponCountByTotal(1, 3));
        map.put("sysYGUseSum", this.coreCouponService.getCoreCouponSumByTotal(1, 3));
        map.put("sysWUseCount", this.coreCouponService.getCoreCouponCountByTotal(1, 4));
        map.put("sysWUseSum", this.coreCouponService.getCoreCouponSumByTotal(1, 4));

        map.put("manualYUseCount", this.coreCouponService.getCoreCouponCountByTotal(2, 1));
        map.put("manualYUseSum", this.coreCouponService.getCoreCouponSumByTotal(2, 1));
        map.put("manualKUseCount", this.coreCouponService.getCoreCouponCountByTotal(2, 2));
        map.put("manualKUseSum", this.coreCouponService.getCoreCouponSumByTotal(2, 2));
        map.put("manualYGUseCount", this.coreCouponService.getCoreCouponCountByTotal(2, 3));
        map.put("manualYGUseSum", this.coreCouponService.getCoreCouponSumByTotal(2, 3));
        map.put("manualWUseCount", this.coreCouponService.getCoreCouponCountByTotal(2, 4));
        map.put("manualWUseSum", this.coreCouponService.getCoreCouponSumByTotal(2, 4));

        map.put("dayCash", this.busiCashRecordService.getBusiCashRecordSumByDay(3));
        map.put("monthCash", this.busiCashRecordService.getBusiCashRecordSumByMonth(3));
        map.put("totalCash", this.busiCashRecordService.getBusiCashRecordSumByTotal(3));

        map.put("dayCommission", this.coreCommissionRecordService.getCoreCommissionRecordSumByDay());
        map.put("monthCommission", this.coreCommissionRecordService.getCoreCommissionRecordSumByMonth());
        map.put("totalCommission", this.coreCommissionRecordService.getCoreCommissionRecordSumByTotal());

        map.put("dayRecharge", this.coreRechargeRecordService.getCoreRechargeRecordSumByDay(1));
        map.put("monthRecharge", this.coreRechargeRecordService.getCoreRechargeRecordSumByMonth(1));
        map.put("totalRecharge", this.coreRechargeRecordService.getCoreRechargeRecordSumByTotal(1));

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取数据统计成功", map), response);
        logger.info("[TotalDataController]:end totalData");
    }
}