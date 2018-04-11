package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiWeekStep;

import java.util.List;

/**
* 周步数表
*/
public interface BusiWeekStepService {

	/**
	* 添加
	* @param busiWeekStep
	* @return
	*/
	public boolean insertBusiWeekStep(BusiWeekStep busiWeekStep);

	/**
	* 修改
	* @param busiWeekStep
	* @return
	*/
	public boolean updateBusiWeekStep(BusiWeekStep busiWeekStep);

	/**
	* 删除
	* @param busiWeekStep
	* @return
	*/
	public boolean deleteBusiWeekStep(BusiWeekStep busiWeekStep);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiWeekStep
	* @return
	*/
	public BusiWeekStep getBusiWeekStep(BusiWeekStep busiWeekStep);

	/**
	 * 获取下一个序号
	 * @return List
	 */
	public BusiWeekStep getBusiWeekStepByOrd(Integer bswspOrd);

	/**
	 * 周排行榜
	 * @return List
	 */
	public List<BusiWeekStep> findBusiWeekStepForWeekChat(Integer bswspOrd);

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiWeekStep> findBusiWeekStepPage(BusiWeekStep busiWeekStep, String name, int pageNum, int pageSize);

}