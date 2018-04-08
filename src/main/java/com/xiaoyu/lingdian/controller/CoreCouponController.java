package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.service.CoreUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;

import com.xiaoyu.lingdian.service.CoreCouponService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreCoupon;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreCouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 优惠券表
 */
@Controller
@RequestMapping(value = "/coreCoupon")
@Api(value = "coreCoupon", description = "优惠券相关操作")
public class CoreCouponController extends BaseController {

    /**
     * 优惠券表
     */
    @Autowired
    private CoreCouponService coreCouponService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 添加4未分配
     *
     * @param crcpnReduce     优惠券金额
     * @param count 生成数量
     * @param crcpnStart      生效时间
     * @param crcpnEnd        截止时间
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/coreCoupon", method = RequestMethod.POST)
    public void addCoreCoupon(
            @ApiParam(value = "优惠券金额", required = true) @RequestParam(value = "crcpnReduce", required = true) Integer crcpnReduce,
            @ApiParam(value = "生成数量", required = true) @RequestParam(value = "count", required = true) Integer count,
            @ApiParam(value = "生效时间", required = true) @RequestParam(value = "crcpnStart", required = true) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date crcpnStart,
            @ApiParam(value = "截止时间", required = true) @RequestParam(value = "crcpnEnd", required = true) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date crcpnEnd,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin addCoreCoupon");
        if(count > 1000) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "一次性生成优惠券数量不能大于1000!"), response);
            logger.info("[CoreCouponController]:end addCoreCoupon");
        }
        for (int i = 0; i < count; i++) {
            CoreCoupon coreCoupon = new CoreCoupon();
            String uuid = RandomUtil.generateString(16);
            coreCoupon.setCrcpnUuid(uuid);
            coreCoupon.setCrcpnUseStatus(4);
            coreCoupon.setCrcpnReduce(crcpnReduce);
            coreCoupon.setCrcpnCdate(new Date());
            coreCoupon.setCrcpnSendStatus(2);
            coreCoupon.setCrcpnStart(crcpnStart);
            coreCoupon.setCrcpnEnd(crcpnEnd);
            coreCouponService.insertCoreCoupon(coreCoupon);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreCouponController]:end addCoreCoupon");
    }

    /**
     * 修改优惠券金额
     *
     * @param crcpnReduce     优惠券金额
     * @return
     */
    @ApiOperation(value = "修改优惠券金额", httpMethod = "POST", notes = "修改优惠券金额")
    @RequestMapping(value = "/update/coreCoupon", method = RequestMethod.POST)
    public void updateCoreCoupon(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crcpnUuid", required = true) String crcpnUuid,
            @ApiParam(value = "优惠券金额", required = true) @RequestParam(value = "crcpnReduce", required = true) Integer crcpnReduce,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin updateCoreCoupon");
        CoreCoupon coreCoupon = new CoreCoupon();
        coreCoupon.setCrcpnUuid(crcpnUuid);
        coreCoupon.setCrcpnReduce(crcpnReduce);
        coreCouponService.updateCoreCoupon(coreCoupon);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改优惠券金额成功!"), response);
        logger.info("[CoreCouponController]:end updateCoreCoupon");
    }

    /**
     * 分配用户优惠券
     *
     * @param crcpnUuid       标识UUID
     * @param crcpnUser       所属用户
     * @return
     */
    @RequestMapping(value = "/send/user/coreCoupon", method = RequestMethod.POST)
    @ApiOperation(value = "分配用户优惠券", httpMethod = "POST", notes = "分配用户优惠券")
    public void sendUserCoreCoupon(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crcpnUuid", required = true) String crcpnUuid,
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "crcpnUser", required = true) String crcpnUser,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin sendUserCoreCoupon");
        CoreCoupon coreCoupon = new CoreCoupon();
        coreCoupon.setCrcpnUuid(crcpnUuid);
        coreCoupon.setCrcpnUser(crcpnUser);
        coreCoupon.setCrcpnUseStatus(2);
        coreCouponService.updateCoreCoupon(coreCoupon);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "分配用户优惠券成功!"), response);
        logger.info("[CoreCouponController]:end sendUserCoreCoupon");
    }

    /**
     * 删除
     *
     * @param crcpnUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreCoupon(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crcpnUuid", required = true) String crcpnUuid,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin deleteCoreCoupon");
        CoreCoupon coreCoupon = new CoreCoupon();
        coreCoupon.setCrcpnUuid(crcpnUuid);

        coreCouponService.deleteCoreCoupon(coreCoupon);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreCouponController]:end deleteCoreCoupon");
    }

    /**
     * 批量删除
     *
     * @param crcpnUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreCoupon(
            @ApiParam(value = "优惠券标识集合(|拼接)", required = true) @RequestParam(value = "crcpnUuids", required = true) String crcpnUuids,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin deleteBatchCoreCoupon");
        String[] uuids = crcpnUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            return;
        }
        coreCouponService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreCouponController]:end deleteBatchCoreCoupon");
    }

    /**
     * 获取我的优惠券数量（排除已过期的）
     *
     * @param crcpnUser         用户标志
     * @return
     */
    @ApiOperation(value = "获取我的优惠券数量（排除已过期的）", httpMethod = "GET", notes = "获取我的优惠券数量（排除已过期的）")
    @RequestMapping(value = "/get/my/coupon/count", method = RequestMethod.GET)
    public void getMyCouponCount(
            @ApiParam(value = "用户标志", required = true) @RequestParam(value = "crcpnUser", required = true) String crcpnUser,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin getMyCouponCount");

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我的优惠券数量成功!", this.coreCouponService.findCoreCouponForMyCount(crcpnUser)), response);
        logger.info("[CoreCouponController]:end getMyCouponCount");
    }

    /**
     * 获取单个优惠券
     *
     * @param crcpnUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个优惠券", httpMethod = "GET", notes = "获取单个优惠券", response = CoreCouponVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreCoupon(
            @ApiParam(value = "标识UUID", required = true) @RequestParam(value = "crcpnUuid", required = true) String crcpnUuid,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin viewsCoreCoupon");
        CoreCoupon coreCoupon = new CoreCoupon();
        coreCoupon.setCrcpnUuid(crcpnUuid);
        coreCoupon = coreCouponService.getCoreCoupon(coreCoupon);
        if (null == coreCoupon) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "优惠券不存在!"), response);
            logger.info("[CoreCouponController]:end viewsCoreCoupon");
            return;
        }

        CoreCouponVO coreCouponVO = new CoreCouponVO();
        coreCouponVO.convertPOToVO(coreCoupon);

        if(null != coreCoupon.getCrcpnUser()) {
            CoreUser coreUser = new CoreUser();
            coreUser.setCrusrUuid(coreCoupon.getCrcpnUser());
            coreUser = coreUserService.getCoreUser(coreUser);
            if (null != coreUser) {
                coreCouponVO.setCrcpnUserMobile(coreUser.getCrusrMobile());
                coreCouponVO.setCrcpnUserName(coreUser.getCrusrName());
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreCouponVO), response);
        logger.info("[CoreCouponController]:end viewsCoreCoupon");
    }

    /**
     * 获取我所有可使用的优惠券列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取我所有可使用的优惠券列表<List>", httpMethod = "GET", notes = "获取我所有可使用的优惠券列表<List>", response = CoreCouponVO.class)
    @RequestMapping(value = "/find/my/list", method = RequestMethod.GET)
    public void findCoreCouponListMy(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "crcpnUser", required = true) String crcpnUser,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin findCoreCouponListMy");
        List<CoreCoupon> lists = coreCouponService.findCoreCouponListMy(crcpnUser);

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreCoupon newCoreCoupon : lists) {
            if(null != newCoreCoupon.getCrcpnUser()) {
                hashUserUuids.add(newCoreCoupon.getCrcpnUser());
            }
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreCouponVO> vos = new ArrayList<CoreCouponVO>();
        CoreCouponVO vo;
        for (CoreCoupon coreCoupon : lists) {
            vo = new CoreCouponVO();
            vo.convertPOToVO(coreCoupon);
            if(null != coreCoupon.getCrcpnUser()) {
                vo.setCrcpnUserMobile(userMap.get(coreCoupon.getCrcpnUser()) == null ? null : userMap.get(coreCoupon.getCrcpnUser()).getCrusrMobile());
                vo.setCrcpnUserName(userMap.get(coreCoupon.getCrcpnUser()) == null ? null : userMap.get(coreCoupon.getCrcpnUser()).getCrusrName());
            }
            vos.add(vo);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我所有可使用的优惠券列表成功!", vos), response);
        logger.info("[CoreCouponController]:end findCoreCouponListMy");
    }

    /**
     * 获取我所有的优惠券分页列表<Page>
     *
     * @param crcpnUser       所属用户
     * @param crcpnUseStatus  使用状态（1已使用，2可使用，3已过期，4未分配）
     * @param pageNum         页码
     * @param pageSize        页数
     * @return
     */
    @ApiOperation(value = "获取我所有的优惠券分页列表", httpMethod = "GET", notes = "获取我所有的优惠券分页列表", response = CoreCouponVO.class)
    @RequestMapping(value = "/find/my/page", method = RequestMethod.GET)
    public void findCoreCouponPageMy(
            @ApiParam(value = "所属用户", required = true) @RequestParam(value = "crcpnUser", required = true) String crcpnUser,
            @ApiParam(value = "使用状态（1已使用，2可使用，3已过期，4未分配）", required = true) @RequestParam(value = "crcpnUseStatus", required = true) Integer crcpnUseStatus,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin findCoreCouponPageMy");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreCoupon> page = coreCouponService.findCoreCouponPageMy(crcpnUser, crcpnUseStatus, pageNum, pageSize);
        Page<CoreCouponVO> pageVO = new Page<CoreCouponVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreCoupon newCoreCoupon : page.getResult()) {
            if(null != newCoreCoupon.getCrcpnUser()) {
                hashUserUuids.add(newCoreCoupon.getCrcpnUser());
            }
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreCouponVO> vos = new ArrayList<CoreCouponVO>();
        CoreCouponVO vo;
        for (CoreCoupon coreCouponPO : page.getResult()) {
            vo = new CoreCouponVO();
            vo.convertPOToVO(coreCouponPO);
            if(null != coreCouponPO.getCrcpnUser()) {
                vo.setCrcpnUserMobile(userMap.get(coreCouponPO.getCrcpnUser()) == null ? null : userMap.get(coreCouponPO.getCrcpnUser()).getCrusrMobile());
                vo.setCrcpnUserName(userMap.get(coreCouponPO.getCrcpnUser()) == null ? null : userMap.get(coreCouponPO.getCrcpnUser()).getCrusrName());
            }
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取我所有的优惠券分页列表成功!", pageVO), response);
        logger.info("[CoreCouponController]:end findCoreCouponPageMy");
    }

    /**
     * 后台获取优惠券分页列表<Page>
     *
     * @param mobile       用户手机号
     * @param crcpnUseStatus  使用状态（1已使用，2可使用，3已过期，4未分配）
     * @param crcpnSendStatus 发送状态（1系统发送、2手动发送）
     * @param pageNum         页码
     * @param pageSize        页数
     * @return
     */
    @ApiOperation(value = "后台获取优惠券分页列表", httpMethod = "GET", notes = "后台获取优惠券分页列表", response = CoreCouponVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreCouponPage(
            @ApiParam(value = "用户手机号", required = false) @RequestParam(value = "用户手机号", required = false) String mobile,
            @ApiParam(value = "使用状态（1已使用，2可使用，3已过期，4未分配）", required = false) @RequestParam(value = "crcpnUseStatus", required = false) Integer crcpnUseStatus,
            @ApiParam(value = "发送状态（1系统发送、2手动发送）", required = false) @RequestParam(value = "crcpnSendStatus", required = false) Integer crcpnSendStatus,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreCouponController]:begin findCoreCouponPage");
        CoreCoupon coreCoupon = new CoreCoupon();
        coreCoupon.setCrcpnUseStatus(crcpnUseStatus);
        coreCoupon.setCrcpnSendStatus(crcpnSendStatus);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<CoreCoupon> page = coreCouponService.findCoreCouponPage(coreCoupon, mobile, pageNum, pageSize);
        Page<CoreCouponVO> pageVO = new Page<CoreCouponVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());

        HashSet<String> hashUserUuids = new HashSet<String>();
        for (CoreCoupon newCoreCoupon : page.getResult()) {
            if(null != newCoreCoupon.getCrcpnUser()) {
                hashUserUuids.add(newCoreCoupon.getCrcpnUser());
            }
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<CoreCouponVO> vos = new ArrayList<CoreCouponVO>();
        CoreCouponVO vo;
        for (CoreCoupon coreCouponPO : page.getResult()) {
            vo = new CoreCouponVO();
            vo.convertPOToVO(coreCouponPO);
            if(null != coreCouponPO.getCrcpnUser()) {
                vo.setCrcpnUserMobile(userMap.get(coreCouponPO.getCrcpnUser()) == null ? null : userMap.get(coreCouponPO.getCrcpnUser()).getCrusrMobile());
                vo.setCrcpnUserName(userMap.get(coreCouponPO.getCrcpnUser()) == null ? null : userMap.get(coreCouponPO.getCrcpnUser()).getCrusrName());
            }
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreCouponController]:end findCoreCouponPage");
    }

}