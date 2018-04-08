package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiAgent;

import java.util.List;
import java.util.Map;

/**
 * 代理商表
 */
public interface BusiAgentService {

    /**
     * 添加
     *
     * @param busiAgent
     * @return
     */
    public boolean insertBusiAgent(BusiAgent busiAgent);

    /**
     * 修改
     *
     * @param busiAgent
     * @return
     */
    public boolean updateBusiAgent(BusiAgent busiAgent);

    /**
     * 代理商打款
     * @param bsaetUuid
     * @param bsaetFee
     * @return
     */
    public boolean updateBusiAgentByFee(String bsaetUuid, double bsaetFee);

    /**
     * 批量删除
     *
     * @param list
     * @return boolean
     */
    public boolean updateBatchBusiAgentByIds(List<String> list);

    /**
     * 查询
     *
     * @param busiAgent
     * @return
     */
    public BusiAgent getBusiAgent(BusiAgent busiAgent);

    /**
     * 查询所有List
     * @param list
     * @return List
     */
    public List<BusiAgent> findBusiAgentList(List<String> list);

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiAgent> findBusiAgentPage(BusiAgent busiAgent, int pageNum, int pageSize);

    /**
     * 查询供应商Mapper
     * @param list
     * @return List
     */
    public Map<String, BusiAgent> findBusiAgentMapByUuidList(List<String> list);

}