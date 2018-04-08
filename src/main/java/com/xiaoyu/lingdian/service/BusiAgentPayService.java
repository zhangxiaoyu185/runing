package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiAgentPay;

import java.util.List;

/**
 * 代理商打款表
 */
public interface BusiAgentPayService {

    /**
     * 添加
     *
     * @param busiAgentPay
     * @return
     */
    public boolean insertBusiAgentPay(BusiAgentPay busiAgentPay);

    /**
     * 修改
     *
     * @param busiAgentPay
     * @return
     */
    public boolean updateBusiAgentPay(BusiAgentPay busiAgentPay);

    /**
     * 查询
     *
     * @param busiAgentPay
     * @return
     */
    public BusiAgentPay getBusiAgentPay(BusiAgentPay busiAgentPay);

    /**
     * 获取代理商的所有打款记录list
     *
     * @param bsapyAgent
     * @return List
     */
    public List<BusiAgentPay> findBusiAgentPayForListsByMy(String bsapyAgent);

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiAgentPay> findBusiAgentPayPage(BusiAgentPay busiAgentPay, int pageNum, int pageSize);

}