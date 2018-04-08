package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiAdvert;

import java.util.List;

/**
 * 油卡充值Banner表
 */
public interface BusiAdvertService {

    /**
     * 添加
     *
     * @param busiAdvert
     * @return
     */
    public boolean insertBusiAdvert(BusiAdvert busiAdvert);

    /**
     * 修改
     *
     * @param busiAdvert
     * @return
     */
    public boolean updateBusiAdvert(BusiAdvert busiAdvert);

    /**
     * 删除
     *
     * @param busiAdvert
     * @return
     */
    public boolean deleteBusiAdvert(BusiAdvert busiAdvert);

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
     * @param busiAdvert
     * @return
     */
    public BusiAdvert getBusiAdvert(BusiAdvert busiAdvert);

    /**
     * 查询所有List
     *
     * @return List
     */
    public List<BusiAdvert> findBusiAdvertList();

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiAdvert> findBusiAdvertPage(BusiAdvert busiAdvert, int pageNum, int pageSize);

}