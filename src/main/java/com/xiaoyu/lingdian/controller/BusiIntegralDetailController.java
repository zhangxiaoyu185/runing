package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import com.xiaoyu.lingdian.service.BusiIntegralDetailService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiIntegralDetail;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiIntegralDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* 积分明细表
*/
@Controller
@RequestMapping(value="/busiIntegralDetail")
@Api(value="busiIntegralDetail", description="积分明细相关操作")
public class BusiIntegralDetailController extends BaseController {

	/**
	* 积分明细表
	*/
	@Autowired
	private BusiIntegralDetailService busiIntegralDetailService;
	
	/**
	* 添加
	*
	* @param bsidlDire 增减方向(1增2减)
	* @param bsidlNum 数值
	* @param bsidlRemark 备注
	* @param bsidlUser 被修改人
	* @param bsidlCdate 创建时间
	* @param bsidlOper 操作人
	* @return
	*/
	@ApiOperation(value="添加", httpMethod = "POST", notes = "添加")
	@RequestMapping(value="/add/busiIntegralDetail", method=RequestMethod.POST)
	public void addBusiIntegralDetail (
					@ApiParam(value="增减方向(1增2减)", required = true) @RequestParam(value="bsidlDire", required = true) Integer bsidlDire,
					@ApiParam(value="数值", required = true) @RequestParam(value="bsidlNum", required = true) Integer bsidlNum,
					@ApiParam(value="备注", required = true) @RequestParam(value="bsidlRemark", required = true) String bsidlRemark,
					@ApiParam(value="被修改人", required = true) @RequestParam(value="bsidlUser", required = true) String bsidlUser,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsidlCdate", required = true) Date bsidlCdate,
					@ApiParam(value="操作人", required = true) @RequestParam(value="bsidlOper", required = true) String bsidlOper,
					HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin addBusiIntegralDetail");
		BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
		String uuid = RandomUtil.generateString(16);
		busiIntegralDetail.setBsidlUuid(uuid);
				busiIntegralDetail.setBsidlDire(bsidlDire);
				busiIntegralDetail.setBsidlNum(bsidlNum);
				busiIntegralDetail.setBsidlRemark(bsidlRemark);
				busiIntegralDetail.setBsidlUser(bsidlUser);
				busiIntegralDetail.setBsidlCdate(bsidlCdate);
				busiIntegralDetail.setBsidlOper(bsidlOper);
		
		busiIntegralDetailService.insertBusiIntegralDetail(busiIntegralDetail);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"),response);
		logger.info("[BusiIntegralDetailController]:end addBusiIntegralDetail");
	}

	/**
	* 修改
	*
	* @param bsidlUuid 标识UUID
	* @param bsidlDire 增减方向(1增2减)
	* @param bsidlNum 数值
	* @param bsidlRemark 备注
	* @param bsidlUser 被修改人
	* @param bsidlCdate 创建时间
	* @param bsidlOper 操作人
	* @return
	*/
	@RequestMapping(value="/update/busiIntegralDetail", method=RequestMethod.POST)
	@ApiOperation(value="修改", httpMethod = "POST", notes = "修改")
	public void updateBusiIntegralDetail (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsidlUuid", required = true) String bsidlUuid,
					@ApiParam(value="增减方向(1增2减)", required = true) @RequestParam(value="bsidlDire", required = true) Integer bsidlDire,
					@ApiParam(value="数值", required = true) @RequestParam(value="bsidlNum", required = true) Integer bsidlNum,
					@ApiParam(value="备注", required = true) @RequestParam(value="bsidlRemark", required = true) String bsidlRemark,
					@ApiParam(value="被修改人", required = true) @RequestParam(value="bsidlUser", required = true) String bsidlUser,
					@ApiParam(value="创建时间", required = true) @RequestParam(value="bsidlCdate", required = true) Date bsidlCdate,
					@ApiParam(value="操作人", required = true) @RequestParam(value="bsidlOper", required = true) String bsidlOper,
				HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin updateBusiIntegralDetail");
		BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
		busiIntegralDetail.setBsidlUuid(bsidlUuid);
		busiIntegralDetail.setBsidlDire(bsidlDire);
		busiIntegralDetail.setBsidlNum(bsidlNum);
		busiIntegralDetail.setBsidlRemark(bsidlRemark);
		busiIntegralDetail.setBsidlUser(bsidlUser);
		busiIntegralDetail.setBsidlCdate(bsidlCdate);
		busiIntegralDetail.setBsidlOper(bsidlOper);

		busiIntegralDetailService.updateBusiIntegralDetail(busiIntegralDetail);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"),response);
		logger.info("[BusiIntegralDetailController]:end updateBusiIntegralDetail");
	}

	/**
	* 删除
	*
	* @param bsidlUuid 标识UUID
	* @return
	*/
	@ApiOperation(value="删除", httpMethod = "POST", notes = "删除")
	@RequestMapping(value="/delete/one", method=RequestMethod.POST)
	public void deleteBusiIntegralDetail (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsidlUuid", required = true) String bsidlUuid,
			HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin deleteBusiIntegralDetail");
		BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
		busiIntegralDetail.setBsidlUuid(bsidlUuid);

		busiIntegralDetailService.deleteBusiIntegralDetail(busiIntegralDetail);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"),response);
		logger.info("[BusiIntegralDetailController]:end deleteBusiIntegralDetail");
	}

	/**
	* 批量删除
	*
	* @param bsidlUuids UUID集合
	* @return
	*/
	@ApiOperation(value="批量删除", httpMethod = "POST", notes = "批量删除")
	@RequestMapping(value="/delete/batch", method=RequestMethod.POST)
	public void deleteBatchBusiIntegralDetail (
			@ApiParam(value="积分明细标识集合(|拼接)", required = true) @RequestParam(value="bsidlUuids", required = true) String bsidlUuids,
			HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin deleteBatchBusiIntegralDetail");
		String[] uuids=bsidlUuids.split("\\|");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < uuids.length; i++) {
			list.add(uuids[i]);
		}
		if (list.size() == 0) {
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
			return;
		}
		busiIntegralDetailService.deleteBatchByIds(list);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"),response);
		logger.info("[BusiIntegralDetailController]:end deleteBatchBusiIntegralDetail");
	}

	/**
	* 获取单个
	*
	* @param bsidlUuid 标识UUID
	* @return
	*/
    @ApiOperation(value="获取单个", httpMethod = "POST", notes = "获取单个", response = BusiIntegralDetailVO.class)
	@RequestMapping(value="/views", method=RequestMethod.POST)
	public void viewsBusiIntegralDetail (
			@ApiParam(value="标识UUID", required = true) @RequestParam(value="bsidlUuid", required = true)  String bsidlUuid,
			HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin viewsBusiIntegralDetail");
		BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
		busiIntegralDetail.setBsidlUuid(bsidlUuid);
		busiIntegralDetail = busiIntegralDetailService.getBusiIntegralDetail(busiIntegralDetail);
		if(null == busiIntegralDetail){
			writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "积分明细不存在!"),response);
    		logger.info("[BusiIntegralDetailController]:end viewsBusiIntegralDetail");
			return;
		}

		BusiIntegralDetailVO busiIntegralDetailVO = new BusiIntegralDetailVO();
		busiIntegralDetailVO.convertPOToVO(busiIntegralDetail);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", busiIntegralDetailVO), response);
		logger.info("[BusiIntegralDetailController]:end viewsBusiIntegralDetail");
	}

	/**
	* 获取列表<List>
	* 
	* @return
	*/
    @ApiOperation(value="获取积分明细列表", httpMethod = "POST", notes = "获取积分明细列表", response = BusiIntegralDetailVO.class)
	@RequestMapping(value="/find/all", method=RequestMethod.POST)
	public void findBusiIntegralDetailList (
			HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin findBusiIntegralDetailList");
		List<BusiIntegralDetail> lists = busiIntegralDetailService.findBusiIntegralDetailList();
		List<BusiIntegralDetailVO> vos = new ArrayList<BusiIntegralDetailVO>();
		BusiIntegralDetailVO vo;
		for (BusiIntegralDetail busiIntegralDetail : lists) {
			vo = new BusiIntegralDetailVO();
			vo.convertPOToVO(busiIntegralDetail);
			vos.add(vo);
		}

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos),response);
		logger.info("[BusiIntegralDetailController]:end findBusiIntegralDetailList");
	}

	/**
	* 获取分页列表<Page>
	*
    * @param bsidlDire 增减方向(1增2减)
    * @param bsidlNum 数值
    * @param bsidlRemark 备注
    * @param bsidlUser 被修改人
    * @param bsidlCdate 创建时间
    * @param bsidlOper 操作人
	* @param pageNum 页码
	* @param pageSize 页数
	* @return
	*/
    @ApiOperation(value="获取积分明细分页列表", httpMethod = "POST", notes = "获取积分明细分页列表", response = BusiIntegralDetailVO.class)
	@RequestMapping(value="/find/by/cnd", method=RequestMethod.POST)
	public void findBusiIntegralDetailPage (
						@ApiParam(value="增减方向(1增2减)", required = true) @RequestParam(value="bsidlDire", required = true) Integer bsidlDire,
						@ApiParam(value="数值", required = true) @RequestParam(value="bsidlNum", required = true) Integer bsidlNum,
						@ApiParam(value="备注", required = true) @RequestParam(value="bsidlRemark", required = true) String bsidlRemark,
						@ApiParam(value="被修改人", required = true) @RequestParam(value="bsidlUser", required = true) String bsidlUser,
						@ApiParam(value="创建时间", required = true) @RequestParam(value="bsidlCdate", required = true) Date bsidlCdate,
						@ApiParam(value="操作人", required = true) @RequestParam(value="bsidlOper", required = true) String bsidlOper,
			            @ApiParam(value="第几页", required = false) @RequestParam(value="pageNum", required = false) Integer pageNum,
            @ApiParam(value="每页数量", required = false) @RequestParam(value="pageSize", required = false) Integer pageSize,
			HttpServletResponse response) {
		logger.info("[BusiIntegralDetailController]:begin findBusiIntegralDetailPage");
		BusiIntegralDetail busiIntegralDetail = new BusiIntegralDetail();
		busiIntegralDetail.setBsidlDire(bsidlDire);
		busiIntegralDetail.setBsidlNum(bsidlNum);
		busiIntegralDetail.setBsidlRemark(bsidlRemark);
		busiIntegralDetail.setBsidlUser(bsidlUser);
		busiIntegralDetail.setBsidlCdate(bsidlCdate);
		busiIntegralDetail.setBsidlOper(bsidlOper);
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 15;
		}
		Page<BusiIntegralDetail> page = busiIntegralDetailService.findBusiIntegralDetailPage(busiIntegralDetail, pageNum, pageSize);
		Page<BusiIntegralDetailVO> pageVO = new Page<BusiIntegralDetailVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
		List<BusiIntegralDetailVO> vos = new ArrayList<BusiIntegralDetailVO>();
		BusiIntegralDetailVO vo;
		for (BusiIntegralDetail busiIntegralDetailPO : page.getResult()) {
			vo = new BusiIntegralDetailVO();
			vo.convertPOToVO(busiIntegralDetailPO);
			vos.add(vo);
		}
		pageVO.setResult(vos);

		writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO),response);
		logger.info("[BusiIntegralDetailController]:end findBusiIntegralDetailPage");
	}

}