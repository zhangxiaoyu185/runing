package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiCashRecord;

import java.util.List;

/**
 * 用户提现记录表
 */
public interface BusiCashRecordService {

    /**
     * 添加
     *
     * @param busiCashRecord
     * @return
     */
    public boolean insertBusiCashRecord(BusiCashRecord busiCashRecord);

    /**
     * 修改
     *
     * @param busiCashRecord
     * @return
     */
    public boolean updateBusiCashRecord(BusiCashRecord busiCashRecord);

    /**
     * 查询
     *
     * @param busiCashRecord
     * @return
     */
    public BusiCashRecord getBusiCashRecord(BusiCashRecord busiCashRecord);

    /**
     * 根据状态获取提现总额
     * @param bscrdStatus
     * @return
     */
    public Double getBusiCashRecordSumByTotal(int bscrdStatus);

    /**
     * 根据状态获取当月提现
     * @param bscrdStatus
     * @return
     */
    public Double getBusiCashRecordSumByMonth(int bscrdStatus);

    /**
     * 根据状态获取当日提现
     * @param bscrdStatus
     * @return
     */
    public Double getBusiCashRecordSumByDay(int bscrdStatus);

    /**
     * 获取我的提现记录page
     * @param bscrdExtracted
     * @param bscrdStatus
     * @return Page
     */
    public Page<BusiCashRecord> findBusiCashRecordForPagesByMy(String bscrdExtracted, int bscrdStatus, int pageNum, int pageSize);

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiCashRecord> findBusiCashRecordPage(BusiCashRecord busiCashRecord, int pageNum, int pageSize);

}