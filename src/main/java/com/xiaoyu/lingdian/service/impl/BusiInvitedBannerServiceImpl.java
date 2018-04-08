package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiInvitedBanner;
import com.xiaoyu.lingdian.service.BusiInvitedBannerService;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请Banner表
 */
@Service("busiInvitedBannerService")
public class BusiInvitedBannerServiceImpl implements BusiInvitedBannerService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner) {
        myBatisDAO.insert(busiInvitedBanner);
        return true;
    }

    @Override
    public boolean updateBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner) {
        myBatisDAO.update(busiInvitedBanner);
        return true;
    }

    @Override
    public boolean deleteBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner) {
        myBatisDAO.delete(busiInvitedBanner);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchBusiInvitedBannerByIds", hashMap);
        return true;
    }

    @Override
    public BusiInvitedBanner getBusiInvitedBanner(BusiInvitedBanner busiInvitedBanner) {
        return (BusiInvitedBanner) myBatisDAO.findForObject(busiInvitedBanner);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiInvitedBanner> findBusiInvitedBannerList() {
        return myBatisDAO.findForList("findBusiInvitedBannerForLists", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiInvitedBanner> findBusiInvitedBannerPage(BusiInvitedBanner busiInvitedBanner, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiInvitedBanner);
        return myBatisDAO.findForPage("findBusiInvitedBannerForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

}