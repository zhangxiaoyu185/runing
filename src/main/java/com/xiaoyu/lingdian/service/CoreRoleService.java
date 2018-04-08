package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreRole;

import java.util.List;

import com.xiaoyu.lingdian.core.mybatis.page.Page;

/**
 * 角色表
 */
public interface CoreRoleService {

    /**
     * 添加
     *
     * @param coreRole
     * @return
     */
    public boolean insertCoreRole(CoreRole coreRole);

    /**
     * 修改
     *
     * @param coreRole
     * @return
     */
    public boolean updateCoreRole(CoreRole coreRole);

    /**
     * 删除
     *
     * @param coreRole
     * @return
     */
    public boolean deleteCoreRole(CoreRole coreRole);

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
     * @param coreRole
     * @return
     */
    public CoreRole getCoreRole(CoreRole coreRole);

    /**
     * 查询所有List
     *
     * @return List
     */
    public List<CoreRole> findCoreRoleList();

    /**
     * 查询所有Page
     *
     * @param crrolName
     * @return Page
     */
    public Page<CoreRole> findCoreRolePage(String crrolName, int pageNum, int pageSize);

    /**
     * 根据角色UUID集合查询角色
     *
     * @param uuids
     * @return
     */
    public List<CoreRole> findCoreRoleByUuids(List<String> uuids);

}