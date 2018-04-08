package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreSystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreSystemConfigService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置表
 */
@Service("coreSystemConfigService")
public class CoreSystemConfigServiceImpl implements CoreSystemConfigService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreSystemConfig(CoreSystemConfig coreSystemConfig) {
        myBatisDAO.insert(coreSystemConfig);
        return true;
    }

    @Override
    public boolean updateCoreSystemConfig(CoreSystemConfig coreSystemConfig) {
        myBatisDAO.update(coreSystemConfig);
        return true;
    }

    @Override
    public boolean deleteCoreSystemConfig(CoreSystemConfig coreSystemConfig) {
        myBatisDAO.delete(coreSystemConfig);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchCoreSystemConfigByIds", hashMap);
        return true;
    }

    @Override
    public CoreSystemConfig getCoreSystemConfig(CoreSystemConfig coreSystemConfig) {
        return (CoreSystemConfig) myBatisDAO.findForObject(coreSystemConfig);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreSystemConfig> findCoreSystemConfigList() {
        return myBatisDAO.findForList("findCoreSystemConfigForLists", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreSystemConfig> findCoreSystemConfigPage(CoreSystemConfig coreSystemConfig, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreSystemConfig);
        return myBatisDAO.findForPage("findCoreSystemConfigForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

    @Override
    public CoreSystemConfig getCoreSystemConfigByKey(CoreSystemConfig coreSystemConfig) {
        return (CoreSystemConfig) myBatisDAO.findForObject("getCoreSystemConfigByKey", coreSystemConfig);
    }

}