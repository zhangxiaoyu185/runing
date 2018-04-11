package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiWeekStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiWeekStepService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 周步数表
*/
@Service("busiWeekStepService")
public class BusiWeekStepServiceImpl implements BusiWeekStepService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiWeekStep(BusiWeekStep busiWeekStep) {
		myBatisDAO.insert(busiWeekStep);
		return true;
	}

	@Override
	public boolean updateBusiWeekStep(BusiWeekStep busiWeekStep) {
		myBatisDAO.update(busiWeekStep);
		return true;
	}

	@Override
	public boolean deleteBusiWeekStep(BusiWeekStep busiWeekStep) {
		myBatisDAO.delete(busiWeekStep);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiWeekStepByIds",hashMap);
		return true;
	}

	@Override
	public BusiWeekStep getBusiWeekStep(BusiWeekStep busiWeekStep) {
		return (BusiWeekStep) myBatisDAO.findForObject(busiWeekStep);
	}

	@Override
	public BusiWeekStep getBusiWeekStepByOrd(Integer bswspOrd) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bswspOrd", bswspOrd);
		return (BusiWeekStep) myBatisDAO.findForObject("getBusiWeekStepByOrd", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiWeekStep> findBusiWeekStepForWeekChat(Integer bswspOrd) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bswspOrd", bswspOrd);
		return myBatisDAO.findForList("findBusiWeekStepForWeekChat", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiWeekStep> findBusiWeekStepPage(BusiWeekStep busiWeekStep, String name, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiWeekStep);
		hashMap.put("name", name);
		return myBatisDAO.findForPage("findBusiWeekStepForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}