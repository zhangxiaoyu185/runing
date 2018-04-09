package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import com.xiaoyu.lingdian.service.BusiUserTitleService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiUserTitle;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiUserTitleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* 个人称号记录表
*/
@Controller
@RequestMapping(value="/busiUserTitle")
@Api(value="busiUserTitle", description="个人称号记录相关操作")
public class BusiUserTitleController extends BaseController {

	/**
	* 个人称号记录表
	*/
	@Autowired
	private BusiUserTitleService busiUserTitleService;
	
	/**
	* 添加
	*
	* @param bsuteUser 所属用户
	* @param bsuteMonth 所属月
	* @param bsuteTitle 获得称号
	* @param bsuteCdate 创建时间
	* @return
	*/
	@ApiOperation(value="添加", httpMethod = "POST", notes = "添加")
	@RequestMapping(value="/add/busiUserTitle", method=RequestMethod.POST)
	public void addBusiUserTitle (
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bsuteUser", required = true) String bsuteUser,
					@ApiParam(value="所属月", required = true) @RequestParam(value="bsuteMonth", required = true) String bsuteMonth,
					@ApiParam(value="获得称号", required = true) @RequestParam(value="bsuteTitle", required = true) String bsuteTitle,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsuteCdate", required = true) Date bsuteCdate,
					HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin addBusiUserTitle");
		BusiUserTitle busiUserTitle = new BusiUserTitle();
		String uuid = RandomUtil.generateString(16);
		busiUserTitle.setBsuteUuid(uuid);
				busiUserTitle.setBsuteUser(bsuteUser);
				busiUserTitle.setBsuteMonth(bsuteMonth);
				busiUserTitle.setBsuteTitle(bsuteTitle);
				busiUserTitle.setBsuteCdate(bsuteCdate);
		
		busiUserTitleService.insertBusiUserTitle(busiUserTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[BusiUserTitleController]:end addBusiUserTitle");
	}

	/**
	* 修改
	*
	* @param bsuteUuid 标识UUID
	* @param bsuteUser 所属用户
	* @param bsuteMonth 所属月
	* @param bsuteTitle 获得称号
	* @param bsuteCdate 创建时间
	* @return
	*/
	@RequestMapping(value="/update/busiUserTitle", method=RequestMethod.POST)
	@ApiOperation(value="修改", httpMethod = "POST", notes = "修改")
	public void updateBusiUserTitle (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsuteUuid", required = true) String bsuteUuid,
					@ApiParam(value="所属用户", required = true) @RequestParam(value="bsuteUser", required = true) String bsuteUser,
					@ApiParam(value="所属月", required = true) @RequestParam(value="bsuteMonth", required = true) String bsuteMonth,
					@ApiParam(value="获得称号", required = true) @RequestParam(value="bsuteTitle", required = true) String bsuteTitle,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsuteCdate", required = true) Date bsuteCdate,
				HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin updateBusiUserTitle");
		BusiUserTitle busiUserTitle = new BusiUserTitle();
		busiUserTitle.setBsuteUuid(bsuteUuid);
		busiUserTitle.setBsuteUser(bsuteUser);
		busiUserTitle.setBsuteMonth(bsuteMonth);
		busiUserTitle.setBsuteTitle(bsuteTitle);
		busiUserTitle.setBsuteCdate(bsuteCdate);

		busiUserTitleService.updateBusiUserTitle(busiUserTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[BusiUserTitleController]:end updateBusiUserTitle");
	}

	/**
	* 删除
	*
	* @param bsuteUuid 标识UUID
	* @return
	*/
	@ApiOperation(value="删除", httpMethod = "POST", notes = "删除")
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteBusiUserTitle (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsuteUuid", required = true) String bsuteUuid,
			HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin deleteBusiUserTitle");
		BusiUserTitle busiUserTitle = new BusiUserTitle();
		busiUserTitle.setBsuteUuid(bsuteUuid);

		busiUserTitleService.deleteBusiUserTitle(busiUserTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[BusiUserTitleController]:end deleteBusiUserTitle");
	}

	/**
	* 批量删除
	*
	* @param bsuteUuids UUID集合
	* @return
	*/
	@ApiOperation(value="批量删除", httpMethod = "POST", notes = "批量删除")
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchBusiUserTitle (
			@ApiParam(value="个人称号记录标识集合(|拼接)", required = true) @RequestParam(value="bsuteUuids", required = true) String bsuteUuids,
			HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin deleteBatchBusiUserTitle");
		String[] uuids=bsuteUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		busiUserTitleService.deleteBatchByIds(list);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[BusiUserTitleController]:end deleteBatchBusiUserTitle");
	}

	/**
	* 获取单个
	*
	* @param bsuteUuid 标识UUID
	* @return
	*/
    @ApiOperation(value="获取单个", httpMethod = "POST", notes = "获取单个", response = BusiUserTitleVO.class)
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsBusiUserTitle (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsuteUuid", required = true)  String bsuteUuid,
			HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin viewsBusiUserTitle");
		BusiUserTitle busiUserTitle = new BusiUserTitle();
		busiUserTitle.setBsuteUuid(bsuteUuid);
		busiUserTitle = busiUserTitleService.getBusiUserTitle(busiUserTitle);
		if(null == busiUserTitle){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "个人称号记录不存在!"),response);
    		logger.info("[BusiUserTitleController]:end viewsBusiUserTitle");
			return;
		}

		BusiUserTitleVO busiUserTitleVO = new BusiUserTitleVO();
		busiUserTitleVO.convertPOToVO(busiUserTitle);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiUserTitleVO), response);
		logger.info("[BusiUserTitleController]:end viewsBusiUserTitle");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
    @ApiOperation(value="获取个人称号记录列表", httpMethod = "POST", notes = "获取个人称号记录列表", response = BusiUserTitleVO.class)
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findBusiUserTitleList (
			HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin findBusiUserTitleList");
		List<BusiUserTitle> lists = busiUserTitleService.findBusiUserTitleList();
		List<BusiUserTitleVO> vos = new ArrayList<BusiUserTitleVO>();
		BusiUserTitleVO vo;
		for (BusiUserTitle busiUserTitle : lists) {
			vo = new BusiUserTitleVO();
			vo.convertPOToVO(busiUserTitle);
			vos.add(vo);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[BusiUserTitleController]:end findBusiUserTitleList");
	}

	/**
	* 获取分页列表<Page>
	*
    * @param bsuteUser 所属用户
    * @param bsuteMonth 所属月
    * @param bsuteTitle 获得称号
    * @param bsuteCdate 创建时间
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
    @ApiOperation(value="获取个人称号记录分页列表", httpMethod = "POST", notes = "获取个人称号记录分页列表", response = BusiUserTitleVO.class)
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findBusiUserTitlePage (
						@ApiParam(value="所属用户", required = true) @RequestParam(value="bsuteUser", required = true) String bsuteUser,
						@ApiParam(value="所属月", required = true) @RequestParam(value="bsuteMonth", required = true) String bsuteMonth,
						@ApiParam(value="获得称号", required = true) @RequestParam(value="bsuteTitle", required = true) String bsuteTitle,
						@ApiParam(value="创建时间", required = true) @RequestParam(value="bsuteCdate", required = true) Date bsuteCdate,
			            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
			HttpServletResponse response) {
		logger.info("[BusiUserTitleController]:begin findBusiUserTitlePage");
		BusiUserTitle busiUserTitle = new BusiUserTitle();
		busiUserTitle.setBsuteUser(bsuteUser);
		busiUserTitle.setBsuteMonth(bsuteMonth);
		busiUserTitle.setBsuteTitle(bsuteTitle);
		busiUserTitle.setBsuteCdate(bsuteCdate);
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 15;
		}
		Page<BusiUserTitle> page = busiUserTitleService.findBusiUserTitlePage(busiUserTitle, pageNum, pageSize);
		Page<BusiUserTitleVO> pageVO = new Page<BusiUserTitleVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<BusiUserTitleVO> vos = new ArrayList<BusiUserTitleVO>();
		BusiUserTitleVO vo;
		for (BusiUserTitle busiUserTitlePO : page.getResult()) {
			vo = new BusiUserTitleVO();
			vo.convertPOToVO(busiUserTitlePO);
			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[BusiUserTitleController]:end findBusiUserTitlePage");
	}

}