package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.entity.CoreSystemSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.service.CoreSystemSetService;

/**
 * 系统设置表
 */
@Service("coreSystemSetService")
public class CoreSystemSetServiceImpl implements CoreSystemSetService {

    @Autowired
    private MyBatisDAO myBatisDAO;

    @Override
    public boolean updateCoreSystemSet(CoreSystemSet coreSystemSet) {
        myBatisDAO.update(coreSystemSet);
        return true;
    }

    @Override
    public CoreSystemSet getCoreSystemSet(CoreSystemSet coreSystemSet) {
        return (CoreSystemSet) myBatisDAO.findForObject(coreSystemSet);
    }

}