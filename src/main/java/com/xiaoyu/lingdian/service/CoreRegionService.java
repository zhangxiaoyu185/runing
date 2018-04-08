package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreRegion;

import java.util.List;
import java.util.Map;

/**
 * 区域表
 */
public interface CoreRegionService {

    /**
     * 查询
     *
     * @param coreRegion
     * @return
     */
    public CoreRegion getCoreRegion(CoreRegion coreRegion);

    /**
     * 根据地区类型 和名称获得地区id
     *
     * @param crrgnName
     * @param crrgnType   1:country/国家;2:province/省/自治区/直辖市;3:city/地区/省下面的地级市;4:district/县/县级市/区
     * @param crrgnParent
     * @return
     */
    public List<CoreRegion> findCoreRegionByParentAndTypeAndName(String crrgnName, Integer crrgnType, String crrgnParent);

    /**
     * 根据地区UUID集合查询地区map
     *
     * @param list
     * @return
     */
    public Map<String, CoreRegion> findCoreRegionMapByUuidList(List<String> list);

    public List<CoreRegion> findCoreRegionByType(Integer crrgnType);
}