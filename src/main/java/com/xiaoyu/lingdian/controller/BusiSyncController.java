package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import com.xiaoyu.lingdian.service.BusiSyncService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiSync;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiSyncVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* 同步表
*/
@Controller
@RequestMapping(value="/busiSync")
@Api(value="busiSync", description="同步相关操作")
public class BusiSyncController extends BaseController {

	/**
	* 同步表
	*/
	@Autowired
	private BusiSyncService busiSyncService;
	
	/**
	* 添加
	*
	* @param bssycUser 所属用户
	* @param bssycSdate 上次同步时间
	* @return
	*/
	@ApiOperation(value="添加", httpMethod = "POST", notes = "添加")
	@RequestMapping(value="/add/busiSync", method=RequestMethod.POST)
	public void addBusiSync (
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bssycUser", required = true) String bssycUser,
					@ApiParam(value="上次同步时间", required = true) @RequestParam(value="bssycSdate", required = true) Date bssycSdate,
					HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin addBusiSync");
		BusiSync busiSync = new BusiSync();
		String uuid = RandomUtil.generateString(16);
		busiSync.setBssycUuid(uuid);
				busiSync.setBssycUser(bssycUser);
				busiSync.setBssycSdate(bssycSdate);
		
		busiSyncService.insertBusiSync(busiSync);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[BusiSyncController]:end addBusiSync");
	}

	/**
	* 修改
	*
	* @param bssycUuid 标识UUID
	* @param bssycUser 所属用户
	* @param bssycSdate 上次同步时间
	* @return
	*/
	@RequestMapping(value="/update/busiSync", method=RequestMethod.POST)
	@ApiOperation(value="修改", httpMethod = "POST", notes = "修改")
	public void updateBusiSync (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bssycUuid", required = true) String bssycUuid,
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bssycUser", required = true) String bssycUser,
					@ApiParam(value="上次同步时间", required = true) @RequestParam(value="bssycSdate", required = true) Date bssycSdate,
				HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin updateBusiSync");
		BusiSync busiSync = new BusiSync();
		busiSync.setBssycUuid(bssycUuid);
		busiSync.setBssycUser(bssycUser);
		busiSync.setBssycSdate(bssycSdate);

		busiSyncService.updateBusiSync(busiSync);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[BusiSyncController]:end updateBusiSync");
	}

	/**
	* 删除
	*
	* @param bssycUuid 标识UUID
	* @return
	*/
	@ApiOperation(value="删除", httpMethod = "POST", notes = "删除")
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteBusiSync (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bssycUuid", required = true) String bssycUuid,
			HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin deleteBusiSync");
		BusiSync busiSync = new BusiSync();
		busiSync.setBssycUuid(bssycUuid);

		busiSyncService.deleteBusiSync(busiSync);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[BusiSyncController]:end deleteBusiSync");
	}

	/**
	* 批量删除
	*
	* @param bssycUuids UUID集合
	* @return
	*/
	@ApiOperation(value="批量删除", httpMethod = "POST", notes = "批量删除")
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchBusiSync (
			@ApiParam(value="同步标识集合(|拼接)", required = true) @RequestParam(value="bssycUuids", required = true) String bssycUuids,
			HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin deleteBatchBusiSync");
		String[] uuids=bssycUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		busiSyncService.deleteBatchByIds(list);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[BusiSyncController]:end deleteBatchBusiSync");
	}

	/**
	* 获取单个
	*
	* @param bssycUuid 标识UUID
	* @return
	*/
    @ApiOperation(value="获取单个", httpMethod = "POST", notes = "获取单个", response = BusiSyncVO.class)
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsBusiSync (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bssycUuid", required = true)  String bssycUuid,
			HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin viewsBusiSync");
		BusiSync busiSync = new BusiSync();
		busiSync.setBssycUuid(bssycUuid);
		busiSync = busiSyncService.getBusiSync(busiSync);
		if(null == busiSync){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "同步不存在!"),response);
    		logger.info("[BusiSyncController]:end viewsBusiSync");
			return;
		}

		BusiSyncVO busiSyncVO = new BusiSyncVO();
		busiSyncVO.convertPOToVO(busiSync);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiSyncVO), response);
		logger.info("[BusiSyncController]:end viewsBusiSync");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
    @ApiOperation(value="获取同步列表", httpMethod = "POST", notes = "获取同步列表", response = BusiSyncVO.class)
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findBusiSyncList (
			HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin findBusiSyncList");
		List<BusiSync> lists = busiSyncService.findBusiSyncList();
		List<BusiSyncVO> vos = new ArrayList<BusiSyncVO>();
		BusiSyncVO vo;
		for (BusiSync busiSync : lists) {
			vo = new BusiSyncVO();
			vo.convertPOToVO(busiSync);
			vos.add(vo);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[BusiSyncController]:end findBusiSyncList");
	}

	/**
	* 获取分页列表<Page>
	*
    * @param bssycUser 所属用户
    * @param bssycSdate 上次同步时间
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
    @ApiOperation(value="获取同步分页列表", httpMethod = "POST", notes = "获取同步分页列表", response = BusiSyncVO.class)
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findBusiSyncPage (
						@ApiParam(value="所属用户", required = true) @RequestParam(value="bssycUser", required = true) String bssycUser,
						@ApiParam(value="上次同步时间", required = true) @RequestParam(value="bssycSdate", required = true) Date bssycSdate,
			            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
			HttpServletResponse response) {
		logger.info("[BusiSyncController]:begin findBusiSyncPage");
		BusiSync busiSync = new BusiSync();
		busiSync.setBssycUser(bssycUser);
		busiSync.setBssycSdate(bssycSdate);
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 15;
		}
		Page<BusiSync> page = busiSyncService.findBusiSyncPage(busiSync, pageNum, pageSize);
		Page<BusiSyncVO> pageVO = new Page<BusiSyncVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<BusiSyncVO> vos = new ArrayList<BusiSyncVO>();
		BusiSyncVO vo;
		for (BusiSync busiSyncPO : page.getResult()) {
			vo = new BusiSyncVO();
			vo.convertPOToVO(busiSyncPO);
			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[BusiSyncController]:end findBusiSyncPage");
	}

}