package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreCommissionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreCommissionRecordService;

import java.util.HashMap;
import java.util.Map;

/**
 * 佣金记录表
 */
@Service("coreCommissionRecordService")
public class CoreCommissionRecordServiceImpl implements CoreCommissionRecordService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreCommissionRecord(CoreCommissionRecord coreCommissionRecord) {
        myBatisDAO.insert(coreCommissionRecord);
        return true;
    }

    @Override
    public boolean updateCoreCommissionRecord(CoreCommissionRecord coreCommissionRecord) {
        myBatisDAO.update(coreCommissionRecord);
        return true;
    }

    @Override
    public CoreCommissionRecord getCoreCommissionRecord(CoreCommissionRecord coreCommissionRecord) {
        return (CoreCommissionRecord) myBatisDAO.findForObject(coreCommissionRecord);
    }

    /**
     * 获取佣金总额
     * @return
     */
    public Double getCoreCommissionRecordSumByTotal(){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Double sum = ((Double) myBatisDAO.findForObject("getCoreCommissionRecordSumByTotal", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    /**
     * 获取当月佣金
     * @return
     */
    public Double getCoreCommissionRecordSumByMonth(){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Double sum = ((Double) myBatisDAO.findForObject("getCoreCommissionRecordSumByMonth", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    /**
     * 获取当日佣金
     * @return
     */
    public Double getCoreCommissionRecordSumByDay(){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Double sum = ((Double) myBatisDAO.findForObject("getCoreCommissionRecordSumByDay", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreCommissionRecord> findCoreCommissionRecordForMy(String crcrdBeUser, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crcrdBeUser", crcrdBeUser);
        return myBatisDAO.findForPage("findCoreCommissionRecordForLists", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreCommissionRecord> findCoreCommissionRecordPage(CoreCommissionRecord coreCommissionRecord, String rechargemobile, String mobile, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreCommissionRecord);
        hashMap.put("rechargemobile", rechargemobile);
        hashMap.put("mobile", mobile);
        return myBatisDAO.findForPage("findCoreCommissionRecordForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}