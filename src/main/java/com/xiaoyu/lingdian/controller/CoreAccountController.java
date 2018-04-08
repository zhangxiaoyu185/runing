package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.xiaoyu.lingdian.core.cache.model.RedisKeyAndValue;
import com.xiaoyu.lingdian.core.cache.service.RedisCacheService;
import com.xiaoyu.lingdian.core.mybatis.page.Page;
import com.xiaoyu.lingdian.entity.CoreAccount;
import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.entity.CoreRole;
import com.xiaoyu.lingdian.entity.weixin.Constant;
import com.xiaoyu.lingdian.service.CoreAccountService;
import com.xiaoyu.lingdian.service.CoreFunctionService;
import com.xiaoyu.lingdian.service.CoreRoleService;
import com.xiaoyu.lingdian.tool.RandomUtil;
import com.xiaoyu.lingdian.tool.StringUtil;
import com.xiaoyu.lingdian.tool.VerifyCodeUtils;
import com.xiaoyu.lingdian.tool.encrypt.MD5Util;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreAccountVO;

@Controller
@RequestMapping(value = "/coreAccount")
@Api(value = "coreAccount", description = "后台账户相关操作")
public class CoreAccountController extends BaseController {

    /**
     * 账户表
     */
    @Autowired
    private CoreAccountService coreAccountService;

    /**
     * 角色表
     */
    @Autowired
    private CoreRoleService coreRoleService;

    /**
     * 功能菜单表
     */
    @Autowired
    private CoreFunctionService coreFunctionService;

    /**
     * redis缓存
     */
    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 图片验证码宽
     */
    private int VERIFY_WIDTH = 80;
    /**
     * 图片验证码高
     */
    private int VERIFY_HEIGHT = 30;

    /**
     * 获取图片验证码
     *
     * @return
     */
    @ApiOperation(value = "获取图片验证码", httpMethod = "GET", notes = "获取图片验证码")
    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.GET)
    public ModelAndView getVerifyCode(
            HttpServletRequest request,
            HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //生成验证码放入缓存
            redisCacheService.add(new RedisKeyAndValue("VERIFYCODE:" + request.getRequestedSessionId(), verifyCode, 300l));

            VerifyCodeUtils.outputImage(VERIFY_WIDTH, VERIFY_HEIGHT, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验图片验证码
     *
     * @param verifyCode
     * @return
     */
    @ApiOperation(value = "校验图片验证码", httpMethod = "POST", notes = "校验图片验证码")
    @RequestMapping(value = "/checkVerifyCode", method = RequestMethod.POST)
    public void checkVerifyCode(
            @ApiParam(value = "验证码", required = true) @RequestParam(value = "verifyCode", required = true) String verifyCode,
            HttpServletRequest request, HttpServletResponse response) {
        //校验图片验证码
        RedisKeyAndValue redisKeyAndValue = redisCacheService.get("VERIFYCODE:" + request.getRequestedSessionId());
        if (redisKeyAndValue == null || StringUtils.isBlank(redisKeyAndValue.getValue())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "验证码已失效"), response);
            logger.info("[CoreAccountController]:end checkVerifyCode");
            return;
        }
        if (!redisKeyAndValue.getValue().toLowerCase().equalsIgnoreCase(verifyCode.toLowerCase())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "验证码输入错误", redisKeyAndValue.getValue()), response);
            logger.info("[CoreAccountController]:end checkVerifyCode");
            return;
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "验证码正确"), response);
        logger.info("[CoreAccountController]:end checkVerifyCode");
    }

    /**
     * 登录
     *
     * @param userName 用户名
     * @param pwd      密码
     * @param response
     */
    @ApiOperation(value = "登录", httpMethod = "POST", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(
            @ApiParam(value = "用户名", required = true) @RequestParam(value = "userName", required = true) String userName,
            @ApiParam(value = "密码", required = true) @RequestParam(value = "pwd", required = true) String pwd,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin login");
        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractName(userName);
        CoreAccount account = coreAccountService.getCoreAccountByName(coreAccount);
        if (null == account || !(MD5Util.encode(pwd, Constant.DEFAULT_CHARSET)).equalsIgnoreCase(account.getCractPassword())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "用户或密码错误！"), response);
            logger.info("[CoreAccountController]:end login");
            return;
        }

        CoreAccountVO coreAccountVO = new CoreAccountVO();
        coreAccountVO.convertPOToVO(account);
        List<CoreFunction> functionList = new ArrayList<CoreFunction>();
        // 返回菜单
        if (account.getCractRoles() != null) {
            String[] roles = account.getCractRoles().split("\\|");
            CoreRole coreRole;
            CoreFunction coreFunction;
            for (int i = 0; i < roles.length; i++) { // 获取角色
                // 根据角色查询功能菜单
                coreRole = new CoreRole();
                coreRole.setCrrolUuid((roles[i]));
                CoreRole role = coreRoleService.getCoreRole(coreRole);
                if (role != null && role.getCrrolFuns() != null) {
                    String[] menus = role.getCrrolFuns().split("\\|");
                    for (int y = 0; y < menus.length; y++) { // 获取菜单权限
                        coreFunction = new CoreFunction();
                        coreFunction.setCrfntUuid(menus[y]);
                        CoreFunction function = coreFunctionService.getCoreFunction(coreFunction);
                        if (null != function && function.getCrfntStatus() == 1 && Collections.frequency(functionList, function) < 1) {
                            //去重
                            functionList.add(function);
                        }
                    }
                }
            }
        }
        coreAccountVO.setCoreFunctions(functionList);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "登录成功!", coreAccountVO), response);
        logger.info("[CoreAccountController]:end login");
    }

    /**
     * 添加
     *
     * @param cractName     帐户名称
     * @param cractPassword 登录密码
     * @param cractRoles    角色集合
     * @param cractTel      联系方式
     * @param cractEmail    邮箱
     * @param cractRemarks  备注
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/coreAccount", method = RequestMethod.POST)
    public void addCoreAccount(
            @ApiParam(value = "帐户名称", required = true) @RequestParam(value = "cractName", required = true) String cractName,
            @ApiParam(value = "登录密码", required = true) @RequestParam(value = "cractPassword", required = true) String cractPassword,
            @ApiParam(value = "角色集合(|拼接)", required = true) @RequestParam(value = "cractRoles", required = true) String cractRoles,
            @ApiParam(value = "联系方式", required = false) @RequestParam(value = "cractTel", required = false) String cractTel,
            @ApiParam(value = "邮箱", required = false) @RequestParam(value = "cractEmail", required = false) String cractEmail,
            @ApiParam(value = "备注", required = false) @RequestParam(value = "cractRemarks", required = false) String cractRemarks,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin addCoreAccount");
        if (!StringUtil.isEmpty(cractTel)) {
            if (!StringUtil.isPhone(cractTel)) {
                if (!StringUtil.isMobile(cractTel)) {
                    writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "联系方式格式不对!"), response);
                    logger.info("[CoreAccountController]:end addCoreAccount");
                    return;
                }
            }
        }
        if (!StringUtil.isEmpty(cractEmail)) {
            if (!StringUtil.isEmail(cractEmail)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "邮箱格式不对!"), response);
                logger.info("[CoreAccountController]:end addCoreAccount");
                return;
            }
        }

        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractName(cractName);
        CoreAccount account = coreAccountService.getCoreAccountByName(coreAccount);
        if (account != null) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "此账户已存在!"), response);
            logger.info("[CoreAccountController]:end addCoreAccount");
            return;
        }

        String uuid = RandomUtil.generateString(16);
        coreAccount.setCractUuid(uuid);
        String md5Pwd = MD5Util.encode(cractPassword, Constant.DEFAULT_CHARSET);
        coreAccount.setCractPassword(md5Pwd);
        coreAccount.setCractStatus(1);
        coreAccount.setCractRoles(cractRoles);
        coreAccount.setCractCdate(new Date());
        coreAccount.setCractUdate(new Date());
        coreAccount.setCractTel(cractTel);
        coreAccount.setCractEmail(cractEmail);
        coreAccount.setCractRemarks(cractRemarks);

        coreAccountService.insertCoreAccount(coreAccount);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreAccountController]:end addCoreAccount");
    }

    /**
     * 重置密码,重置为123456
     *
     * @param cractUuid
     * @param response
     */
    @ApiOperation(value = "重置密码,重置为123456", httpMethod = "POST", notes = "重置密码,重置为123456")
    @RequestMapping(value = "/reset/pwd", method = RequestMethod.POST)
    public void resetPwd(
            @ApiParam(value = "帐户标识UUID", required = true) @RequestParam(value = "cractUuid", required = true) String cractUuid,
            HttpServletResponse response) {
        logger.info("[CoreAccountController.resetPwd]:begin resetPwd.");
        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractUuid(cractUuid);
        String newMd5PWD = MD5Util.encode("123456", Constant.DEFAULT_CHARSET);
        coreAccount.setCractPassword(newMd5PWD);
        coreAccount.setCractUdate(new Date());
        coreAccountService.updateCoreAccount(coreAccount);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "重置密码成功！"), response);
        logger.info("[CoreAccountController.resetPwd]:end resetPwd.");
    }

    /**
     * 修改密码
     *
     * @param cractUuid
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @param response
     */
    @ApiOperation(value = "修改密码", httpMethod = "POST", notes = "修改密码")
    @RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
    public void updatePwd(
            @ApiParam(value = "帐户标识UUID", required = true) @RequestParam(value = "cractUuid", required = true) String cractUuid,
            @ApiParam(value = "旧密码", required = true) @RequestParam(value = "oldPwd", required = true) String oldPwd,
            @ApiParam(value = "新密码", required = true) @RequestParam(value = "newPwd", required = true) String newPwd,
            @ApiParam(value = "确认密码", required = true) @RequestParam(value = "confirmPwd", required = true) String confirmPwd,
            HttpServletResponse response) {
        logger.info("[CoreAccountController.updatePwd]:begin updatePwd.");
        if (!newPwd.equals(confirmPwd)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "密码输入不一致！"), response);
            logger.info("[CoreAccountController]:end updatePwd");
            return;
        }

        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractUuid(cractUuid);
        coreAccount = coreAccountService.getCoreAccount(coreAccount);
        String oldMd5PWD = MD5Util.encode(oldPwd, Constant.DEFAULT_CHARSET);
        if (coreAccount == null || !oldMd5PWD.equalsIgnoreCase(coreAccount.getCractPassword())) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "原密码错误！"), response);
            logger.info("[CoreAccountController]:end updatePwd");
            return;
        }
        String newMd5PWD = MD5Util.encode(newPwd, Constant.DEFAULT_CHARSET);
        coreAccount.setCractPassword(newMd5PWD);
        coreAccount.setCractUdate(new Date());
        coreAccountService.updateCoreAccount(coreAccount);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改密码成功！"), response);
        logger.info("[CoreAccountController.updatePwd]:end updatePwd.");
    }

    /**
     * 修改
     *
     * @param cractUuid    标识UUID
     * @param cractRoles   角色集合
     * @param cractTel     联系方式
     * @param cractEmail   邮箱
     * @param cractRemarks 备注
     * @return
     */
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    @RequestMapping(value = "/update/coreAccount", method = RequestMethod.POST)
    public void updateCoreAccount(
            @ApiParam(value = "后台账户标识", required = true) @RequestParam(value = "cractUuid", required = true) String cractUuid,
            @ApiParam(value = "角色集合(|拼接)", required = true) @RequestParam(value = "cractRoles", required = true) String cractRoles,
            @ApiParam(value = "联系方式", required = false) @RequestParam(value = "cractTel", required = false) String cractTel,
            @ApiParam(value = "邮箱", required = false) @RequestParam(value = "cractEmail", required = false) String cractEmail,
            @ApiParam(value = "备注", required = false) @RequestParam(value = "cractRemarks", required = false) String cractRemarks,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin updateCoreAccount");
        if (!StringUtil.isEmpty(cractTel)) {
            if (!StringUtil.isPhone(cractTel)) {
                if (!StringUtil.isMobile(cractTel)) {
                    writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "联系方式格式不对!"), response);
                    logger.info("[CoreAccountController]:end addCoreAccount");
                    return;
                }
            }
        }
        if (!StringUtil.isEmpty(cractEmail)) {
            if (!StringUtil.isEmail(cractEmail)) {
                writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "邮箱格式不对!"), response);
                logger.info("[CoreAccountController]:end addCoreAccount");
                return;
            }
        }

        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractUuid(cractUuid);
        coreAccount.setCractRoles(cractRoles);
        coreAccount.setCractUdate(new Date());
        coreAccount.setCractTel(cractTel);
        coreAccount.setCractEmail(cractEmail);
        coreAccount.setCractRemarks(cractRemarks);
        coreAccountService.updateCoreAccount(coreAccount);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreAccountController]:end updateCoreAccount");
    }

    /**
     * 删除
     *
     * @param cractUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreAccount(
            @ApiParam(value = "后台账户标识", required = true) @RequestParam(value = "cractUuid", required = true) String cractUuid,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin deleteCoreAccount");
        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractUuid(cractUuid);
        coreAccount.setCractStatus(0);
        coreAccountService.updateCoreAccount(coreAccount);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreAccountController]:end deleteCoreAccount");
    }

    /**
     * 批量删除
     *
     * @param cractUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreAccount(
            @ApiParam(value = "后台账户标识集合(|拼接)", required = true) @RequestParam(value = "cractUuids", required = true) String cractUuids,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin deleteBatchCoreAccount");

        if (StringUtil.isEmpty(cractUuids)) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "[UUID集合]不能为空!"), response);
            logger.info("[CoreAccountController]:end deleteBatchCoreAccount");
            return;
        }

        String[] uuids = cractUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            logger.info("[CoreAccountController]:end deleteBatchCoreAccount");
            return;
        }
        for (String strUuid : list) {
            CoreAccount coreAccount = new CoreAccount();
            coreAccount.setCractUuid(strUuid);
            coreAccount.setCractStatus(0);
            coreAccountService.updateCoreAccount(coreAccount);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreAccountController]:end deleteBatchCoreAccount");
    }

    /**
     * 获取单个后台账户
     *
     * @param cractUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个后台账户", httpMethod = "GET", notes = "获取单个后台账户", response = CoreAccountVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreAccount(
            @ApiParam(value = "后台账户标识", required = true) @RequestParam(value = "cractUuid", required = true) String cractUuid,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin viewsCoreAccount");
        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setCractUuid(cractUuid);
        coreAccount = coreAccountService.getCoreAccount(coreAccount);
        if (null == coreAccount) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "账户不存在!"), response);
            logger.info("[CoreAccountController]:end viewsCoreAccount");
            return;
        }

        CoreAccountVO coreAccountVO = new CoreAccountVO();
        coreAccountVO.convertPOToVO(coreAccount);
        String role = coreAccount.getCractRoles();
        String[] roles = role.split("\\|");
        List<String> uuids = new ArrayList<String>();
        Collections.addAll(uuids, roles);
        List<CoreRole> coreRoles = coreRoleService.findCoreRoleByUuids(uuids);
        if (coreRoles.size() > 0) {
            StringBuffer rolesName = new StringBuffer();
            for (CoreRole coreRole : coreRoles) {
                rolesName.append(coreRole.getCrrolName() + "|");
            }
            coreAccountVO.setCractRolesName(rolesName.toString());
        } else {
            coreAccountVO.setCractRolesName("");
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreAccountVO), response);
        logger.info("[CoreAccountController]:end viewsCoreAccount");
    }

    /**
     * 获取后台账户分页列表<Page>
     *
     * @param cractName 帐户名称
     * @param pageNum   页码
     * @param pageSize  页数
     * @return
     */
    @ApiOperation(value = "获取后台账户分页列表", httpMethod = "GET", notes = "获取后台账户分页列表", response = CoreAccountVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreAccountPage(
            @ApiParam(value = "帐户名称", required = false) @RequestParam(value = "cractName", required = false) String cractName,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreAccountController]:begin findCoreAccountPage");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        Page<CoreAccount> page = coreAccountService.findCoreAccountPage(cractName, pageNum, pageSize);
        Page<CoreAccountVO> pageVO = new Page<CoreAccountVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreAccountVO> vos = new ArrayList<CoreAccountVO>();
        CoreAccountVO vo;
        for (CoreAccount coreAccount : page.getResult()) {
            vo = new CoreAccountVO();
            vo.convertPOToVO(coreAccount);
            String role = coreAccount.getCractRoles();
            String[] roles = role.split("\\|");
            List<String> uuids = new ArrayList<String>();
            Collections.addAll(uuids, roles);
            List<CoreRole> coreRoles = coreRoleService.findCoreRoleByUuids(uuids);
            if (coreRoles.size() > 0) {
                StringBuffer rolesName = new StringBuffer();
                for (CoreRole coreRole : coreRoles) {
                    rolesName.append(coreRole.getCrrolName() + "|");
                }
                vo.setCractRolesName(rolesName.toString());
            } else {
                vo.setCractRolesName("");
            }

            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreAccountController]:end findCoreAccountPage");
    }

}