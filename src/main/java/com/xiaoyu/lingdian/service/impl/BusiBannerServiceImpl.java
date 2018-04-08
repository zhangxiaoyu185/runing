package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiBannerService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页Banner表
 */
@Service("busiBannerService")
public class BusiBannerServiceImpl implements BusiBannerService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiBanner(BusiBanner busiBanner) {
        myBatisDAO.insert(busiBanner);
        return true;
    }

    @Override
    public boolean updateBusiBanner(BusiBanner busiBanner) {
        myBatisDAO.update(busiBanner);
        return true;
    }

    @Override
    public boolean deleteBusiBanner(BusiBanner busiBanner) {
        myBatisDAO.delete(busiBanner);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchBusiBannerByIds", hashMap);
        return true;
    }

    @Override
    public BusiBanner getBusiBanner(BusiBanner busiBanner) {
        return (BusiBanner) myBatisDAO.findForObject(busiBanner);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiBanner> findBusiBannerList() {
        return myBatisDAO.findForList("findBusiBannerForLists", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiBanner> findBusiBannerPage(BusiBanner busiBanner, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiBanner);
        return myBatisDAO.findForPage("findBusiBannerForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}