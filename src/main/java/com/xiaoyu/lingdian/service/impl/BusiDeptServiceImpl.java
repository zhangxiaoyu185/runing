package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.mapper.SimpleMapMapper;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiDeptService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 部门表
*/
@Service("busiDeptService")
public class BusiDeptServiceImpl implements BusiDeptService {

	private static final SimpleMapMapper<String, BusiDept> DEPT_UUID_MAPPER = new SimpleMapMapper<String, BusiDept>() {
		@Override
		public String mapKey(BusiDept object, int rowNum) {
			return object.getBsdetUuid();
		}
	};

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiDept(BusiDept busiDept) {
		myBatisDAO.insert(busiDept);
		return true;
	}

	@Override
	public boolean updateBusiDept(BusiDept busiDept) {
		myBatisDAO.update(busiDept);
		return true;
	}

	@Override
	public boolean deleteBusiDept(BusiDept busiDept) {
		myBatisDAO.delete(busiDept);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiDeptByIds",hashMap);
		return true;
	}

	@Override
	public BusiDept getBusiDept(BusiDept busiDept) {
		return (BusiDept) myBatisDAO.findForObject(busiDept);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiDept> findBusiDeptList() {
		return myBatisDAO.findForList("findBusiDeptForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiDept> findBusiDeptPage(BusiDept busiDept, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiDept);
		return myBatisDAO.findForPage("findBusiDeptForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BusiDept> findBusiDeptMapByUuidList(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new HashMap<>();
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		return myBatisDAO.findForMap("findBusiDeptForLists", hashMap, DEPT_UUID_MAPPER);
	}

}