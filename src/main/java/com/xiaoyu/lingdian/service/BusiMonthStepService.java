package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiMonthStep;

import java.util.List;

/**
* 月步数表
*/
public interface BusiMonthStepService {

	/**
	* 添加
	* @param busiMonthStep
	* @return
	*/
	public boolean insertBusiMonthStep(BusiMonthStep busiMonthStep);

	/**
	* 修改
	* @param busiMonthStep
	* @return
	*/
	public boolean updateBusiMonthStep(BusiMonthStep busiMonthStep);

	/**
	* 删除
	* @param busiMonthStep
	* @return
	*/
	public boolean deleteBusiMonthStep(BusiMonthStep busiMonthStep);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiMonthStep
	* @return
	*/
	public BusiMonthStep getBusiMonthStep(BusiMonthStep busiMonthStep);

	/**
	* 查询所有List
	* @return List
	*/
	public List<BusiMonthStep> findBusiMonthStepList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiMonthStep> findBusiMonthStepPage(BusiMonthStep busiMonthStep, int pageNum, int pageSize);

}