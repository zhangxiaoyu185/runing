package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiUserTitle;

import java.util.List;

/**
* 个人称号记录表
*/
public interface BusiUserTitleService {

	/**
	* 添加
	* @param busiUserTitle
	* @return
	*/
	public boolean insertBusiUserTitle(BusiUserTitle busiUserTitle);

	/**
	* 修改
	* @param busiUserTitle
	* @return
	*/
	public boolean updateBusiUserTitle(BusiUserTitle busiUserTitle);

	/**
	* 删除
	* @param busiUserTitle
	* @return
	*/
	public boolean deleteBusiUserTitle(BusiUserTitle busiUserTitle);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiUserTitle
	* @return
	*/
	public BusiUserTitle getBusiUserTitle(BusiUserTitle busiUserTitle);

	/**
	* 查询所有List
	* @return List
	*/
	public List<BusiUserTitle> findBusiUserTitleList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiUserTitle> findBusiUserTitlePage(BusiUserTitle busiUserTitle, String name, int pageNum, int pageSize);

}