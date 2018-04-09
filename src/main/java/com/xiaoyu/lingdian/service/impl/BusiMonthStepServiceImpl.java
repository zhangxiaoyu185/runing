package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiMonthStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiMonthStepService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 月步数表
*/
@Service("busiMonthStepService")
public class BusiMonthStepServiceImpl implements BusiMonthStepService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiMonthStep(BusiMonthStep busiMonthStep) {
		myBatisDAO.insert(busiMonthStep);
		return true;
	}

	@Override
	public boolean updateBusiMonthStep(BusiMonthStep busiMonthStep) {
		myBatisDAO.update(busiMonthStep);
		return true;
	}

	@Override
	public boolean deleteBusiMonthStep(BusiMonthStep busiMonthStep) {
		myBatisDAO.delete(busiMonthStep);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiMonthStepByIds",hashMap);
		return true;
	}

	@Override
	public BusiMonthStep getBusiMonthStep(BusiMonthStep busiMonthStep) {
		return (BusiMonthStep) myBatisDAO.findForObject(busiMonthStep);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiMonthStep> findBusiMonthStepList() {
		return myBatisDAO.findForList("findBusiMonthStepForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiMonthStep> findBusiMonthStepPage(BusiMonthStep busiMonthStep, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiMonthStep);
		return myBatisDAO.findForPage("findBusiMonthStepForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}