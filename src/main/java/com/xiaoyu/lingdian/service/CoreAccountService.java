package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreAccount;
import com.xiaoyu.lingdian.core.mybatis.page.Page;

/**
 * 账户表
 */
public interface CoreAccountService {

    /**
     * 添加
     *
     * @param coreAccount
     * @return
     */
    public boolean insertCoreAccount(CoreAccount coreAccount);

    /**
     * 修改
     *
     * @param coreAccount
     * @return
     */
    public boolean updateCoreAccount(CoreAccount coreAccount);

    /**
     * 查询
     *
     * @param coreAccount
     * @return
     */
    public CoreAccount getCoreAccount(CoreAccount coreAccount);

    /**
     * 查询登录
     *
     * @param coreAccount
     * @return
     */
    public CoreAccount getCoreAccountByName(CoreAccount coreAccount);

    /**
     * 查询所有Page
     *
     * @param cractName
     * @param pageNum
     * @param pageSize
     * @return Page
     */
    public Page<CoreAccount> findCoreAccountPage(String cractName, int pageNum, int pageSize);

}