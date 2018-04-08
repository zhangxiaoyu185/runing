package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.mapper.SimpleMapMapper;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiAgentService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理商表
 */
@Service("busiAgentService")
public class BusiAgentServiceImpl implements BusiAgentService {

    private static final SimpleMapMapper<String, BusiAgent> AGENT_UUID_MAPPER = new SimpleMapMapper<String, BusiAgent>() {
        @Override
        public String mapKey(BusiAgent object, int rowNum) {
            return object.getBsaetUuid();
        }
    };

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertBusiAgent(BusiAgent busiAgent) {
        myBatisDAO.insert(busiAgent);
        return true;
    }

    @Override
    public boolean updateBusiAgent(BusiAgent busiAgent) {
        myBatisDAO.update(busiAgent);
        return true;
    }

    @Override
    public boolean updateBusiAgentByFee(String bsaetUuid, double bsaetFee){
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("bsaetUuid", bsaetUuid);
        hashMap.put("bsaetFee", bsaetFee);
        myBatisDAO.update("updateBusiAgentByFee", hashMap);
        return true;
    }

    @Override
    public boolean updateBatchBusiAgentByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.update("updateBatchBusiAgentByIds", hashMap);
        return true;
    }

    @Override
    public BusiAgent getBusiAgent(BusiAgent busiAgent) {
        return (BusiAgent) myBatisDAO.findForObject(busiAgent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BusiAgent> findBusiAgentList(List<String> list) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        return myBatisDAO.findForList("findBusiAgentForLists", hashMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<BusiAgent> findBusiAgentPage(BusiAgent busiAgent, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiAgent);
        return myBatisDAO.findForPage("findBusiAgentForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

    public Map<String, BusiAgent> findBusiAgentMapByUuidList(List<String> list){
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        return myBatisDAO.findForMap("findBusiAgentForLists", hashMap, AGENT_UUID_MAPPER);
    }

}