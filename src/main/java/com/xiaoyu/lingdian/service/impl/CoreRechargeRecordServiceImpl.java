package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreRechargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreRechargeRecordService;

import java.util.HashMap;
import java.util.Map;

/**
 * 充值记录表
 */
@Service("coreRechargeRecordService")
public class CoreRechargeRecordServiceImpl implements CoreRechargeRecordService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreRechargeRecord(CoreRechargeRecord coreRechargeRecord) {
        myBatisDAO.insert(coreRechargeRecord);
        return true;
    }

    @Override
    public boolean updateCoreRechargeRecord(CoreRechargeRecord coreRechargeRecord) {
        myBatisDAO.update(coreRechargeRecord);
        return true;
    }

    @Override
    public CoreRechargeRecord getCoreRechargeRecord(CoreRechargeRecord coreRechargeRecord) {
        return (CoreRechargeRecord) myBatisDAO.findForObject(coreRechargeRecord);
    }

    /**
     * 根据状态获取充值总额
     * @param crrerStatus
     * @return
     */
    public Double getCoreRechargeRecordSumByTotal(int crrerStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crrerStatus", crrerStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getCoreRechargeRecordSumByTotal", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    /**
     * 根据状态获取当月充值
     * @param crrerStatus
     * @return
     */
    public Double getCoreRechargeRecordSumByMonth(int crrerStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crrerStatus", crrerStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getCoreRechargeRecordSumByMonth", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    /**
     * 根据状态获取当日充值
     * @param crrerStatus
     * @return
     */
    public Double getCoreRechargeRecordSumByDay(int crrerStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crrerStatus", crrerStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getCoreRechargeRecordSumByDay", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreRechargeRecord> findCoreRechargeRecordForMy(String crrerUser, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crrerUser", crrerUser);
        return myBatisDAO.findForPage("findCoreRechargeRecordForLists", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreRechargeRecord> findCoreRechargeRecordPage(CoreRechargeRecord coreRechargeRecord, String mobile, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreRechargeRecord);
        hashMap.put("mobile", mobile);
        return myBatisDAO.findForPage("findCoreRechargeRecordForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}