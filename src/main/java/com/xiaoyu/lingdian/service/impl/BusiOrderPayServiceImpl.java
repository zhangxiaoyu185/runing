package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiOrderPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiOrderPayService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付记录表
 */
@Service("busiOrderPayService")
public class BusiOrderPayServiceImpl implements BusiOrderPayService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiOrderPay(BusiOrderPay busiOrderPay) {
        myBatisDAO.insert(busiOrderPay);
        return true;
    }

    @Override
    public boolean updateBusiOrderPay(BusiOrderPay busiOrderPay) {
        myBatisDAO.update(busiOrderPay);
        return true;
    }

    @Override
    public boolean deleteBusiOrderPay(BusiOrderPay busiOrderPay) {
        myBatisDAO.delete(busiOrderPay);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchBusiOrderPayByIds", hashMap);
        return true;
    }

    @Override
    public BusiOrderPay getBusiOrderPay(BusiOrderPay busiOrderPay) {
        return (BusiOrderPay) myBatisDAO.findForObject(busiOrderPay);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiOrderPay> findBusiOrderPayList() {
        return myBatisDAO.findForList("findBusiOrderPayForLists", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiOrderPay> findBusiOrderPayPage(BusiOrderPay busiOrderPay, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiOrderPay);
        return myBatisDAO.findForPage("findBusiOrderPayForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}