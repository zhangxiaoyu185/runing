package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreCouponService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券表
 */
@Service("coreCouponService")
public class CoreCouponServiceImpl implements CoreCouponService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreCoupon(CoreCoupon coreCoupon) {
        myBatisDAO.insert(coreCoupon);
        return true;
    }

    @Override
    public boolean updateCoreCoupon(CoreCoupon coreCoupon) {
        myBatisDAO.update(coreCoupon);
        return true;
    }

    @Override
    public boolean deleteCoreCoupon(CoreCoupon coreCoupon) {
        myBatisDAO.delete(coreCoupon);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchCoreCouponByIds", hashMap);
        return true;
    }

    @Override
    public CoreCoupon getCoreCoupon(CoreCoupon coreCoupon) {
        return (CoreCoupon) myBatisDAO.findForObject(coreCoupon);
    }

    /**
     * 根据发送状态和使用状态获取数量
     * @param crcpnSendStatus
     * @param crcpnUseStatus
     * @return
     */
    public int getCoreCouponCountByTotal(int crcpnSendStatus, int crcpnUseStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crcpnSendStatus", crcpnSendStatus);
        hashMap.put("crcpnUseStatus", crcpnUseStatus);
        return ((Long) myBatisDAO.findForObject("getCoreCouponCountByTotal", hashMap)).intValue();
    }

    /**
     * 根据发送状态和使用状态获取总额
     * @param crcpnSendStatus
     * @param crcpnUseStatus
     * @return
     */
    public Double getCoreCouponSumByTotal(int crcpnSendStatus, int crcpnUseStatus){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crcpnSendStatus", crcpnSendStatus);
        hashMap.put("crcpnUseStatus", crcpnUseStatus);
        Double sum = ((Double) myBatisDAO.findForObject("getCoreCouponSumByTotal", hashMap));
        if(null == sum) {
            return 0.0;
        } else {
            return sum;
        }
    }

    @Override
    public CoreCoupon getCoreCouponByMyUse(String crcpnUuid, String crcpnUser) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crcpnUuid", crcpnUuid);
        hashMap.put("crcpnUser", crcpnUser);
        return (CoreCoupon)myBatisDAO.findForObject("getCoreCouponByMyUse", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreCoupon> findCoreCouponListMy(String crcpnUser) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crcpnUser", crcpnUser);
        hashMap.put("crcpnUseStatus", 2);
        return myBatisDAO.findForList("findCoreCouponForLists", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreCoupon> findCoreCouponPageMy(String crcpnUser, Integer crcpnUseStatus, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("crcpnUser", crcpnUser);
        hashMap.put("crcpnUseStatus", crcpnUseStatus);
        return myBatisDAO.findForPage("findCoreCouponForLists", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreCoupon> findCoreCouponPage(CoreCoupon coreCoupon, String mobile, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreCoupon);
        hashMap.put("mobile", mobile);
        return myBatisDAO.findForPage("findCoreCouponForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

    /**
     * 获取我的未使用优惠券金额总和
     * @param crcpnUser
     * @return
     */
    public int findCoreCouponForMySum(String crcpnUser){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crcpnUser", crcpnUser);
        Long sum = (Long) myBatisDAO.findForObject("findCoreCouponForMySum", hashMap);
        if(null == sum) {
            return 0;
        } else {
            return sum.intValue();
        }
    }

    /**
     * 获取我的优惠券使用和未使用数量
     * @param crcpnUser
     * @return
     */
    public int findCoreCouponForMyCount(String crcpnUser){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("crcpnUser", crcpnUser);
        return ((Long) myBatisDAO.findForObject("findCoreCouponForMyCount", hashMap)).intValue();
    }

}