package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreCoupon;

import java.util.List;

/**
 * 优惠券表
 */
public interface CoreCouponService {

    /**
     * 添加
     *
     * @param coreCoupon
     * @return
     */
    public boolean insertCoreCoupon(CoreCoupon coreCoupon);

    /**
     * 修改
     *
     * @param coreCoupon
     * @return
     */
    public boolean updateCoreCoupon(CoreCoupon coreCoupon);

    /**
     * 删除
     *
     * @param coreCoupon
     * @return
     */
    public boolean deleteCoreCoupon(CoreCoupon coreCoupon);

    /**
     * 批量删除
     *
     * @param list
     * @return boolean
     */
    public boolean deleteBatchByIds(List<String> list);

    /**
     * 查询
     *
     * @param coreCoupon
     * @return
     */
    public CoreCoupon getCoreCoupon(CoreCoupon coreCoupon);

    /**
     * 根据发送状态和使用状态获取数量
     * @param crcpnSendStatus
     * @param crcpnUseStatus
     * @return
     */
    public int getCoreCouponCountByTotal(int crcpnSendStatus, int crcpnUseStatus);

    /**
     * 根据发送状态和使用状态获取总额
     * @param crcpnSendStatus
     * @param crcpnUseStatus
     * @return
     */
    public Double getCoreCouponSumByTotal(int crcpnSendStatus, int crcpnUseStatus);

    /**
     * 查询优惠券
     *
     * @param crcpnUuid
     * @param crcpnUser
     * @return
     */
    public CoreCoupon getCoreCouponByMyUse(String crcpnUuid, String crcpnUser);

    /**
     * 查询我的所有未使用优惠券List
     *
     * @return List
     */
    public List<CoreCoupon> findCoreCouponListMy(String crcpnUser);

    /**
     * 查询我的所有Page
     *
     * @return Page
     */
    public Page<CoreCoupon> findCoreCouponPageMy(String crcpnUser, Integer crcpnUseStatus, int pageNum, int pageSize);

    /**
     * 后台查询所有Page
     *
     * @return Page
     */
    public Page<CoreCoupon> findCoreCouponPage(CoreCoupon coreCoupon, String mobile, int pageNum, int pageSize);

    /**
     * 获取我的未使用优惠券金额总和
     * @param crcpnUser
     * @return
     */
    public int findCoreCouponForMySum(String crcpnUser);

    /**
     * 获取我的优惠券使用和未使用数量
     * @param crcpnUser
     * @return
     */
    public int findCoreCouponForMyCount(String crcpnUser);

}