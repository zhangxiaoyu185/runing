package com.xiaoyu.lingdian.service;

import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiIntegralDetail;

import java.util.List;

/**
* 积分明细表
*/
public interface BusiIntegralDetailService {

	/**
	* 添加
	* @param busiIntegralDetail
	* @return
	*/
	public boolean insertBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail);

	/**
	* 修改
	* @param busiIntegralDetail
	* @return
	*/
	public boolean updateBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail);

	/**
	* 删除
	* @param busiIntegralDetail
	* @return
	*/
	public boolean deleteBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail);

	/**
	* 批量删除
	* @param list
	* @return boolean
	*/
	public boolean deleteBatchByIds(List<String> list);

	/**
	* 查询
	* @param busiIntegralDetail
	* @return
	*/
	public BusiIntegralDetail getBusiIntegralDetail(BusiIntegralDetail busiIntegralDetail);

	/**
	* 查询所有List
	* @return List
	*/
	public List<BusiIntegralDetail> findBusiIntegralDetailList();

	/**
	* 查询所有Page
	* @return Page
	*/
	public Page<BusiIntegralDetail> findBusiIntegralDetailPage(BusiIntegralDetail busiIntegralDetail, String name, int pageNum, int pageSize);

}