package com.xiaoyu.lingdian.service.impl;

import java.util.Date;
import com.xiaoyu.lingdian.service.CoreWechatService;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.wx.WeixinUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreWechat;
import com.xiaoyu.lingdian.entity.weixin.AccessTokenModel;
import com.xiaoyu.lingdian.entity.weixin.ShareConfig;

/**
 * 小程序表
 */
@Service("coreWechatService")
public class CoreWechatServiceImpl implements CoreWechatService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean updateCoreWechat(CoreWechat coreWechat) {
        myBatisDAO.update(coreWechat);
        return true;
    }

    @Override
    public boolean updateCoreWechatByAppid(CoreWechat coreWechat) {
        myBatisDAO.update("updateCoreWechatByAppid", coreWechat);
        return true;
    }

    @Override
    public CoreWechat getCoreWechat(CoreWechat coreWechat) {
        return (CoreWechat) myBatisDAO.findForObject(coreWechat);
    }

    @Override
    public CoreWechat getCoreWechatByName(CoreWechat coreWechat) {
        return (CoreWechat) myBatisDAO.findForObject("getCoreWechatByName", coreWechat);
    }

    @Override
    public String getAccessToken(String crwctUuid) {
        String accessToken = "";
        CoreWechat coreWechat = new CoreWechat();
        coreWechat.setCrwctUuid(crwctUuid);
        coreWechat = this.getCoreWechat(coreWechat);
        if (null != coreWechat) {
            String appid = coreWechat.getCrwctAppid();
            Date now = new Date();
            if (now.getTime() >= coreWechat.getCrwctAccessTime().getTime()) {
                AccessTokenModel accessTokenModel = WeixinUtil.getAccessToken(coreWechat.getCrwctAppid(), coreWechat.getCrwctAppsecret());
                if (null != accessTokenModel) {
                    accessToken = accessTokenModel.getAccess_token();
                    coreWechat = new CoreWechat();
                    coreWechat.setCrwctAppid(appid);
                    coreWechat.setCrwctAccessTime(DateUtil.addSecond(now, 7000));
                    coreWechat.setCrwctAccessToken(accessToken);
                    this.updateCoreWechatByAppid(coreWechat);
                }
            } else {
                accessToken = coreWechat.getCrwctAccessToken();
            }
        }

        return accessToken;
    }
}
