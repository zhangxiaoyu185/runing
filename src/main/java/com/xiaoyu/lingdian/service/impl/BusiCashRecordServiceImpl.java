package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiCashRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiCashRecordService;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户提现记录表
 */
@Service("busiCashRecordService")
public class BusiCashRecordServiceImpl implements BusiCashRecordService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiCashRecord(BusiCashRecord busiCashRecord) {
        myBatisDAO.insert(busiCashRecord);
        return true;
    }

    @Override
    public boolean updateBusiCashRecord(BusiCashRecord busiCashRecord) {
        myBatisDAO.update(busiCashRecord);
        return true;
    }

    @Override
    public BusiCashRecord getBusiCashRecord(BusiCashRecord busiCashRecord) {
        return (BusiCashRecord) myBatisDAO.findForObject(busiCashRecord);
    }

    /**
     * 根据状态获取提现总额
     * @param bscrdStatus
     * @return
     */
    public Double getBusiCashRecordSumByTotal(int bscrdStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("bscrdStatus", bscrdStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getBusiCashRecordSumByTotal", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    /**
     * 根据状态获取当月提现
     * @param bscrdStatus
     * @return
     */
    public Double getBusiCashRecordSumByMonth(int bscrdStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("bscrdStatus", bscrdStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getBusiCashRecordSumByMonth", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    /**
     * 根据状态获取当日提现
     * @param bscrdStatus
     * @return
     */
    public Double getBusiCashRecordSumByDay(int bscrdStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("bscrdStatus", bscrdStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getBusiCashRecordSumByDay", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiCashRecord> findBusiCashRecordForPagesByMy(String bscrdExtracted, int bscrdStatus, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("bscrdExtracted", bscrdExtracted);
        hashMap.put("bscrdStatus", bscrdStatus);
        return myBatisDAO.findForPage("findBusiCashRecordForPagesByMy", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiCashRecord> findBusiCashRecordPage(BusiCashRecord busiCashRecord, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiCashRecord);
        return myBatisDAO.findForPage("findBusiCashRecordForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}