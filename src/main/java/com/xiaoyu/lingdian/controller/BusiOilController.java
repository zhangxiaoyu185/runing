package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.ExcelUtils;
import com.xiaoyu.lingdian.tool.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.*;

import com.xiaoyu.lingdian.service.BusiOilService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiOil;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiOilVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 油卡表
 */
@Controller
@RequestMapping(value = "/busiOil")
@Api(value = "busiOil", description = "油卡相关操作")
public class BusiOilController extends BaseController {

    /**
     * 油卡表
     */
    @Autowired
    private BusiOilService busiOilService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 申请油卡状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     *
     * @param bsoilName        绑定姓名
     * @param bsoilMobile      绑定手机号
     * @param bsoilCarNo 车牌
     * @param bsoilDrivingNo 行驶证号
     * @param bsoilUser        申请人
     * @param bsoilAddress    邮寄地址
     * @return
     */
    @ApiOperation(value = "(前台调用)申请油卡", httpMethod = "POST", notes = "(前台调用)申请油卡")
    @RequestMapping(value = "/apply/busiOil", method = RequestMethod.POST)
    public void applyBusiOil(
            @ApiParam(value = "绑定姓名", required = true) @RequestParam(value = "bsoilName", required = true) String bsoilName,
            @ApiParam(value = "绑定手机号", required = true) @RequestParam(value = "bsoilMobile", required = true) String bsoilMobile,
            @ApiParam(value = "车牌", required = true) @RequestParam(value = "bsoilCarNo", required = true) String bsoilCarNo,
            @ApiParam(value = "行驶证号", required = true) @RequestParam(value = "bsoilDrivingNo", required = true) String bsoilDrivingNo,
            @ApiParam(value = "申请人", required = true) @RequestParam(value = "bsoilUser", required = true) String bsoilUser,
            @ApiParam(value = "邮寄地址", required = true) @RequestParam(value = "bsoilAddress", required = true) String bsoilAddress,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin applyBusiOil");
        BusiOil busiOil = new BusiOil();
        String uuid = RandomUtil.generateString(16);
        busiOil.setBsoilUuid(uuid);
        busiOil.setBsoilName(bsoilName);
        busiOil.setBsoilMobile(bsoilMobile);
        busiOil.setBsoilCarNo(bsoilCarNo);
        busiOil.setBsoilDrivingNo(bsoilDrivingNo);
        busiOil.setBsoilUser(bsoilUser);
        busiOil.setBsoilStatus(1);
        busiOil.setBsoilCdate(new Date());
        busiOil.setBsoilUdate(new Date());
        busiOil.setBsoilAgent("1");
        busiOil.setBsoilDeleteFlag(1);
        busiOil.setBsoilAddress(bsoilAddress);
        busiOilService.insertBusiOil(busiOil);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "申请成功!"), response);
        logger.info("[BusiOilController]:end applyBusiOil");
    }

    /**
     * 添加油卡状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     *
     * @param bsoilCode        油卡卡号
     * @param bsoilName        绑定姓名
     * @param bsoilMobile      绑定手机号
     * @param bsoilUser        申请人
     * @return
     */
    @ApiOperation(value = "(前台调用)添加油卡", httpMethod = "POST", notes = "(前台调用)添加油卡")
    @RequestMapping(value = "/add/busiOil", method = RequestMethod.POST)
    public void addBusiOil(
            @ApiParam(value = "油卡卡号", required = true) @RequestParam(value = "bsoilCode", required = true) String bsoilCode,
            @ApiParam(value = "绑定姓名", required = true) @RequestParam(value = "bsoilName", required = true) String bsoilName,
            @ApiParam(value = "绑定手机号", required = true) @RequestParam(value = "bsoilMobile", required = true) String bsoilMobile,
            @ApiParam(value = "申请人", required = true) @RequestParam(value = "bsoilUser", required = true) String bsoilUser,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin addBusiOil");
        BusiOil busiOil = new BusiOil();
        String uuid = RandomUtil.generateString(16);
        busiOil.setBsoilUuid(uuid);
        busiOil.setBsoilCode(bsoilCode);
        busiOil.setBsoilName(bsoilName);
        busiOil.setBsoilMobile(bsoilMobile);
        busiOil.setBsoilUser(bsoilUser);
        busiOil.setBsoilStatus(5);
        busiOil.setBsoilCdate(new Date());
        busiOil.setBsoilUdate(new Date());
        busiOil.setBsoilAgent("1");
        busiOil.setBsoilDeleteFlag(1);
        busiOilService.insertBusiOil(busiOil);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "添加油卡成功!"), response);
        logger.info("[BusiOilController]:end addBusiOil");
    }

    /**
     * 修改状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     *
     * @param bsoilUuid        标识UUID
     * @param bsoilCode        油卡卡号
     * @param bsoilExpress     快递单号
     * @param bsoilStatus      状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     * @return
     */
    @RequestMapping(value = "/update/busiOil", method = RequestMethod.POST)
    @ApiOperation(value = "(前后台通用)修改状态", httpMethod = "POST", notes = "(前后台通用)修改状态")
    public void updateBusiOil(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsoilUuid", required = true) String bsoilUuid,
            @ApiParam(value = "油卡卡号", required = false) @RequestParam(value = "bsoilCode", required = false) String bsoilCode,
            @ApiParam(value = "快递单号", required = false) @RequestParam(value = "bsoilExpress", required = false) String bsoilExpress,
            @ApiParam(value = "状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）", required = true) @RequestParam(value = "bsoilStatus", required = true) Integer bsoilStatus,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin updateBusiOil");
        if(bsoilStatus == 3) {
            if(StringUtil.isEmpty(bsoilCode)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "油卡卡号必填!"), response);
                logger.info("[BusiOilController]:end updateBusiOil");
            }
        }
        if(bsoilStatus == 4) {
            if(StringUtil.isEmpty(bsoilExpress)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "快递单号必填!"), response);
                logger.info("[BusiOilController]:end updateBusiOil");
            }
        }
        BusiOil busiOil = new BusiOil();
        busiOil.setBsoilUuid(bsoilUuid);
        busiOil.setBsoilCode(bsoilCode);
        busiOil.setBsoilExpress(bsoilExpress);
        busiOil.setBsoilStatus(bsoilStatus);
        busiOil.setBsoilUdate(new Date());
        busiOilService.updateBusiOil(busiOil);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[BusiOilController]:end updateBusiOil");
    }

    /**
     * 删除
     *
     * @param bsoilUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "(前后台通用)删除", httpMethod = "POST", notes = "(前后台通用)删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteBusiOil(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsoilUuid", required = true) String bsoilUuid,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin deleteBusiOil");
        BusiOil busiOil = new BusiOil();
        busiOil.setBsoilUuid(bsoilUuid);
        busiOil.setBsoilDeleteFlag(0);
        busiOilService.updateBusiOil(busiOil);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[BusiOilController]:end deleteBusiOil");
    }

    /**
     * 批量删除
     *
     * @param bsoilUuids UUID集合
     * @return
     */
    @ApiOperation(value = "(前后台通用)批量删除", httpMethod = "POST", notes = "(前后台通用)批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchBusiOil(
            @ApiParam(value = "油卡标识集合(|拼接)", required = true) @RequestParam(value = "bsoilUuids", required = true) String bsoilUuids,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin deleteBatchBusiOil");
        String[] uuids = bsoilUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        busiOilService.updateBatchBusiOilByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[BusiOilController]:end deleteBatchBusiOil");
    }

    /**
     * 获取单个油卡信息
     *
     * @param bsoilUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "(前后台通用)获取单个油卡信息", httpMethod = "GET", notes = "(前后台通用)获取单个油卡信息", response = BusiOilVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsBusiOil(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "bsoilUuid", required = true) String bsoilUuid,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin viewsBusiOil");
        BusiOil busiOil = new BusiOil();
        busiOil.setBsoilUuid(bsoilUuid);
        busiOil = busiOilService.getBusiOil(busiOil);
        if (null == busiOil) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "油卡不存在!"), response);
            logger.info("[BusiOilController]:end viewsBusiOil");
            return;
        }

        BusiOilVO busiOilVO = new BusiOilVO();
        busiOilVO.convertPOToVO(busiOil);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个油卡信息成功", busiOilVO), response);
        logger.info("[BusiOilController]:end viewsBusiOil");
    }

    /**
     * 我的油卡分页列表<Page>
     *
     * @param bsoilUser        申请人
     * @param pageNum          页码
     * @param pageSize         页数
     * @return
     */
    @ApiOperation(value = "(前台调用)我的油卡分页列表", httpMethod = "GET", notes = "(前台调用)我的油卡分页列表", response = BusiOilVO.class)
    @RequestMapping(value = "/my/find/by/cnd", method = RequestMethod.GET)
    public void findBusiOilPageByMy(
            @ApiParam(value = "申请人", required = true) @RequestParam(value = "bsoilUser", required = true) String bsoilUser,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin findBusiOilPageByMy");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiOil> page = busiOilService.findBusiOilForPagesByMy(bsoilUser, pageNum, pageSize);
        Page<BusiOilVO> pageVO = new Page<BusiOilVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiOil busiOil : page.getResult()) {
            hashUserUuids.add(busiOil.getBsoilUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiOilVO> vos = new ArrayList<BusiOilVO>();
        BusiOilVO vo;
        for (BusiOil busiOilPO : page.getResult()) {
            vo = new BusiOilVO();
            vo.convertPOToVO(busiOilPO);
            vo.setBsoilUserHead(userMap.get(busiOilPO.getBsoilUser())==null?null:userMap.get(busiOilPO.getBsoilUser()).getCrusrWxHeadimgurl());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "我的油卡分页列表获取成功!", pageVO), response);
        logger.info("[BusiOilController]:end findBusiOilPageByMy");
    }

    /**
     * 后台获取分页列表<Page>
     *
     * @param bsoilCode        油卡卡号
     * @param bsoilName        绑定姓名
     * @param bsoilMobile      绑定手机号
     * @param bsoilStatus      状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）
     * @param pageNum          页码
     * @param pageSize         页数
     * @return
     */
    @ApiOperation(value = "后台获取分页列表", httpMethod = "GET", notes = "后台获取分页列表", response = BusiOilVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findBusiOilPage(
            @ApiParam(value = "油卡卡号", required = false) @RequestParam(value = "bsoilCode", required = false) String bsoilCode,
            @ApiParam(value = "绑定姓名", required = false) @RequestParam(value = "bsoilName", required = false) String bsoilName,
            @ApiParam(value = "绑定手机号", required = false) @RequestParam(value = "bsoilMobile", required = false) String bsoilMobile,
            @ApiParam(value = "状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）", required = false) @RequestParam(value = "bsoilStatus", required = false) Integer bsoilStatus,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiOilController]:begin findBusiOilPage");
        BusiOil newBusiOil = new BusiOil();
        newBusiOil.setBsoilCode(bsoilCode);
        newBusiOil.setBsoilName(bsoilName);
        newBusiOil.setBsoilMobile(bsoilMobile);
        newBusiOil.setBsoilStatus(bsoilStatus);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<BusiOil> page = busiOilService.findBusiOilPage(newBusiOil, pageNum, pageSize);
        Page<BusiOilVO> pageVO = new Page<BusiOilVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiOil busiOil : page.getResult()) {
            hashUserUuids.add(busiOil.getBsoilUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiOilVO> vos = new ArrayList<BusiOilVO>();
        BusiOilVO vo;
        for (BusiOil busiOilPO : page.getResult()) {
            vo = new BusiOilVO();
            vo.convertPOToVO(busiOilPO);
            vo.setBsoilUserHead(userMap.get(busiOilPO.getBsoilUser())==null?null:userMap.get(busiOilPO.getBsoilUser()).getCrusrWxHeadimgurl());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiOilController]:end findBusiOilPage");
    }

    /**
     * 后台导出油卡
     *
     * @return
     */
    @ApiOperation(value = "后台导出油卡", httpMethod = "GET", notes = "后台导出油卡")
    @RequestMapping(value = "/excel/export/oil", method = RequestMethod.GET)
    public void exportExcelOil(
            @ApiParam(value = "油卡卡号", required = false) @RequestParam(value = "bsoilCode", required = false) String bsoilCode,
            @ApiParam(value = "绑定姓名", required = false) @RequestParam(value = "bsoilName", required = false) String bsoilName,
            @ApiParam(value = "绑定手机号", required = false) @RequestParam(value = "bsoilMobile", required = false) String bsoilMobile,
            @ApiParam(value = "状态（1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过）", required = false) @RequestParam(value = "bsoilStatus", required = false) Integer bsoilStatus,
            HttpServletResponse response) throws IOException {
        logger.info("[BusiOilController]:begin exportExcelOil");
        final Map<String, String> linkedHeader = new LinkedHashMap<>();
        linkedHeader.put("bsoilCode", "油卡卡号");
        linkedHeader.put("bsoilName", "绑定姓名");
        linkedHeader.put("bsoilMobile", "绑定手机号");
        linkedHeader.put("bsoilExpress", "快递单号");
        linkedHeader.put("bsoilCarNo", "车牌");
        linkedHeader.put("bsoilDrivingNo", "行驶证号");
        linkedHeader.put("bsoilStatus", "状态");
        linkedHeader.put("bsoilAddress", "邮寄地址");
        linkedHeader.put("bsoilCdate", "创建时间");
        linkedHeader.put("bsoilUdate", "更新时间");

        BusiOil newBusiOil = new BusiOil();
        newBusiOil.setBsoilCode(bsoilCode);
        newBusiOil.setBsoilName(bsoilName);
        newBusiOil.setBsoilMobile(bsoilMobile);
        newBusiOil.setBsoilStatus(bsoilStatus);

        List<BusiOil> blist = busiOilService.findBusiOilList(newBusiOil);
        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiOil busiOil : blist) {
            hashUserUuids.add(busiOil.getBsoilUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiOilVO> list = new ArrayList<BusiOilVO>();
        BusiOilVO vo;
        for (BusiOil busiOilPO : blist) {
            vo = new BusiOilVO();
            vo.convertPOToVO(busiOilPO);
            vo.setBsoilUserHead(userMap.get(busiOilPO.getBsoilUser())==null?null:userMap.get(busiOilPO.getBsoilUser()).getCrusrWxHeadimgurl());
            list.add(vo);
        }

        List<String[]> contents = ExcelUtils.buildExcelList(list, new ExcelUtils.ExcelMapper<BusiOilVO>() {

            @Override
            public String[] mapHeader() {
                return toExcelHeader(linkedHeader);
            }

            @Override
            public String[] mapRowData(BusiOilVO obj) {
                return toExcelLine(obj, linkedHeader);
            }
        });
        ExcelUtils.writeXls(response, contents, "oil_list");
        logger.info("[BusiOilController]:end exportExcelOil");
    }

    private String[] toExcelLine(BusiOilVO obj, Map<String, String> linkedHeader) {
        List<String> list = new ArrayList<>();
        for (String key : linkedHeader.keySet()) {
            if(key.equals("bsoilCode")) {
                list.add(obj.getBsoilCode());
            }
            if(key.equals("bsoilName")) {
                list.add(obj.getBsoilName());
            }
            if(key.equals("bsoilMobile")) {
                list.add(obj.getBsoilMobile());
            }
            if(key.equals("bsoilExpress")) {
                list.add(obj.getBsoilExpress());
            }
            if(key.equals("bsoilCarNo")) {
                list.add(obj.getBsoilCarNo());
            }
            if(key.equals("bsoilDrivingNo")) {
                list.add(obj.getBsoilDrivingNo());
            }
            if(key.equals("bsoilStatus")) {
                if(obj.getBsoilStatus() == 1) {
                    list.add("等待审核");
                } else if(obj.getBsoilStatus() == 2){
                    list.add("提交中石化");
                } else if(obj.getBsoilStatus() == 3){
                    list.add("注册成功");
                } else if(obj.getBsoilStatus() == 4){
                    list.add("邮寄中");
                } else if(obj.getBsoilStatus() == 5){
                    list.add("正常使用");
                } else if(obj.getBsoilStatus() == 6){
                    list.add("审核不通过");
                } else {
                    list.add("未知");
                }
            }
            if(key.equals("bsoilAddress")) {
                list.add(obj.getBsoilAddress());
            }
            if(key.equals("bsoilCdate")) {
                list.add(DateUtil.formatDefaultDate(obj.getBsoilCdate()));
            }
            if(key.equals("bsoilUdate")) {
                list.add(DateUtil.formatDefaultDate(obj.getBsoilUdate()));
            }
        }
        return list.toArray(new String[list.size()]);
    }

    private String[] toExcelHeader(Map<String, String> linkedHeader) {
        Collection<String> titles = linkedHeader.values();
        return new ArrayList<>(titles).toArray(new String[titles.size()]);
    }
}