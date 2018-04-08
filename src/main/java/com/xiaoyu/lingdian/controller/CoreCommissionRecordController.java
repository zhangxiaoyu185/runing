package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.service.CoreCommissionRecordService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreCommissionRecord;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreCommissionRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 佣金记录表
 */
@Controller
@RequestMapping(value = "/coreCommissionRecord")
@Api(value = "coreCommissionRecord", description = "佣金记录相关操作")
public class CoreCommissionRecordController extends BaseController {

    /**
     * 佣金记录表
     */
    @Autowired
    private CoreCommissionRecordService coreCommissionRecordService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 获取单个佣金记录
     *
     * @param crcrdUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个佣金记录", httpMethod = "GET", notes = "获取单个佣金记录", response = CoreCommissionRecordVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreCommissionRecord(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crcrdUuid", required = true) String crcrdUuid,
            HttpServletResponse response) {
        logger.info("[CoreCommissionRecordController]:begin viewsCoreCommissionRecord");
        CoreCommissionRecord coreCommissionRecord = new CoreCommissionRecord();
        coreCommissionRecord.setCrcrdUuid(crcrdUuid);
        coreCommissionRecord = coreCommissionRecordService.getCoreCommissionRecord(coreCommissionRecord);
        if (null == coreCommissionRecord) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "佣金记录不存在!"), response);
            logger.info("[CoreCommissionRecordController]:end viewsCoreCommissionRecord");
            return;
        }

        CoreCommissionRecordVO coreCommissionRecordVO = new CoreCommissionRecordVO();
        coreCommissionRecordVO.convertPOToVO(coreCommissionRecord);

        HashSet<String> hashUserUuids = new HashSet<String>();
        hashUserUuids.add(coreCommissionRecord.getCrcrdBeUser());
        hashUserUuids.add(coreCommissionRecord.getCrcrdUser());
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);
        coreCommissionRecordVO.setCrcrdBeUserMobile(userMap.get(coreCommissionRecord.getCrcrdBeUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdBeUser()).getCrusrMobile());
        coreCommissionRecordVO.setCrcrdBeUserName(userMap.get(coreCommissionRecord.getCrcrdBeUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdBeUser()).getCrusrName());
        coreCommissionRecordVO.setCrcrdBeUserHead(userMap.get(coreCommissionRecord.getCrcrdBeUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdBeUser()).getCrusrWxHeadimgurl());
        coreCommissionRecordVO.setCrcrdUserMobile(userMap.get(coreCommissionRecord.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdUser()).getCrusrMobile());
        coreCommissionRecordVO.setCrcrdUserName(userMap.get(coreCommissionRecord.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdUser()).getCrusrName());
        coreCommissionRecordVO.setCrcrdUserHead(userMap.get(coreCommissionRecord.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdUser()).getCrusrWxHeadimgurl());
        coreCommissionRecordVO.setCrusrCdate(userMap.get(coreCommissionRecord.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecord.getCrcrdUser()).getCrusrCdate());

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreCommissionRecordVO), response);
        logger.info("[CoreCommissionRecordController]:end viewsCoreCommissionRecord");
    }

    /**
     *  获取我所有的佣金记录分页列表<Page>
     *
     * @param crcrdBeUser 佣金用户
     * @param pageNum     页码
     * @param pageSize    页数
     * @return
     */
    @ApiOperation(value = "获取我所有的佣金记录分页列表", httpMethod = "GET", notes = "获取我所有的佣金记录分页列表", response = CoreCommissionRecordVO.class)
    @RequestMapping(value = "/find/my/page", method = RequestMethod.GET)
    public void findCoreCommissionRecordPageMy(
            @ApiParam(value = "佣金用户", required = true) @RequestParam(value = "crcrdBeUser", required = true) String crcrdBeUser,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreCommissionRecordController]:begin findCoreCommissionRecordPageMy");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreCommissionRecord> page = coreCommissionRecordService.findCoreCommissionRecordForMy(crcrdBeUser, pageNum, pageSize);
        Page<CoreCommissionRecordVO> pageVO = new Page<CoreCommissionRecordVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreCommissionRecord coreCommissionRecord : page.getResult()) {
            hashUserUuids.add(coreCommissionRecord.getCrcrdBeUser());
            hashUserUuids.add(coreCommissionRecord.getCrcrdUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreCommissionRecordVO> vos = new ArrayList<CoreCommissionRecordVO>();
        CoreCommissionRecordVO vo;
        for (CoreCommissionRecord coreCommissionRecordPO : page.getResult()) {
            vo = new CoreCommissionRecordVO();
            vo.convertPOToVO(coreCommissionRecordPO);
            vo.setCrcrdUserMobile(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrMobile());
            vo.setCrcrdUserName(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrName());
            vo.setCrcrdUserHead(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrWxHeadimgurl());
            vo.setCrusrCdate(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrCdate());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我所有的佣金记录分页列表成功!", pageVO), response);
        logger.info("[CoreCommissionRecordController]:end findCoreCommissionRecordPageMy");
    }

    /**
     * 后台获取佣金记录分页列表<Page>
     *
     * @param rechargemobile   充值用户手机号
     * @param mobile    佣金用户手机号
     * @param pageNum     页码
     * @param pageSize    页数
     * @return
     */
    @ApiOperation(value = "后台获取佣金记录分页列表", httpMethod = "GET", notes = "后台获取佣金记录分页列表", response = CoreCommissionRecordVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreCommissionRecordPage(
            @ApiParam(value = "充值用户手机号", required = false) @RequestParam(value = "rechargemobile", required = false) String rechargemobile,
            @ApiParam(value = "佣金用户手机号", required = false) @RequestParam(value = "mobile", required = false) String mobile,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreCommissionRecordController]:begin findCoreCommissionRecordPage");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreCommissionRecord> page = coreCommissionRecordService.findCoreCommissionRecordPage(new CoreCommissionRecord(), rechargemobile, mobile, pageNum, pageSize);
        Page<CoreCommissionRecordVO> pageVO = new Page<CoreCommissionRecordVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreCommissionRecord coreCommissionRecord : page.getResult()) {
            hashUserUuids.add(coreCommissionRecord.getCrcrdBeUser());
            hashUserUuids.add(coreCommissionRecord.getCrcrdUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreCommissionRecordVO> vos = new ArrayList<CoreCommissionRecordVO>();
        CoreCommissionRecordVO vo;
        for (CoreCommissionRecord coreCommissionRecordPO : page.getResult()) {
            vo = new CoreCommissionRecordVO();
            vo.convertPOToVO(coreCommissionRecordPO);
            vo.setCrcrdBeUserMobile(userMap.get(coreCommissionRecordPO.getCrcrdBeUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdBeUser()).getCrusrMobile());
            vo.setCrcrdBeUserName(userMap.get(coreCommissionRecordPO.getCrcrdBeUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdBeUser()).getCrusrName());
            vo.setCrcrdBeUserHead(userMap.get(coreCommissionRecordPO.getCrcrdBeUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdBeUser()).getCrusrWxHeadimgurl());
            vo.setCrcrdUserMobile(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrMobile());
            vo.setCrcrdUserName(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrName());
            vo.setCrcrdUserHead(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrWxHeadimgurl());
            vo.setCrusrCdate(userMap.get(coreCommissionRecordPO.getCrcrdUser()) == null ? null : userMap.get(coreCommissionRecordPO.getCrcrdUser()).getCrusrCdate());
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreCommissionRecordController]:end findCoreCommissionRecordPage");
    }

}