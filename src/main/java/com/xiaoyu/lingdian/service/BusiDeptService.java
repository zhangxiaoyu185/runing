package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDept;

import java.util.List;

/**
* 部门表
*/
public interface BusiDeptService {

	/**
	* 添加
	* @param busiDept
	* @return
	*/
	public boolean insertBusiDept(BusiDept busiDept);

	/**
	* 修改
	* @param busiDept
	* @return
	*/
	public boolean updateBusiDept(BusiDept busiDept);

	/**
	* 删除
	* @param busiDept
	* @return
	*/
	public boolean deleteBusiDept(BusiDept busiDept);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiDept
	* @return
	*/
	public BusiDept getBusiDept(BusiDept busiDept);

	/**
	* 查询所有List
	* @return List
	*/
	public List<BusiDept> findBusiDeptList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiDept> findBusiDeptPage(BusiDept busiDept, int pageNum, int pageSize);

}