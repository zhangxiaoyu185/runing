package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiInvitedRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiInvitedRelationService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请关系表
 */
@Service("busiInvitedRelationService")
public class BusiInvitedRelationServiceImpl implements BusiInvitedRelationService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiInvitedRelation(BusiInvitedRelation busiInvitedRelation) {
        myBatisDAO.insert(busiInvitedRelation);
        return true;
    }

    @Override
    public BusiInvitedRelation getBusiInvitedRelation(BusiInvitedRelation busiInvitedRelation) {
        return (BusiInvitedRelation) myBatisDAO.findForObject(busiInvitedRelation);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiInvitedRelation> findBusiInvitedRelationList(String bsirnInvited) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("bsirnInvited", bsirnInvited);
        return myBatisDAO.findForList("findBusiInvitedRelationForLists", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiInvitedRelation> findBusiInvitedRelationPage(String bsirnInvited, int pageNum, int pageSize) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("bsirnInvited", bsirnInvited);
        return myBatisDAO.findForPage("findBusiInvitedRelationForLists", new PageRequest(pageNum, pageSize, hashMap));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiInvitedRelation> findBusiInvitedRelationPage(BusiInvitedRelation busiInvitedRelation, String mobile, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiInvitedRelation);
        hashMap.put("mobile", mobile);
        return myBatisDAO.findForPage("findBusiInvitedRelationForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}