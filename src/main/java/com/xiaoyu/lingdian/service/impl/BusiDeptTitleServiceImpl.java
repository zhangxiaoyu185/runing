package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiDeptTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiDeptTitleService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 部门称号记录表
*/
@Service("busiDeptTitleService")
public class BusiDeptTitleServiceImpl implements BusiDeptTitleService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiDeptTitle(BusiDeptTitle busiDeptTitle) {
		myBatisDAO.insert(busiDeptTitle);
		return true;
	}

	@Override
	public boolean updateBusiDeptTitle(BusiDeptTitle busiDeptTitle) {
		myBatisDAO.update(busiDeptTitle);
		return true;
	}

	@Override
	public boolean deleteBusiDeptTitle(BusiDeptTitle busiDeptTitle) {
		myBatisDAO.delete(busiDeptTitle);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiDeptTitleByIds",hashMap);
		return true;
	}

	@Override
	public BusiDeptTitle getBusiDeptTitle(BusiDeptTitle busiDeptTitle) {
		return (BusiDeptTitle) myBatisDAO.findForObject(busiDeptTitle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiDeptTitle> findBusiDeptTitleList() {
		return myBatisDAO.findForList("findBusiDeptTitleForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiDeptTitle> findBusiDeptTitlePage(BusiDeptTitle busiDeptTitle, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiDeptTitle);
		return myBatisDAO.findForPage("findBusiDeptTitleForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}