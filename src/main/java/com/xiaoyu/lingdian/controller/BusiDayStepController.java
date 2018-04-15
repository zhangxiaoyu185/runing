package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.constant.BaseConstant;
import com.xiaoyu.lingdian.entity.CoreUser;
import com.xiaoyu.lingdian.entity.CoreWechat;
import com.xiaoyu.lingdian.service.CoreUserService;
import com.xiaoyu.lingdian.service.CoreWechatService;
import com.xiaoyu.lingdian.tool.DateUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.encrypt.SecretUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.text.SimpleDateFormat;
import java.util.*;

import com.xiaoyu.lingdian.service.BusiDayStepService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.BusiDayStep;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.BusiDayStepVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 日步数表
 */
@Controller
@RequestMapping(value = "/busiDayStep")
@Api(value = "busiDayStep", description = "日步数相关操作")
public class BusiDayStepController extends BaseController {

    /**
     * 日步数表
     */
    @Autowired
    private BusiDayStepService busiDayStepService;

    /**
     * 用户表
     */
    @Autowired
    private CoreUserService coreUserService;

    /**
     * 小程序表
     */
    @Autowired
    private CoreWechatService coreWechatService;

    /**
     * 获取微信运动数据
     * @param encryptedData
     * @param iv
     * @param sessionKey
     * @param userUuid
     * @return
     */
    @RequestMapping(value ="/getWeRunData",method = RequestMethod.POST)
    public void getWeRunData(
            String encryptedData,
            String iv,
            String sessionKey,
            String userUuid,
            HttpServletResponse response) {
        if(StringUtil.isEmpty(encryptedData) || StringUtil.isEmpty(iv) || StringUtil.isEmpty(sessionKey) || StringUtil.isEmpty(userUuid)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "同步失败!"), response);
            logger.info("[BusiDayStepController]:end getWeRunData");
            return;
        }
        CoreWechat coreWechat = new CoreWechat();
        coreWechat.setCrwctUuid(BaseConstant.WE_CHAT_UUID);
        coreWechat = coreWechatService.getCoreWechat(coreWechat);
        if (null == coreWechat) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "小程序账号不存在!"), response);
            logger.info("[BusiDayStepController]:end getWeRunData");
            return;
        }
        try {
            String result = SecretUtils.AES128CBCdecrypt(encryptedData, iv, coreWechat.getCrwctAppid(), sessionKey);
            if(!StringUtil.isEmpty(result)){
                JSONObject obj = JSONObject.fromObject(result);
                JSONArray stepInfoList = obj.getJSONArray("stepInfoList");
                if(null != stepInfoList && stepInfoList.size() > 0) {
                    String nowday = DateUtil.getToday(DateUtil.DEFAULT_PATTERN);
                    String yesterday = DateUtil.getYesterday(DateUtil.DEFAULT_PATTERN);
                    for(int i=0; i<stepInfoList.size(); i++){
                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        JSONObject job = stepInfoList.getJSONObject(i);
                        long millis = job.getLong("timestamp");
                        int step = job.getInt("step");
                        String valueday = new SimpleDateFormat(DateUtil.DEFAULT_PATTERN, Locale.CHINA).format(new Date(millis*1000));
                        if(nowday.equals(valueday) || yesterday.equals(valueday)) {
                            //判断数据库数据是否存在
                            BusiDayStep busiDayStep = busiDayStepService.getBusiDayStepByDayAndUser(userUuid, valueday);
                            if(null == busiDayStep) {
                                //添加
                                busiDayStep = new BusiDayStep();
                                busiDayStep.setBsdspCdate(new Date());
                                busiDayStep.setBsdspStep(step);
                                busiDayStep.setBsdspDay(valueday);
                                busiDayStep.setBsdspUser(userUuid);
                                busiDayStep.setBsdspUuid(RandomUtil.generateString(16));
                                busiDayStepService.insertBusiDayStep(busiDayStep);
                            } else {
                                //修改
                                busiDayStep.setBsdspStep(step);
                                busiDayStep.setBsdspCdate(new Date());
                                busiDayStepService.updateBusiDayStep(busiDayStep);
                            }
                        }
                    }
                }
            }
            logger.info(result);
        } catch (Exception e) {
            logger.info(userUuid + "同步失败");
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "同步失败!"), response);
            logger.info("[BusiDayStepController]:end getWeRunData");
            return;
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "同步成功"), response);
        logger.info("[BusiDayStepController]:end getWeRunData");
    }

    /**
     * 日步数排行榜<List>
     *
     * @param bsdspDay   上一次的日期(yyyy-MM-dd)
     * @return
     */
    @ApiOperation(value = "日步数排行榜", httpMethod = "POST", notes = "日步数排行榜", response = BusiDayStepVO.class)
    @RequestMapping(value = "/find/day/chat", method = RequestMethod.POST)
    public void findBusiDayStepForDayChat(
            @ApiParam(value = "上一次的日期(yyyy-MM-dd)", required = false) @RequestParam(value = "bsdspDay", required = false) String bsdspDay,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin findBusiDayStepForDayChat");
        if(StringUtil.isEmpty(bsdspDay)) { //日期不传就查询最新
            Date date = DateUtil.addDay(new Date(), 1);
            bsdspDay = DateUtil.formatDate(DateUtil.DEFAULT_PATTERN, date);
        }
        BusiDayStep oldbusiDayStep = busiDayStepService.getBusiDayStepByOrd(bsdspDay);
        if(null == oldbusiDayStep || StringUtil.isEmpty(oldbusiDayStep.getBsdspDay())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiDayStepController]:end findBusiDayStepForDayChat");
            return;
        }
        List<BusiDayStep> lists = busiDayStepService.findBusiDayStepForDayChat(oldbusiDayStep.getBsdspDay());
        if(CollectionUtils.isEmpty(lists)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, 2, "已查询全部!"), response);
            logger.info("[BusiDayStepController]:end findBusiDayStepForDayChat");
            return;
        }
        HashSet<String> hashUserUuids = new HashSet<String>();
        for (BusiDayStep busiDayStepPO : lists) {
            hashUserUuids.add(busiDayStepPO.getBsdspUser());
        }
        List<String> userUuids = new ArrayList<>(hashUserUuids);
        Map<String, CoreUser> userMap = coreUserService.findCoreUserMapByUuidList(userUuids);

        List<BusiDayStepVO> vos = new ArrayList<BusiDayStepVO>();
        BusiDayStepVO vo;
        for (BusiDayStep busiDayStep : lists) {
            vo = new BusiDayStepVO();
            vo.convertPOToVO(busiDayStep);
            vo.setBsdspUserName(userMap.get(busiDayStep.getBsdspUser())==null?null:userMap.get(busiDayStep.getBsdspUser()).getCrusrName());
            vo.setBsdspUserHead(userMap.get(busiDayStep.getBsdspUser())==null?null:userMap.get(busiDayStep.getBsdspUser()).getCrusrWxHeadimgurl());
            vos.add(vo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("bsdspDay", oldbusiDayStep.getBsdspDay());
        map.put("list", vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "日步数排行榜获取成功!", map), response);
        logger.info("[BusiDayStepController]:end findBusiDayStepForDayChat");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param name  用户昵称
     * @param bsdspDay   所属日期
     * @param pageNum    页码
     * @param pageSize   页数
     * @return
     */
    @ApiOperation(value = "获取日步数分页列表", httpMethod = "POST", notes = "获取日步数分页列表", response = BusiDayStepVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.POST)
    public void findBusiDayStepPage(
            @ApiParam(value = "用户昵称", required = false) @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "所属日期", required = false) @RequestParam(value = "bsdspDay", required = false) String bsdspDay,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[BusiDayStepController]:begin findBusiDayStepPage");
        BusiDayStep busiDayStep = new BusiDayStep();
        busiDayStep.setBsdspDay(bsdspDay);
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        Page<BusiDayStep> page = busiDayStepService.findBusiDayStepPage(busiDayStep, name, pageNum, pageSize);
        Page<BusiDayStepVO> pageVO = new Page<BusiDayStepVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<BusiDayStepVO> vos = new ArrayList<BusiDayStepVO>();
        BusiDayStepVO vo;
        for (BusiDayStep busiDayStepPO : page.getResult()) {
            vo = new BusiDayStepVO();
            vo.convertPOToVO(busiDayStepPO);
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[BusiDayStepController]:end findBusiDayStepPage");
    }

}