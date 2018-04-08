package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiAgentPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiAgentPayService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理商打款表
 */
@Service("busiAgentPayService")
public class BusiAgentPayServiceImpl implements BusiAgentPayService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiAgentPay(BusiAgentPay busiAgentPay) {
        myBatisDAO.insert(busiAgentPay);
        return true;
    }

    @Override
    public boolean updateBusiAgentPay(BusiAgentPay busiAgentPay) {
        myBatisDAO.update(busiAgentPay);
        return true;
    }

    @Override
    public BusiAgentPay getBusiAgentPay(BusiAgentPay busiAgentPay) {
        return (BusiAgentPay) myBatisDAO.findForObject(busiAgentPay);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiAgentPay> findBusiAgentPayForListsByMy(String bsapyAgent) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("bsapyAgent", bsapyAgent);
        return myBatisDAO.findForList("findBusiAgentPayForListsByMy", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiAgentPay> findBusiAgentPayPage(BusiAgentPay busiAgentPay, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiAgentPay);
        return myBatisDAO.findForPage("findBusiAgentPayForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}