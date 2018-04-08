package com.xiaoyu.lingdian.service.impl;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import com.xiaoyu.lingdian.service.CoreAttachmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreAttachment;

/**
 * 业务附件表
 */
@Service("coreAttachmentService")
public class CoreAttachmentServiceImpl implements CoreAttachmentService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean insertCoreAttachment(CoreAttachment coreAttachment) {
        myBatisDAO.insert(coreAttachment);
        return true;
    }

    @Override
    public boolean updateCoreAttachment(CoreAttachment coreAttachment) {
        myBatisDAO.update(coreAttachment);
        return true;
    }

    @Override
    public boolean deleteCoreAttachment(CoreAttachment coreAttachment) {
        myBatisDAO.delete(coreAttachment);
        return true;
    }

    @Override
    public CoreAttachment getCoreAttachment(CoreAttachment coreAttachment) {
        return (CoreAttachment) myBatisDAO.findForObject(coreAttachment);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CoreAttachment> findCoreAttachmentByCnd(String cratmBusUuid, Integer cratmType) {
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("cratmBusUuid", cratmBusUuid);
        hashMap.put("cratmType", cratmType);
        return myBatisDAO.findForList("findCoreAttachmentByCnd", hashMap);
    }

    @Override
    public boolean deleteCoreAttachmentByBusi(CoreAttachment coreAttachment) {
        myBatisDAO.delete("deleteCoreAttachmentByBusi", coreAttachment);
        return true;
    }

    @Override
    public boolean updateCoreAttachmentByBus(CoreAttachment coreAttachment) {
        myBatisDAO.update("updateCoreAttachmentByBus", coreAttachment);
        return true;
    }

}