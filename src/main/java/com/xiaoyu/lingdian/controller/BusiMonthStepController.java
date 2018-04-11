package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import com.xiaoyu.lingdian.service.BusiMonthStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiMonthStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiMonthStepVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* 月步数表
*/
@Controller
@RequestMapping(value="/busiMonthStep")
@Api(value="busiMonthStep", description="月步数相关操作")
public class BusiMonthStepController extends BaseController {

	/**
	* 月步数表
	*/
	@Autowired
	private BusiMonthStepService busiMonthStepService;
	
	/**
	* 添加
	*
	* @param bsmspUser 所属用户
	* @param bsmspCdate 创建时间
	* @param bsmspMonth 所属月
	* @param bsmspStep 步数
	* @param bsmspScore 分值
	* @param bsmspOrd 序号
	* @return
	*/
	@ApiOperation(value="添加", httpMethod = "POST", notes = "添加")
	@RequestMapping(value="/add/busiMonthStep", method=RequestMethod.POST)
	public void addBusiMonthStep (
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bsmspUser", required = true) String bsmspUser,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsmspCdate", required = true) Date bsmspCdate,
					@ApiParam(value="所属月", required = true) @RequestParam(value="bsmspMonth", required = true) String bsmspMonth,
					@ApiParam(value="步数", required = true) @RequestParam(value="bsmspStep", required = true) Integer bsmspStep,
					@ApiParam(value="分值", required = true) @RequestParam(value="bsmspScore", required = true) Integer bsmspScore,
					@ApiParam(value="序号", required = true) @RequestParam(value="bsmspOrd", required = true) Integer bsmspOrd,
					HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin addBusiMonthStep");
		BusiMonthStep busiMonthStep = new BusiMonthStep();
		String uuid = RandomUtil.generateString(16);
		busiMonthStep.setBsmspUuid(uuid);
				busiMonthStep.setBsmspUser(bsmspUser);
				busiMonthStep.setBsmspCdate(bsmspCdate);
				busiMonthStep.setBsmspMonth(bsmspMonth);
				busiMonthStep.setBsmspStep(bsmspStep);
				busiMonthStep.setBsmspScore(bsmspScore);
				busiMonthStep.setBsmspOrd(bsmspOrd);
		
		busiMonthStepService.insertBusiMonthStep(busiMonthStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[BusiMonthStepController]:end addBusiMonthStep");
	}

	/**
	* 修改
	*
	* @param bsmspUuid 标识UUID
	* @param bsmspUser 所属用户
	* @param bsmspCdate 创建时间
	* @param bsmspMonth 所属月
	* @param bsmspStep 步数
	* @param bsmspScore 分值
	* @param bsmspOrd 序号
	* @return
	*/
	@RequestMapping(value="/update/busiMonthStep", method=RequestMethod.POST)
	@ApiOperation(value="修改", httpMethod = "POST", notes = "修改")
	public void updateBusiMonthStep (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsmspUuid", required = true) String bsmspUuid,
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bsmspUser", required = true) String bsmspUser,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsmspCdate", required = true) Date bsmspCdate,
					@ApiParam(value="所属月", required = true) @RequestParam(value="bsmspMonth", required = true) String bsmspMonth,
					@ApiParam(value="步数", required = true) @RequestParam(value="bsmspStep", required = true) Integer bsmspStep,
					@ApiParam(value="分值", required = true) @RequestParam(value="bsmspScore", required = true) Integer bsmspScore,
					@ApiParam(value="序号", required = true) @RequestParam(value="bsmspOrd", required = true) Integer bsmspOrd,
				HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin updateBusiMonthStep");
		BusiMonthStep busiMonthStep = new BusiMonthStep();
		busiMonthStep.setBsmspUuid(bsmspUuid);
		busiMonthStep.setBsmspUser(bsmspUser);
		busiMonthStep.setBsmspCdate(bsmspCdate);
		busiMonthStep.setBsmspMonth(bsmspMonth);
		busiMonthStep.setBsmspStep(bsmspStep);
		busiMonthStep.setBsmspScore(bsmspScore);
		busiMonthStep.setBsmspOrd(bsmspOrd);

		busiMonthStepService.updateBusiMonthStep(busiMonthStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[BusiMonthStepController]:end updateBusiMonthStep");
	}

	/**
	* 删除
	*
	* @param bsmspUuid 标识UUID
	* @return
	*/
	@ApiOperation(value="删除", httpMethod = "POST", notes = "删除")
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteBusiMonthStep (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsmspUuid", required = true) String bsmspUuid,
			HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin deleteBusiMonthStep");
		BusiMonthStep busiMonthStep = new BusiMonthStep();
		busiMonthStep.setBsmspUuid(bsmspUuid);

		busiMonthStepService.deleteBusiMonthStep(busiMonthStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[BusiMonthStepController]:end deleteBusiMonthStep");
	}

	/**
	* 批量删除
	*
	* @param bsmspUuids UUID集合
	* @return
	*/
	@ApiOperation(value="批量删除", httpMethod = "POST", notes = "批量删除")
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchBusiMonthStep (
			@ApiParam(value="月步数标识集合(|拼接)", required = true) @RequestParam(value="bsmspUuids", required = true) String bsmspUuids,
			HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin deleteBatchBusiMonthStep");
		String[] uuids=bsmspUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		busiMonthStepService.deleteBatchByIds(list);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[BusiMonthStepController]:end deleteBatchBusiMonthStep");
	}

	/**
	* 获取单个
	*
	* @param bsmspUuid 标识UUID
	* @return
	*/
    @ApiOperation(value="获取单个", httpMethod = "POST", notes = "获取单个", response = BusiMonthStepVO.class)
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsBusiMonthStep (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsmspUuid", required = true)  String bsmspUuid,
			HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin viewsBusiMonthStep");
		BusiMonthStep busiMonthStep = new BusiMonthStep();
		busiMonthStep.setBsmspUuid(bsmspUuid);
		busiMonthStep = busiMonthStepService.getBusiMonthStep(busiMonthStep);
		if(null == busiMonthStep){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "月步数不存在!"),response);
    		logger.info("[BusiMonthStepController]:end viewsBusiMonthStep");
			return;
		}

		BusiMonthStepVO busiMonthStepVO = new BusiMonthStepVO();
		busiMonthStepVO.convertPOToVO(busiMonthStep);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiMonthStepVO), response);
		logger.info("[BusiMonthStepController]:end viewsBusiMonthStep");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
    @ApiOperation(value="获取月步数列表", httpMethod = "POST", notes = "获取月步数列表", response = BusiMonthStepVO.class)
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findBusiMonthStepList (
			HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin findBusiMonthStepList");
		List<BusiMonthStep> lists = busiMonthStepService.findBusiMonthStepList();
		List<BusiMonthStepVO> vos = new ArrayList<BusiMonthStepVO>();
		BusiMonthStepVO vo;
		for (BusiMonthStep busiMonthStep : lists) {
			vo = new BusiMonthStepVO();
			vo.convertPOToVO(busiMonthStep);
			vos.add(vo);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[BusiMonthStepController]:end findBusiMonthStepList");
	}

	/**
	* 获取分页列表<Page>
	*
    * @param bsmspUser 所属用户
    * @param bsmspCdate 创建时间
    * @param bsmspMonth 所属月
    * @param bsmspStep 步数
    * @param bsmspScore 分值
    * @param bsmspOrd 序号
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
    @ApiOperation(value="获取月步数分页列表", httpMethod = "POST", notes = "获取月步数分页列表", response = BusiMonthStepVO.class)
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findBusiMonthStepPage (
						@ApiParam(value="所属用户", required = true) @RequestParam(value="bsmspUser", required = true) String bsmspUser,
						@ApiParam(value="创建时间", required = true) @RequestParam(value="bsmspCdate", required = true) Date bsmspCdate,
						@ApiParam(value="所属月", required = true) @RequestParam(value="bsmspMonth", required = true) String bsmspMonth,
						@ApiParam(value="步数", required = true) @RequestParam(value="bsmspStep", required = true) Integer bsmspStep,
						@ApiParam(value="分值", required = true) @RequestParam(value="bsmspScore", required = true) Integer bsmspScore,
						@ApiParam(value="序号", required = true) @RequestParam(value="bsmspOrd", required = true) Integer bsmspOrd,
			            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
			HttpServletResponse response) {
		logger.info("[BusiMonthStepController]:begin findBusiMonthStepPage");
		BusiMonthStep busiMonthStep = new BusiMonthStep();
		busiMonthStep.setBsmspUser(bsmspUser);
		busiMonthStep.setBsmspCdate(bsmspCdate);
		busiMonthStep.setBsmspMonth(bsmspMonth);
		busiMonthStep.setBsmspStep(bsmspStep);
		busiMonthStep.setBsmspScore(bsmspScore);
		busiMonthStep.setBsmspOrd(bsmspOrd);
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 15;
		}
		Page<BusiMonthStep> page = busiMonthStepService.findBusiMonthStepPage(busiMonthStep, pageNum, pageSize);
		Page<BusiMonthStepVO> pageVO = new Page<BusiMonthStepVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<BusiMonthStepVO> vos = new ArrayList<BusiMonthStepVO>();
		BusiMonthStepVO vo;
		for (BusiMonthStep busiMonthStepPO : page.getResult()) {
			vo = new BusiMonthStepVO();
			vo.convertPOToVO(busiMonthStepPO);
			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[BusiMonthStepController]:end findBusiMonthStepPage");
	}

}