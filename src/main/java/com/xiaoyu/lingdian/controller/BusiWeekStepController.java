package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import com.xiaoyu.lingdian.service.BusiWeekStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiWeekStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiWeekStepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* 周步数表
*/
@Controller
@RequestMapping(value="/busiWeekStep")
@Api(value="busiWeekStep", description="周步数相关操作")
public class BusiWeekStepController extends BaseController {

	/**
	* 周步数表
	*/
	@Autowired
	private BusiWeekStepService busiWeekStepService;
	
	/**
	* 添加
	*
	* @param bswspUser 所属用户
	* @param bswspCdate 创建时间
	* @param bswspWeek 所属周
	* @param bswspStep 步数
	* @return
	*/
	@ApiOperation(value="添加", httpMethod = "POST", notes = "添加")
	@RequestMapping(value="/add/busiWeekStep", method=RequestMethod.POST)
	public void addBusiWeekStep (
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bswspUser", required = true) String bswspUser,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bswspCdate", required = true) Date bswspCdate,
					@ApiParam(value="所属周", required = true) @RequestParam(value="bswspWeek", required = true) String bswspWeek,
					@ApiParam(value="步数", required = true) @RequestParam(value="bswspStep", required = true) Integer bswspStep,
					HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin addBusiWeekStep");
		BusiWeekStep busiWeekStep = new BusiWeekStep();
		String uuid = RandomUtil.generateString(16);
		busiWeekStep.setBswspUuid(uuid);
				busiWeekStep.setBswspUser(bswspUser);
				busiWeekStep.setBswspCdate(bswspCdate);
				busiWeekStep.setBswspWeek(bswspWeek);
				busiWeekStep.setBswspStep(bswspStep);
		
		busiWeekStepService.insertBusiWeekStep(busiWeekStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[BusiWeekStepController]:end addBusiWeekStep");
	}

	/**
	* 修改
	*
	* @param bswspUuid 标识UUID
	* @param bswspUser 所属用户
	* @param bswspCdate 创建时间
	* @param bswspWeek 所属周
	* @param bswspStep 步数
	* @return
	*/
	@RequestMapping(value="/update/busiWeekStep", method=RequestMethod.POST)
	@ApiOperation(value="修改", httpMethod = "POST", notes = "修改")
	public void updateBusiWeekStep (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bswspUuid", required = true) String bswspUuid,
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bswspUser", required = true) String bswspUser,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bswspCdate", required = true) Date bswspCdate,
					@ApiParam(value="所属周", required = true) @RequestParam(value="bswspWeek", required = true) String bswspWeek,
					@ApiParam(value="步数", required = true) @RequestParam(value="bswspStep", required = true) Integer bswspStep,
				HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin updateBusiWeekStep");
		BusiWeekStep busiWeekStep = new BusiWeekStep();
		busiWeekStep.setBswspUuid(bswspUuid);
		busiWeekStep.setBswspUser(bswspUser);
		busiWeekStep.setBswspCdate(bswspCdate);
		busiWeekStep.setBswspWeek(bswspWeek);
		busiWeekStep.setBswspStep(bswspStep);

		busiWeekStepService.updateBusiWeekStep(busiWeekStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[BusiWeekStepController]:end updateBusiWeekStep");
	}

	/**
	* 删除
	*
	* @param bswspUuid 标识UUID
	* @return
	*/
	@ApiOperation(value="删除", httpMethod = "POST", notes = "删除")
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteBusiWeekStep (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bswspUuid", required = true) String bswspUuid,
			HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin deleteBusiWeekStep");
		BusiWeekStep busiWeekStep = new BusiWeekStep();
		busiWeekStep.setBswspUuid(bswspUuid);

		busiWeekStepService.deleteBusiWeekStep(busiWeekStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[BusiWeekStepController]:end deleteBusiWeekStep");
	}

	/**
	* 批量删除
	*
	* @param bswspUuids UUID集合
	* @return
	*/
	@ApiOperation(value="批量删除", httpMethod = "POST", notes = "批量删除")
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchBusiWeekStep (
			@ApiParam(value="周步数标识集合(|拼接)", required = true) @RequestParam(value="bswspUuids", required = true) String bswspUuids,
			HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin deleteBatchBusiWeekStep");
		String[] uuids=bswspUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		busiWeekStepService.deleteBatchByIds(list);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[BusiWeekStepController]:end deleteBatchBusiWeekStep");
	}

	/**
	* 获取单个
	*
	* @param bswspUuid 标识UUID
	* @return
	*/
    @ApiOperation(value="获取单个", httpMethod = "POST", notes = "获取单个", response = BusiWeekStepVO.class)
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsBusiWeekStep (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bswspUuid", required = true)  String bswspUuid,
			HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin viewsBusiWeekStep");
		BusiWeekStep busiWeekStep = new BusiWeekStep();
		busiWeekStep.setBswspUuid(bswspUuid);
		busiWeekStep = busiWeekStepService.getBusiWeekStep(busiWeekStep);
		if(null == busiWeekStep){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "周步数不存在!"),response);
    		logger.info("[BusiWeekStepController]:end viewsBusiWeekStep");
			return;
		}

		BusiWeekStepVO busiWeekStepVO = new BusiWeekStepVO();
		busiWeekStepVO.convertPOToVO(busiWeekStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiWeekStepVO), response);
		logger.info("[BusiWeekStepController]:end viewsBusiWeekStep");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
    @ApiOperation(value="获取周步数列表", httpMethod = "POST", notes = "获取周步数列表", response = BusiWeekStepVO.class)
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findBusiWeekStepList (
			HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin findBusiWeekStepList");
		List<BusiWeekStep> lists = busiWeekStepService.findBusiWeekStepList();
		List<BusiWeekStepVO> vos = new ArrayList<BusiWeekStepVO>();
		BusiWeekStepVO vo;
		for (BusiWeekStep busiWeekStep : lists) {
			vo = new BusiWeekStepVO();
			vo.convertPOToVO(busiWeekStep);
			vos.add(vo);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[BusiWeekStepController]:end findBusiWeekStepList");
	}

	/**
	* 获取分页列表<Page>
	*
    * @param bswspUser 所属用户
    * @param bswspCdate 创建时间
    * @param bswspWeek 所属周
    * @param bswspStep 步数
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
    @ApiOperation(value="获取周步数分页列表", httpMethod = "POST", notes = "获取周步数分页列表", response = BusiWeekStepVO.class)
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findBusiWeekStepPage (
						@ApiParam(value="所属用户", required = true) @RequestParam(value="bswspUser", required = true) String bswspUser,
						@ApiParam(value="创建时间", required = true) @RequestParam(value="bswspCdate", required = true) Date bswspCdate,
						@ApiParam(value="所属周", required = true) @RequestParam(value="bswspWeek", required = true) String bswspWeek,
						@ApiParam(value="步数", required = true) @RequestParam(value="bswspStep", required = true) Integer bswspStep,
			            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
			HttpServletResponse response) {
		logger.info("[BusiWeekStepController]:begin findBusiWeekStepPage");
		BusiWeekStep busiWeekStep = new BusiWeekStep();
		busiWeekStep.setBswspUser(bswspUser);
		busiWeekStep.setBswspCdate(bswspCdate);
		busiWeekStep.setBswspWeek(bswspWeek);
		busiWeekStep.setBswspStep(bswspStep);
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 15;
		}
		Page<BusiWeekStep> page = busiWeekStepService.findBusiWeekStepPage(busiWeekStep, pageNum, pageSize);
		Page<BusiWeekStepVO> pageVO = new Page<BusiWeekStepVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<BusiWeekStepVO> vos = new ArrayList<BusiWeekStepVO>();
		BusiWeekStepVO vo;
		for (BusiWeekStep busiWeekStepPO : page.getResult()) {
			vo = new BusiWeekStepVO();
			vo.convertPOToVO(busiWeekStepPO);
			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[BusiWeekStepController]:end findBusiWeekStepPage");
	}

}