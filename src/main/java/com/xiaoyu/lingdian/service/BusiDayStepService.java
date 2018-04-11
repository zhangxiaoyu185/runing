package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDayStep;

import java.util.List;

/**
* 日步数表
*/
public interface BusiDayStepService {

	/**
	* 添加
	* @param busiDayStep
	* @return
	*/
	public boolean insertBusiDayStep(BusiDayStep busiDayStep);

	/**
	* 修改
	* @param busiDayStep
	* @return
	*/
	public boolean updateBusiDayStep(BusiDayStep busiDayStep);

	/**
	* 删除
	* @param busiDayStep
	* @return
	*/
	public boolean deleteBusiDayStep(BusiDayStep busiDayStep);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiDayStep
	* @return
	*/
	public BusiDayStep getBusiDayStep(BusiDayStep busiDayStep);

	/**
	 * 获取下一个日期
	 * @return List
	 */
	public BusiDayStep getBusiDayStepByOrd(String bsdspDay);

	/**
	* 日排行榜
	* @return List
	*/
	public List<BusiDayStep> findBusiDayStepForDayChat(String bsdspDay);

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiDayStep> findBusiDayStepPage(BusiDayStep busiDayStep, String name, int pageNum, int pageSize);

}