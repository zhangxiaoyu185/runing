package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.service.CoreFunctionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreFunction;

/**
 * 功能项表
 */
@Service("coreFunctionService")
public class CoreFunctionServiceImpl implements CoreFunctionService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreFunction(CoreFunction coreFunction) {
        myBatisDAO.insert(coreFunction);
        return true;
    }

    @Override
    public boolean updateCoreFunction(CoreFunction coreFunction) {
        myBatisDAO.update(coreFunction);
        return true;
    }

    @Override
    public boolean updateCoreFunctionByFather(CoreFunction coreFunction) {
        myBatisDAO.update("updateCoreFunctionByFather", coreFunction);
        return true;
    }

    @Override
    public boolean deleteCoreFunction(CoreFunction coreFunction) {
        myBatisDAO.delete(coreFunction);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (list.size() <= 0) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchCoreFunctionByIds", hashMap);
        return true;
    }

    @Override
    public CoreFunction getCoreFunction(CoreFunction coreFunction) {
        return (CoreFunction) myBatisDAO.findForObject(coreFunction);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreFunction> findCoreFunctionList(String crfntFather) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crfntFather", crfntFather);
        return myBatisDAO.findForList("findCoreFunctionForLists", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreFunction> findCoreFunctionPage(String crfntFunName, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crfntFunName", crfntFunName);
        return myBatisDAO.findForPage("findCoreFunctionForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreFunction> findCoreFunctionFather() {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        return myBatisDAO.findForList("findCoreFunctionFather", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreFunction> findCoreFunctionsByIds(List<String> ids) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", ids);
        return myBatisDAO.findForList("findCoreFunctionsByIds", hashMap);
    }

}