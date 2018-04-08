package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiOrderPay;

import java.util.List;

/**
 * 支付记录表
 */
public interface BusiOrderPayService {

    /**
     * 添加
     *
     * @param busiOrderPay
     * @return
     */
    public boolean insertBusiOrderPay(BusiOrderPay busiOrderPay);

    /**
     * 修改
     *
     * @param busiOrderPay
     * @return
     */
    public boolean updateBusiOrderPay(BusiOrderPay busiOrderPay);

    /**
     * 删除
     *
     * @param busiOrderPay
     * @return
     */
    public boolean deleteBusiOrderPay(BusiOrderPay busiOrderPay);

    /**
     * 批量删除
     *
     * @param list
     * @return boolean
     */
    public boolean deleteBatchByIds(List<String> list);

    /**
     * 查询
     *
     * @param busiOrderPay
     * @return
     */
    public BusiOrderPay getBusiOrderPay(BusiOrderPay busiOrderPay);

    /**
     * 查询所有List
     *
     * @return List
     */
    public List<BusiOrderPay> findBusiOrderPayList();

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiOrderPay> findBusiOrderPayPage(BusiOrderPay busiOrderPay, int pageNum, int pageSize);

}