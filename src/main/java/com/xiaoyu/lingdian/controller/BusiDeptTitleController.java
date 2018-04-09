package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import com.xiaoyu.lingdian.service.BusiDeptTitleService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDeptTitle;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiDeptTitleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* 部门称号记录表
*/
@Controller
@RequestMapping(value="/busiDeptTitle")
@Api(value="busiDeptTitle", description="部门称号记录相关操作")
public class BusiDeptTitleController extends BaseController {

	/**
	* 部门称号记录表
	*/
	@Autowired
	private BusiDeptTitleService busiDeptTitleService;
	
	/**
	* 添加
	*
	* @param bsdteDept 所属部门
	* @param bsdteMonth 所属月
	* @param bsdteTitle 获得称号
	* @param bsdteCdate 创建时间
	* @return
	*/
	@ApiOperation(value="添加", httpMethod = "POST", notes = "添加")
	@RequestMapping(value="/add/busiDeptTitle", method=RequestMethod.POST)
	public void addBusiDeptTitle (
					@ApiParam(value="所属部门", required = true) @RequestParam(value="bsdteDept", required = true) String bsdteDept,
					@ApiParam(value="所属月", required = true) @RequestParam(value="bsdteMonth", required = true) String bsdteMonth,
					@ApiParam(value="获得称号", required = true) @RequestParam(value="bsdteTitle", required = true) String bsdteTitle,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsdteCdate", required = true) Date bsdteCdate,
					HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin addBusiDeptTitle");
		BusiDeptTitle busiDeptTitle = new BusiDeptTitle();
		String uuid = RandomUtil.generateString(16);
		busiDeptTitle.setBsdteUuid(uuid);
				busiDeptTitle.setBsdteDept(bsdteDept);
				busiDeptTitle.setBsdteMonth(bsdteMonth);
				busiDeptTitle.setBsdteTitle(bsdteTitle);
				busiDeptTitle.setBsdteCdate(bsdteCdate);
		
		busiDeptTitleService.insertBusiDeptTitle(busiDeptTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[BusiDeptTitleController]:end addBusiDeptTitle");
	}

	/**
	* 修改
	*
	* @param bsdteUuid 标识UUID
	* @param bsdteDept 所属部门
	* @param bsdteMonth 所属月
	* @param bsdteTitle 获得称号
	* @param bsdteCdate 创建时间
	* @return
	*/
	@RequestMapping(value="/update/busiDeptTitle", method=RequestMethod.POST)
	@ApiOperation(value="修改", httpMethod = "POST", notes = "修改")
	public void updateBusiDeptTitle (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsdteUuid", required = true) String bsdteUuid,
					@ApiParam(value="所属部门", required = true) @RequestParam(value="bsdteDept", required = true) String bsdteDept,
					@ApiParam(value="所属月", required = true) @RequestParam(value="bsdteMonth", required = true) String bsdteMonth,
					@ApiParam(value="获得称号", required = true) @RequestParam(value="bsdteTitle", required = true) String bsdteTitle,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsdteCdate", required = true) Date bsdteCdate,
				HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin updateBusiDeptTitle");
		BusiDeptTitle busiDeptTitle = new BusiDeptTitle();
		busiDeptTitle.setBsdteUuid(bsdteUuid);
		busiDeptTitle.setBsdteDept(bsdteDept);
		busiDeptTitle.setBsdteMonth(bsdteMonth);
		busiDeptTitle.setBsdteTitle(bsdteTitle);
		busiDeptTitle.setBsdteCdate(bsdteCdate);

		busiDeptTitleService.updateBusiDeptTitle(busiDeptTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[BusiDeptTitleController]:end updateBusiDeptTitle");
	}

	/**
	* 删除
	*
	* @param bsdteUuid 标识UUID
	* @return
	*/
	@ApiOperation(value="删除", httpMethod = "POST", notes = "删除")
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteBusiDeptTitle (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsdteUuid", required = true) String bsdteUuid,
			HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin deleteBusiDeptTitle");
		BusiDeptTitle busiDeptTitle = new BusiDeptTitle();
		busiDeptTitle.setBsdteUuid(bsdteUuid);

		busiDeptTitleService.deleteBusiDeptTitle(busiDeptTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[BusiDeptTitleController]:end deleteBusiDeptTitle");
	}

	/**
	* 批量删除
	*
	* @param bsdteUuids UUID集合
	* @return
	*/
	@ApiOperation(value="批量删除", httpMethod = "POST", notes = "批量删除")
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchBusiDeptTitle (
			@ApiParam(value="部门称号记录标识集合(|拼接)", required = true) @RequestParam(value="bsdteUuids", required = true) String bsdteUuids,
			HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin deleteBatchBusiDeptTitle");
		String[] uuids=bsdteUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		busiDeptTitleService.deleteBatchByIds(list);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[BusiDeptTitleController]:end deleteBatchBusiDeptTitle");
	}

	/**
	* 获取单个
	*
	* @param bsdteUuid 标识UUID
	* @return
	*/
    @ApiOperation(value="获取单个", httpMethod = "POST", notes = "获取单个", response = BusiDeptTitleVO.class)
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsBusiDeptTitle (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsdteUuid", required = true)  String bsdteUuid,
			HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin viewsBusiDeptTitle");
		BusiDeptTitle busiDeptTitle = new BusiDeptTitle();
		busiDeptTitle.setBsdteUuid(bsdteUuid);
		busiDeptTitle = busiDeptTitleService.getBusiDeptTitle(busiDeptTitle);
		if(null == busiDeptTitle){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "部门称号记录不存在!"),response);
    		logger.info("[BusiDeptTitleController]:end viewsBusiDeptTitle");
			return;
		}

		BusiDeptTitleVO busiDeptTitleVO = new BusiDeptTitleVO();
		busiDeptTitleVO.convertPOToVO(busiDeptTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiDeptTitleVO), response);
		logger.info("[BusiDeptTitleController]:end viewsBusiDeptTitle");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
    @ApiOperation(value="获取部门称号记录列表", httpMethod = "POST", notes = "获取部门称号记录列表", response = BusiDeptTitleVO.class)
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findBusiDeptTitleList (
			HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin findBusiDeptTitleList");
		List<BusiDeptTitle> lists = busiDeptTitleService.findBusiDeptTitleList();
		List<BusiDeptTitleVO> vos = new ArrayList<BusiDeptTitleVO>();
		BusiDeptTitleVO vo;
		for (BusiDeptTitle busiDeptTitle : lists) {
			vo = new BusiDeptTitleVO();
			vo.convertPOToVO(busiDeptTitle);
			vos.add(vo);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[BusiDeptTitleController]:end findBusiDeptTitleList");
	}

	/**
	* 获取分页列表<Page>
	*
    * @param bsdteDept 所属部门
    * @param bsdteMonth 所属月
    * @param bsdteTitle 获得称号
    * @param bsdteCdate 创建时间
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
    @ApiOperation(value="获取部门称号记录分页列表", httpMethod = "POST", notes = "获取部门称号记录分页列表", response = BusiDeptTitleVO.class)
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findBusiDeptTitlePage (
						@ApiParam(value="所属部门", required = true) @RequestParam(value="bsdteDept", required = true) String bsdteDept,
						@ApiParam(value="所属月", required = true) @RequestParam(value="bsdteMonth", required = true) String bsdteMonth,
						@ApiParam(value="获得称号", required = true) @RequestParam(value="bsdteTitle", required = true) String bsdteTitle,
						@ApiParam(value="创建时间", required = true) @RequestParam(value="bsdteCdate", required = true) Date bsdteCdate,
			            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
			HttpServletResponse response) {
		logger.info("[BusiDeptTitleController]:begin findBusiDeptTitlePage");
		BusiDeptTitle busiDeptTitle = new BusiDeptTitle();
		busiDeptTitle.setBsdteDept(bsdteDept);
		busiDeptTitle.setBsdteMonth(bsdteMonth);
		busiDeptTitle.setBsdteTitle(bsdteTitle);
		busiDeptTitle.setBsdteCdate(bsdteCdate);
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 15;
		}
		Page<BusiDeptTitle> page = busiDeptTitleService.findBusiDeptTitlePage(busiDeptTitle, pageNum, pageSize);
		Page<BusiDeptTitleVO> pageVO = new Page<BusiDeptTitleVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<BusiDeptTitleVO> vos = new ArrayList<BusiDeptTitleVO>();
		BusiDeptTitleVO vo;
		for (BusiDeptTitle busiDeptTitlePO : page.getResult()) {
			vo = new BusiDeptTitleVO();
			vo.convertPOToVO(busiDeptTitlePO);
			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[BusiDeptTitleController]:end findBusiDeptTitlePage");
	}

}