package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDeptTitle;

import java.util.List;

/**
* 部门称号记录表
*/
public interface BusiDeptTitleService {

	/**
	* 添加
	* @param busiDeptTitle
	* @return
	*/
	public boolean insertBusiDeptTitle(BusiDeptTitle busiDeptTitle);

	/**
	* 修改
	* @param busiDeptTitle
	* @return
	*/
	public boolean updateBusiDeptTitle(BusiDeptTitle busiDeptTitle);

	/**
	* 删除
	* @param busiDeptTitle
	* @return
	*/
	public boolean deleteBusiDeptTitle(BusiDeptTitle busiDeptTitle);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiDeptTitle
	* @return
	*/
	public BusiDeptTitle getBusiDeptTitle(BusiDeptTitle busiDeptTitle);

	/**
	* 查询所有List
	* @return List
	*/
	public List<BusiDeptTitle> findBusiDeptTitleList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiDeptTitle> findBusiDeptTitlePage(BusiDeptTitle busiDeptTitle, int pageNum, int pageSize);

}