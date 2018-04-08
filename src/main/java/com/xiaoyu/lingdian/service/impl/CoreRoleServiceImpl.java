package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.service.CoreRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreRole;

/**
 * 角色表
 */
@Service("coreRoleService")
public class CoreRoleServiceImpl implements CoreRoleService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreRole(CoreRole coreRole) {
        myBatisDAO.insert(coreRole);
        return true;
    }

    @Override
    public boolean updateCoreRole(CoreRole coreRole) {
        myBatisDAO.update(coreRole);
        return true;
    }

    @Override
    public boolean deleteCoreRole(CoreRole coreRole) {
        myBatisDAO.delete(coreRole);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (list.size() <= 0) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchCoreRoleByIds", hashMap);
        return true;
    }

    @Override
    public CoreRole getCoreRole(CoreRole coreRole) {
        return (CoreRole) myBatisDAO.findForObject(coreRole);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreRole> findCoreRoleList() {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        return myBatisDAO.findForList("findCoreRoleForLists", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreRole> findCoreRolePage(String crrolName, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crrolName", crrolName);
        return myBatisDAO.findForPage("findCoreRoleForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreRole> findCoreRoleByUuids(List<String> uuids) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", uuids);
        return myBatisDAO.findForList("findCoreRoleByUuids", hashMap);
    }

}