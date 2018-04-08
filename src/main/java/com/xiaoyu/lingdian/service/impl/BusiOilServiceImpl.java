package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.mapper.SimpleMapMapper;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiOil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiOilService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 油卡表
 */
@Service("busiOilService")
public class BusiOilServiceImpl implements BusiOilService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiOil(BusiOil busiOil) {
        myBatisDAO.insert(busiOil);
        return true;
    }

    @Override
    public boolean updateBusiOil(BusiOil busiOil) {
        myBatisDAO.update(busiOil);
        return true;
    }

    @Override
    public boolean updateBatchBusiOilByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.update("updateBatchBusiOilByIds", hashMap);
        return true;
    }

    @Override
    public BusiOil getBusiOil(BusiOil busiOil) {
        return (BusiOil) myBatisDAO.findForObject(busiOil);
    }

    private static final SimpleMapMapper<String, BusiOil> OIL_UUID_MAPPER = new SimpleMapMapper<String, BusiOil>() {
        @Override
        public String mapKey(BusiOil object, int rowNum) {
            return object.getBsoilUuid();
        }
    };

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, BusiOil> findBusiOilForLists(List<String> list){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        return myBatisDAO.findForMap("findBusiOilForLists", hashMap, OIL_UUID_MAPPER);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiOil> findBusiOilForPagesByMy(String bsoilUser, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("bsoilUser", bsoilUser);
        return myBatisDAO.findForPage("findBusiOilForPagesByMy", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiOil> findBusiOilList(BusiOil busiOil){
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiOil);
        return myBatisDAO.findForList("findBusiOilForPages", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiOil> findBusiOilPage(BusiOil busiOil, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiOil);
        return myBatisDAO.findForPage("findBusiOilForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}