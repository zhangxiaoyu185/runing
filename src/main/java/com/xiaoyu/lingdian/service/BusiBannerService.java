package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiBanner;

import java.util.List;

/**
 * 首页Banner表
 */
public interface BusiBannerService {

    /**
     * 添加
     *
     * @param busiBanner
     * @return
     */
    public boolean insertBusiBanner(BusiBanner busiBanner);

    /**
     * 修改
     *
     * @param busiBanner
     * @return
     */
    public boolean updateBusiBanner(BusiBanner busiBanner);

    /**
     * 删除
     *
     * @param busiBanner
     * @return
     */
    public boolean deleteBusiBanner(BusiBanner busiBanner);

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
     * @param busiBanner
     * @return
     */
    public BusiBanner getBusiBanner(BusiBanner busiBanner);

    /**
     * 查询所有List
     *
     * @return List
     */
    public List<BusiBanner> findBusiBannerList();

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiBanner> findBusiBannerPage(BusiBanner busiBanner, int pageNum, int pageSize);

}