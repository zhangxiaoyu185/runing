package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiIntegralDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiIntegralDetailService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 积分明细表
*/
@Service("busiIntegralDetailService")
public class BusiIntegralDetailServiceImpl implements BusiIntegralDetailService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail) {
		myBatisDAO.insert(busiIntegralDetail);
		return true;
	}

	@Override
	public boolean updateBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail) {
		myBatisDAO.update(busiIntegralDetail);
		return true;
	}

	@Override
	public boolean deleteBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail) {
		myBatisDAO.delete(busiIntegralDetail);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiIntegralDetailByIds",hashMap);
		return true;
	}

	@Override
	public BusiIntegralDetail getBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail) {
		return (BusiIntegralDetail) myBatisDAO.findForObject(busiIntegralDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiIntegralDetail> findBusiIntegralDetailList() {
		return myBatisDAO.findForList("findBusiIntegralDetailForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiIntegralDetail> findBusiIntegralDetailPage(BusiIntegralDetail busiIntegralDetail, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiIntegralDetail);
		return myBatisDAO.findForPage("findBusiIntegralDetailForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}