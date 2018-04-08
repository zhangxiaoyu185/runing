package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreSystemSet;

/**
 * 系统设置表
 */
public interface CoreSystemSetService {

    /**
     * 修改
     *
     * @param coreSystemSet
     * @return
     */
    public boolean updateCoreSystemSet(CoreSystemSet coreSystemSet);

    /**
     * 查询
     *
     * @param coreSystemSet
     * @return
     */
    public CoreSystemSet getCoreSystemSet(CoreSystemSet coreSystemSet);

}