package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreCommissionRecord;

/**
 * 佣金记录表
 */
public interface CoreCommissionRecordService {

    /**
     * 添加
     *
     * @param coreCommissionRecord
     * @return
     */
    public boolean insertCoreCommissionRecord(CoreCommissionRecord coreCommissionRecord);

    /**
     * 修改
     *
     * @param coreCommissionRecord
     * @return
     */
    public boolean updateCoreCommissionRecord(CoreCommissionRecord coreCommissionRecord);

    /**
     * 查询
     *
     * @param coreCommissionRecord
     * @return
     */
    public CoreCommissionRecord getCoreCommissionRecord(CoreCommissionRecord coreCommissionRecord);

    /**
     * 获取佣金总额
     * @return
     */
    public Double getCoreCommissionRecordSumByTotal();

    /**
     * 获取当月佣金
     * @return
     */
    public Double getCoreCommissionRecordSumByMonth();

    /**
     * 获取当日佣金
     * @return
     */
    public Double getCoreCommissionRecordSumByDay();

    /**
     * 获取我的佣金所有page
     *
     * @return Page
     */
    public Page<CoreCommissionRecord> findCoreCommissionRecordForMy(String crcrdBeUser, int pageNum, int pageSize);

    /**
     * 后台查询所有Page
     *
     * @return Page
     */
    public Page<CoreCommissionRecord> findCoreCommissionRecordPage(CoreCommissionRecord coreCommissionRecord, String rechargemobile, String mobile, int pageNum, int pageSize);

}