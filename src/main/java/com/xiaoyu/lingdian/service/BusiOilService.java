package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiOil;

import java.util.List;
import java.util.Map;

/**
 * 油卡表
 */
public interface BusiOilService {

    /**
     * 添加
     *
     * @param busiOil
     * @return
     */
    public boolean insertBusiOil(BusiOil busiOil);

    /**
     * 修改
     *
     * @param busiOil
     * @return
     */
    public boolean updateBusiOil(BusiOil busiOil);

    /**
     * 批量删除
     *
     * @param list
     * @return boolean
     */
    public boolean updateBatchBusiOilByIds(List<String> list);

    /**
     * 查询
     *
     * @param busiOil
     * @return
     */
    public BusiOil getBusiOil(BusiOil busiOil);

    /**
     * 根据油卡集合获取油卡Map
     * @param list
     * @return map
     */
    public Map<String, BusiOil> findBusiOilForLists(List<String> list);

    /**
     * 获取我的油卡page
     * @param bsoilUser
     * @return Page
     */
    public Page<BusiOil> findBusiOilForPagesByMy(String bsoilUser, int pageNum, int pageSize);

    /**
     * 后台获取所有List
     *
     * @return list
     */
    public List<BusiOil> findBusiOilList(BusiOil busiOil);

    /**
     * 后台获取所有page
     *
     * @return Page
     */
    public Page<BusiOil> findBusiOilPage(BusiOil busiOil, int pageNum, int pageSize);

}