package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.CoreShortMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.CoreShortMessageService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信日志记录表
 */
@Service("coreShortMessageService")
public class CoreShortMessageServiceImpl implements CoreShortMessageService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreShortMessage(CoreShortMessage coreShortMessage) {
        myBatisDAO.insert(coreShortMessage);
        return true;
    }

    @Override
    public boolean updateCoreShortMessage(CoreShortMessage coreShortMessage) {
        myBatisDAO.update(coreShortMessage);
        return true;
    }

    @Override
    public boolean deleteCoreShortMessage(CoreShortMessage coreShortMessage) {
        myBatisDAO.delete(coreShortMessage);
        return true;
    }

    @Override
    public boolean deleteBatchByIds(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("list", list);
        myBatisDAO.delete("deleteBatchCoreShortMessageByIds", hashMap);
        return true;
    }

    @Override
    public CoreShortMessage getCoreShortMessage(CoreShortMessage coreShortMessage) {
        return (CoreShortMessage) myBatisDAO.findForObject(coreShortMessage);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<CoreShortMessage> findCoreShortMessageList(String crmceMobile) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crmceMobile", crmceMobile);
		return myBatisDAO.findForList("findCoreShortMessageForLists", hashMap);
	}

    @SuppressWarnings("unchecked")
    @Override
    public Page<CoreShortMessage> findCoreShortMessagePage(CoreShortMessage coreShortMessage, int pageNum, int pageSize) {
        Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(coreShortMessage);
        return myBatisDAO.findForPage("findCoreShortMessageForPages", new PageRequest(pageNum, pageSize, hashMap));
    }

@Override
	public CoreShortMessage getCoreShortMessageByMobile(String crmceMobile) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crmceMobile", crmceMobile);
		return (CoreShortMessage) myBatisDAO.findForObject("getCoreShortMessageByMobile", hashMap);
	}

	@Override
	public int getCoreShortMessageCountByMobile(String crmceMobile) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("crmceMobile", crmceMobile);
		Integer count = (Integer) myBatisDAO.findForObject("getCoreShortMessageCountByMobile", hashMap);
		return count == null ? 0 : count;
	}
}