package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.xiaoyu.lingdian.service.CoreRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.mapper.SimpleMapMapper;
import com.xiaoyu.lingdian.entity.CoreRegion;
import org.springframework.util.CollectionUtils;

/**
 * 区域表
 */
@Service("coreRegionService")
public class CoreRegionServiceImpl implements CoreRegionService {

    private static final SimpleMapMapper<String, CoreRegion> REGION_UUID_MAPPER = new SimpleMapMapper<String, CoreRegion>() {
        @Override
        public String mapKey(CoreRegion object, int rowNum) {
            return object.getCrrgnUuid();
        }
    };

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public CoreRegion getCoreRegion(CoreRegion coreRegion) {
        return (CoreRegion) myBatisDAO.findForObject(coreRegion);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreRegion> findCoreRegionByParentAndTypeAndName(String crrgnName, Integer crrgnType, String crrgnParent) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crrgnType", crrgnType);
        hashMap.put("crrgnName", crrgnName);
        hashMap.put("crrgnParent", crrgnParent);
        return myBatisDAO.findForList("findCoreRegionByParentAndTypeAndName", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, CoreRegion> findCoreRegionMapByUuidList(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        return myBatisDAO.findForMap("findCoreRegionForLists", hashMap, REGION_UUID_MAPPER);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreRegion> findCoreRegionByType(Integer crrgnType) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crrgnType", crrgnType);
        return myBatisDAO.findForList("findCoreRegionByParentAndTypeAndName", hashMap);
    }

}