package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiDayStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiDayStepService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 日步数表
*/
@Service("busiDayStepService")
public class BusiDayStepServiceImpl implements BusiDayStepService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiDayStep(BusiDayStep busiDayStep) {
		myBatisDAO.insert(busiDayStep);
		return true;
	}

	@Override
	public boolean updateBusiDayStep(BusiDayStep busiDayStep) {
		myBatisDAO.update(busiDayStep);
		return true;
	}

	@Override
	public boolean deleteBusiDayStep(BusiDayStep busiDayStep) {
		myBatisDAO.delete(busiDayStep);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiDayStepByIds",hashMap);
		return true;
	}

	@Override
	public BusiDayStep getBusiDayStep(BusiDayStep busiDayStep) {
		return (BusiDayStep) myBatisDAO.findForObject(busiDayStep);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiDayStep> findBusiDayStepBySevenDay(String bsdspUser) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bsdspUser", bsdspUser);
		return myBatisDAO.findForList("findBusiDayStepBySevenDay", hashMap);
	}

	@Override
	public BusiDayStep getBusiDayStepByDayAndUser(String bsdspUser, String bsdspDay) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bsdspUser", bsdspUser);
		hashMap.put("bsdspDay", bsdspDay);
		return (BusiDayStep) myBatisDAO.findForObject("getBusiDayStepByDayAndUser", hashMap);
	}

	@Override
	public int getSumBusiDayStepByWeek(String bsdspUser) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bsdspUser", bsdspUser);
		Integer sum = (Integer) myBatisDAO.findForObject("getSumBusiDayStepByWeek", hashMap);
		return sum == null ? 0 : sum;
	}

	@Override
	public int getSumBusiDayStepByMonth(String bsdspUser) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bsdspUser", bsdspUser);
		Integer sum = (Integer) myBatisDAO.findForObject("getSumBusiDayStepByMonth", hashMap);
		return sum == null ? 0 : sum;
	}

	@Override
	public BusiDayStep getBusiDayStepByOrd(String bsdspDay) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bsdspDay", bsdspDay);
		return (BusiDayStep) myBatisDAO.findForObject("getBusiDayStepByOrd", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiDayStep> findBusiDayStepForDayChat(String bsdspDay) {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("bsdspDay", bsdspDay);
		return myBatisDAO.findForList("findBusiDayStepForDayChat", hashMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiDayStep> findBusiDayStepPage(BusiDayStep busiDayStep, String name, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiDayStep);
		hashMap.put("name", name);
		return myBatisDAO.findForPage("findBusiDayStepForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}