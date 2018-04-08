package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreRechargeRecord;

import java.util.List;

/**
 * 充值记录表
 */
public interface CoreRechargeRecordService {

    /**
     * 添加
     *
     * @param coreRechargeRecord
     * @return
     */
    public boolean insertCoreRechargeRecord(CoreRechargeRecord coreRechargeRecord);

    /**
     * 修改
     *
     * @param coreRechargeRecord
     * @return
     */
    public boolean updateCoreRechargeRecord(CoreRechargeRecord coreRechargeRecord);

    /**
     * 查询
     *
     * @param coreRechargeRecord
     * @return
     */
    public CoreRechargeRecord getCoreRechargeRecord(CoreRechargeRecord coreRechargeRecord);

    /**
     * 根据状态获取充值总额
     * @param crrerStatus
     * @return
     */
    public Double getCoreRechargeRecordSumByTotal(int crrerStatus);

    /**
     * 根据状态获取当月充值
     * @param crrerStatus
     * @return
     */
    public Double getCoreRechargeRecordSumByMonth(int crrerStatus);

    /**
     * 根据状态获取当日充值
     * @param crrerStatus
     * @return
     */
    public Double getCoreRechargeRecordSumByDay(int crrerStatus);

    /**
     *  获取我的所有充值所有page
     *
     * @return List
     */
    public Page<CoreRechargeRecord> findCoreRechargeRecordForMy(String crrerUser, int pageNum, int pageSize);

    /**
     * 后台查询所有Page
     *
     * @return Page
     */
    public Page<CoreRechargeRecord> findCoreRechargeRecordPage(CoreRechargeRecord coreRechargeRecord, String mobile, int pageNum, int pageSize);

}