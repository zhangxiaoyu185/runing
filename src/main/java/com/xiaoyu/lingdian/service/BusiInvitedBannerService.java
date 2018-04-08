package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiInvitedBanner;

import java.util.List;

/**
 * 邀请Banner表
 */
public interface BusiInvitedBannerService {

    /**
     * 添加
     *
     * @param busiInvitedBanner
     * @return
     */
    public boolean insertBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner);

    /**
     * 修改
     *
     * @param busiInvitedBanner
     * @return
     */
    public boolean updateBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner);

    /**
     * 删除
     *
     * @param busiInvitedBanner
     * @return
     */
    public boolean deleteBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner);

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
     * @param busiInvitedBanner
     * @return
     */
    public BusiInvitedBanner getBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner);

    /**
     * 查询所有List
     *
     * @return List
     */
    public List<BusiInvitedBanner> findBusiInvitedBannerList();

    /**
     * 查询所有Page
     *
     * @return Page
     */
    public Page<BusiInvitedBanner> findBusiInvitedBannerPage(BusiInvitedBanner busiInvitedBanner, int pageNum, int pageSize);

}