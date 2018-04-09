package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiSyncService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 同步表
*/
@Service("busiSyncService")
public class BusiSyncServiceImpl implements BusiSyncService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiSync(BusiSync busiSync) {
		myBatisDAO.insert(busiSync);
		return true;
	}

	@Override
	public boolean updateBusiSync(BusiSync busiSync) {
		myBatisDAO.update(busiSync);
		return true;
	}

	@Override
	public boolean deleteBusiSync(BusiSync busiSync) {
		myBatisDAO.delete(busiSync);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiSyncByIds",hashMap);
		return true;
	}

	@Override
	public BusiSync getBusiSync(BusiSync busiSync) {
		return (BusiSync) myBatisDAO.findForObject(busiSync);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiSync> findBusiSyncList() {
		return myBatisDAO.findForList("findBusiSyncForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiSync> findBusiSyncPage(BusiSync busiSync, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiSync);
		return myBatisDAO.findForPage("findBusiSyncForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}