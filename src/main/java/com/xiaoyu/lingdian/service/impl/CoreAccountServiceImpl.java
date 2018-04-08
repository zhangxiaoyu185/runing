package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.HashMap;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.service.CoreAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreAccount;

/**
 * 账户表
 */
@Service("coreAccountService")
public class CoreAccountServiceImpl implements CoreAccountService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreAccount(CoreAccount coreAccount) {
        myBatisDAO.insert(coreAccount);
        return true;
    }

    @Override
    public boolean updateCoreAccount(CoreAccount coreAccount) {
        myBatisDAO.update(coreAccount);
        return true;
    }

    @Override
    public CoreAccount getCoreAccountByName(CoreAccount coreAccount) {
        return (CoreAccount) myBatisDAO.findForObject("getCoreAccountByName", coreAccount);
    }

    @Override
    public CoreAccount getCoreAccount(CoreAccount coreAccount) {
        return (CoreAccount) myBatisDAO.findForObject(coreAccount);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreAccount> findCoreAccountPage(String cractName, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("cractName", cractName);
        return myBatisDAO.findForPage("findCoreAccountForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}