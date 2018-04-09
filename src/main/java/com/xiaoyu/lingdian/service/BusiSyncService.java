package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiSync;

import java.util.List;

/**
* 同步表
*/
public interface BusiSyncService {

	/**
	* 添加
	* @param busiSync
	* @return
	*/
	public boolean insertBusiSync(BusiSync busiSync);

	/**
	* 修改
	* @param busiSync
	* @return
	*/
	public boolean updateBusiSync(BusiSync busiSync);

	/**
	* 删除
	* @param busiSync
	* @return
	*/
	public boolean deleteBusiSync(BusiSync busiSync);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiSync
	* @return
	*/
	public BusiSync getBusiSync(BusiSync busiSync);

	/**
	* 查询所有List
	* @return List
	*/
	public List<BusiSync> findBusiSyncList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiSync> findBusiSyncPage(BusiSync busiSync, int pageNum, int pageSize);

}