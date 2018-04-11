package com.xiaoyu.lingdian.service.impl;

import com.xiaoyu.lingdian.core.mybatis.dao.MyBatisDAO;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.core.mybatis.page.PageRequest;
import com.xiaoyu.lingdian.entity.BusiUserTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyu.lingdian.tool.BeanToMapUtil;
import com.xiaoyu.lingdian.service.BusiUserTitleService;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 个人称号记录表
*/
@Service("busiUserTitleService")
public class BusiUserTitleServiceImpl implements BusiUserTitleService {

	@Autowired
	private MyBatisDAO myBatisDAO;

	@Override
	public boolean insertBusiUserTitle(BusiUserTitle busiUserTitle) {
		myBatisDAO.insert(busiUserTitle);
		return true;
	}

	@Override
	public boolean updateBusiUserTitle(BusiUserTitle busiUserTitle) {
		myBatisDAO.update(busiUserTitle);
		return true;
	}

	@Override
	public boolean deleteBusiUserTitle(BusiUserTitle busiUserTitle) {
		myBatisDAO.delete(busiUserTitle);
		return true;
	}

	@Override
	public boolean deleteBatchByIds(List<String> list ) {
		if(CollectionUtils.isEmpty(list)) {
			return true;
		}
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("list", list);
		myBatisDAO.delete("deleteBatchBusiUserTitleByIds",hashMap);
		return true;
	}

	@Override
	public BusiUserTitle getBusiUserTitle(BusiUserTitle busiUserTitle) {
		return (BusiUserTitle) myBatisDAO.findForObject(busiUserTitle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusiUserTitle> findBusiUserTitleList() {
		return myBatisDAO.findForList("findBusiUserTitleForLists", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<BusiUserTitle> findBusiUserTitlePage(BusiUserTitle busiUserTitle, String name, int pageNum, int pageSize) {
		Map<String, Object> hashMap = BeanToMapUtil.objectToMapReflect(busiUserTitle);
		hashMap.put("name", name);
		return myBatisDAO.findForPage("findBusiUserTitleForPages", new PageRequest(pageNum, pageSize, hashMap));
	}

}