package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.entity.CoreWechat;

/**
 * 小程序表
 */
public interface CoreWechatService {

    /**
     * 修改
     *
     * @param coreWechat
     * @return
     */
    public boolean updateCoreWechat(CoreWechat coreWechat);

    /**
     * 修改
     *
     * @param coreWechat
     * @return
     */
    public boolean updateCoreWechatByAppid(CoreWechat coreWechat);

    /**
     * 查询
     *
     * @param coreWechat
     * @return
     */
    public CoreWechat getCoreWechat(CoreWechat coreWechat);

    /**
     * 根据微信名称查询
     *
     * @param coreWechat
     * @return
     */
    public CoreWechat getCoreWechatByName(CoreWechat coreWechat);

    /**
     * 根据公众号UUID获取全局access_token
     *
     * @param crwctUuid
     */
    public String getAccessToken(String crwctUuid);
}