package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiInvitedRelation;

import java.util.List;

/**
 * 邀请关系表
 */
public interface BusiInvitedRelationService {

    /**
     * 添加
     *
     * @param busiInvitedRelation
     * @return
     */
    public boolean insertBusiInvitedRelation(BusiInvitedRelation busiInvitedRelation);

    /**
     * 查询
     *
     * @param busiInvitedRelation
     * @return
     */
    public BusiInvitedRelation getBusiInvitedRelation(BusiInvitedRelation busiInvitedRelation);

    /**
     * 获取我邀请的所有用户List
     *  @param bsirnInvited
     * @return List
     */
    public List<BusiInvitedRelation> findBusiInvitedRelationList(String bsirnInvited);

    /**
     * 获取我邀请的所有用户Page
     *
     * @return Page
     */
    public Page<BusiInvitedRelation> findBusiInvitedRelationPage(String bsirnInvited, int pageNum, int pageSize);

    /**
     * 后台获取所有用户邀请好友page
     *
     * @return Page
     */
    public Page<BusiInvitedRelation> findBusiInvitedRelationPage(BusiInvitedRelation busiInvitedRelation, String mobile, int pageNum, int pageSize);

}