package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreFunction;

import java.util.List;

import com.xiaoyu.lingdian.core.mybatis.page.Page;

/**
 * 功能项表
 */
public interface CoreFunctionService {

    /**
     * 添加
     *
     * @param coreFunction
     * @return
     */
    public boolean insertCoreFunction(CoreFunction coreFunction);

    /**
     * 修改
     *
     * @param coreFunction
     * @return
     */
    public boolean updateCoreFunction(CoreFunction coreFunction);

    /**
     * 根据父菜单统一修改子菜单
     *
     * @param coreFunction
     * @return
     */
    public boolean updateCoreFunctionByFather(CoreFunction coreFunction);

    /**
     * 删除
     *
     * @param coreFunction
     * @return
     */
    public boolean deleteCoreFunction(CoreFunction coreFunction);

    /**
     * 批量删除
     *
     * @param list
     * @return boolean
     */
    public boolean deleteBatchByIds(List<String> list);

    /**
     * 查询(包括删除和未删除)
     *
     * @param coreFunction
     * @return
     */
    public CoreFunction getCoreFunction(CoreFunction coreFunction);

    /**
     * 查询所有List
     *
     * @param crfntFather
     * @return List
     */
    public List<CoreFunction> findCoreFunctionList(String crfntFather);

    /**
     * 查询所有Page(包括删除和未删除)
     *
     * @param crfntFunName
     * @return Page
     */
    public Page<CoreFunction> findCoreFunctionPage(String crfntFunName, int pageNum, int pageSize);

    /**
     * 获取主菜单和一级菜单列表
     *
     * @return
     */
    public List<CoreFunction> findCoreFunctionFather();

    /**
     * 获取权限UUID集合获取权限列表
     *
     * @param ids
     */
    public List<CoreFunction> findCoreFunctionsByIds(List<String> ids);

}