package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.xiaoyu.lingdian.tool.RandomUtil;

import javax.servlet.http.HttpServletResponse;

import com.xiaoyu.lingdian.core.mybatis.page.Page;

import org.springframework.stereotype.Controller;

import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.entity.CoreFunction;
import com.xiaoyu.lingdian.service.CoreFunctionService;
import com.xiaoyu.lingdian.vo.CoreFunctionVO;

@Controller
@RequestMapping(value = "/coreFunction")
@Api(value = "coreFunction", description = "功能菜单相关操作")
public class CoreFunctionController extends BaseController {

    /**
     * 功能项表
     */
    @Autowired
    private CoreFunctionService coreFunctionService;

    /**
     * 添加
     *
     * @param crfntFunName  功能项名称
     * @param crfntResource 资源请求地址
     * @param crfntOrd      排序号
     * @param crfntFather   上级菜单
     * @return
     */
    @ApiOperation(value = "添加", httpMethod = "POST", notes = "添加")
    @RequestMapping(value = "/add/coreFunction", method = RequestMethod.POST)
    public void addCoreFunction(
            @ApiParam(value = "功能项名称", required = true) @RequestParam(value = "crfntFunName", required = true) String crfntFunName,
            @ApiParam(value = "资源请求地址", required = false) @RequestParam(value = "crfntResource", required = false) String crfntResource,
            @ApiParam(value = "排序号", required = false) @RequestParam(value = "crfntOrd", required = false) Integer crfntOrd,
            @ApiParam(value = "上级菜单", required = true) @RequestParam(value = "crfntFather", required = true) String crfntFather,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin addCoreFunction");
        CoreFunction coreFunction = new CoreFunction();
        String uuid = RandomUtil.generateString(16);
        coreFunction.setCrfntUuid(uuid);
        coreFunction.setCrfntFunName(crfntFunName);
        coreFunction.setCrfntResource(crfntResource);
        coreFunction.setCrfntStatus(1);
        coreFunction.setCrfntCdate(new Date());
        coreFunction.setCrfntUdate(new Date());
        coreFunction.setCrfntOrd(crfntOrd == null ? 1 : crfntOrd);
        coreFunction.setCrfntFather(crfntFather);
        coreFunctionService.insertCoreFunction(coreFunction);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "新增成功!"), response);
        logger.info("[CoreFunctionController]:end addCoreFunction");
    }

    /**
     * 单个功能菜单启用禁用
     *
     * @param crfntUuid
     * @param crfntStatus
     * @param response
     */
    @ApiOperation(value = "启用禁用", httpMethod = "POST", notes = "启用禁用")
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public void enable(
            @ApiParam(value = "功能项菜单标识", required = true) @RequestParam(value = "crfntUuid", required = true) String crfntUuid,
            @ApiParam(value = "状态", required = true) @RequestParam(value = "crfntStatus", required = true) Integer crfntStatus,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin enable");
        CoreFunction coreFunction = new CoreFunction();
        coreFunction.setCrfntUuid(crfntUuid);
        coreFunction.setCrfntStatus(crfntStatus);
        coreFunctionService.updateCoreFunction(coreFunction);
        if (crfntStatus == 1) { //如果是状态为启用，即父菜单也启用，子菜单也启用
            coreFunction = coreFunctionService.getCoreFunction(coreFunction);
            if (coreFunction != null && coreFunction.getCrfntFather() != null) {
                //父菜单也启用
                CoreFunction fatherFunction = new CoreFunction();
                fatherFunction.setCrfntUuid(coreFunction.getCrfntFather());
                fatherFunction.setCrfntStatus(1);
                coreFunctionService.updateCoreFunction(fatherFunction);
                //子菜单也启用
                CoreFunction childFunction = new CoreFunction();
                childFunction.setCrfntFather(coreFunction.getCrfntUuid());
                childFunction.setCrfntStatus(1);
                coreFunctionService.updateCoreFunctionByFather(childFunction);
            }
        }
        if (crfntStatus == 0) { //如果是状态为禁用，即子菜单也禁用
            coreFunction = coreFunctionService.getCoreFunction(coreFunction);
            if (coreFunction != null && coreFunction.getCrfntFather() != null) {
                CoreFunction childFunction = new CoreFunction();
                childFunction.setCrfntFather(coreFunction.getCrfntUuid());
                childFunction.setCrfntStatus(0);
                coreFunctionService.updateCoreFunctionByFather(childFunction);
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, crfntStatus == 1 ? "启用成功！" : "禁用成功！"), response);
        logger.info("[CoreFunctionController]:end enable");
    }

    /**
     * 修改
     *
     * @param crfntUuid     标识UUID
     * @param crfntFunName  功能项名称
     * @param crfntResource 资源请求地址
     * @param crfntOrd      排序号
     * @param crfntFather   上级菜单
     * @return
     */
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    @RequestMapping(value = "/update/coreFunction", method = RequestMethod.POST)
    public void updateCoreFunction(
            @ApiParam(value = "功能项菜单标识", required = true) @RequestParam(value = "crfntUuid", required = true) String crfntUuid,
            @ApiParam(value = "功能项名称", required = true) @RequestParam(value = "crfntFunName", required = true) String crfntFunName,
            @ApiParam(value = "资源请求地址", required = false) @RequestParam(value = "crfntResource", required = false) String crfntResource,
            @ApiParam(value = "排序号", required = false) @RequestParam(value = "crfntOrd", required = false) Integer crfntOrd,
            @ApiParam(value = "上级菜单", required = true) @RequestParam(value = "crfntFather", required = true) String crfntFather,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin updateCoreFunction");
        CoreFunction coreFunction = new CoreFunction();
        coreFunction.setCrfntUuid(crfntUuid);
        coreFunction.setCrfntFunName(crfntFunName);
        coreFunction.setCrfntResource(crfntResource);
        coreFunction.setCrfntUdate(new Date());
        coreFunction.setCrfntOrd(crfntOrd == null ? 1 : crfntOrd);
        coreFunction.setCrfntFather(crfntFather);
        coreFunctionService.updateCoreFunction(coreFunction);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "修改成功!"), response);
        logger.info("[CoreFunctionController]:end updateCoreFunction");
    }

    /**
     * 删除
     *
     * @param crfntUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "删除", httpMethod = "POST", notes = "删除")
    @RequestMapping(value = "/delete/one", method = RequestMethod.POST)
    public void deleteCoreFunction(
            @ApiParam(value = "功能项菜单标识", required = true) @RequestParam(value = "crfntUuid", required = true) String crfntUuid,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin deleteCoreFunction");
        CoreFunction coreFunction = new CoreFunction();
        coreFunction.setCrfntUuid(crfntUuid);
        coreFunctionService.deleteCoreFunction(coreFunction);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "删除成功!"), response);
        logger.info("[CoreFunctionController]:end deleteCoreFunction");
    }

    /**
     * 批量删除
     *
     * @param crfntUuids UUID集合
     * @return
     */
    @ApiOperation(value = "批量删除", httpMethod = "POST", notes = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public void deleteBatchCoreFunction(
            @ApiParam(value = "功能项菜单标识集合(|拼接)", required = true) @RequestParam(value = "crfntUuids", required = true) String crfntUuids,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin deleteBatchCoreFunction");
        String[] uuids = crfntUuids.split("\\|");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < uuids.length; i++) {
            list.add(uuids[i]);
        }
        if (list.size() == 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "请选择批量删除对象！"), response);
            logger.info("[CoreFunctionController]:end deleteBatchCoreFunction");
            return;
        }
        coreFunctionService.deleteBatchByIds(list);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "批量删除成功!"), response);
        logger.info("[CoreFunctionController]:end deleteBatchCoreFunction");
    }

    /**
     * 获取单个功能菜单项
     *
     * @param crfntUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个功能菜单项", httpMethod = "GET", notes = "获取单个功能菜单项", response = CoreFunctionVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreFunction(
            @ApiParam(value = "功能项菜单标识", required = true) @RequestParam(value = "crfntUuid", required = true) String crfntUuid,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin viewsCoreFunction");
        CoreFunction coreFunction = new CoreFunction();
        coreFunction.setCrfntUuid(crfntUuid);
        coreFunction = coreFunctionService.getCoreFunction(coreFunction);
        if (coreFunction == null) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, -1, "详情不存在!"), response);
            logger.info("[CoreFunctionController]:begin viewsCoreFunction");
            return;
        }

        CoreFunctionVO coreFunctionVO = new CoreFunctionVO();
        coreFunctionVO.convertPOToVO(coreFunction);
        CoreFunction fatherFunction = new CoreFunction();
        if (coreFunction.getCrfntFather() != null) {
            fatherFunction.setCrfntUuid(coreFunction.getCrfntFather());
            fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
            if (fatherFunction != null) {
                coreFunctionVO.setCrfntFatherName(fatherFunction.getCrfntFunName());
            }
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreFunctionVO), response);
        logger.info("[CoreFunctionController]:end viewsCoreFunction");
    }

    /**
     * 主菜单和一级菜单列表
     *
     * @param response
     */
    @ApiOperation(value = "主菜单和一级菜单列表", httpMethod = "GET", notes = "主菜单和一级菜单列表", response = CoreFunctionVO.class)
    @RequestMapping(value = "/top/menu", method = RequestMethod.GET)
    public void topMenuList(
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin topMenuList");
        List<CoreFunction> list = coreFunctionService.findCoreFunctionFather();
        List<CoreFunctionVO> functionVOs = new ArrayList<CoreFunctionVO>();
        CoreFunctionVO functionVO;
        CoreFunction fatherFunction;
        for (CoreFunction coreFunction : list) {
            functionVO = new CoreFunctionVO();
            functionVO.convertPOToVO(coreFunction);
            if (functionVO.getCrfntFather() != null) {
                fatherFunction = new CoreFunction();
                fatherFunction.setCrfntUuid(functionVO.getCrfntFather());
                fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
                if (fatherFunction != null) {
                    functionVO.setCrfntFatherName(fatherFunction.getCrfntFunName());
                }
            }
            functionVOs.add(functionVO);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取主菜单和一级菜单列表成功！", functionVOs), response);
        logger.info("[CoreFunctionController]:end topMenuList");
    }

    /**
     * 获取功能菜单列表<List>
     *
     * @return
     */
    @ApiOperation(value = "获取功能菜单列表", httpMethod = "GET", notes = "获取功能菜单列表", response = CoreFunctionVO.class)
    @RequestMapping(value = "/find/all", method = RequestMethod.GET)
    public void findCoreFunctionList(
            @ApiParam(value = "父菜单标识", required = false) @RequestParam(value = "crfntFather", required = false) String crfntFather,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin findCoreFunctionList");
        List<CoreFunction> lists = coreFunctionService.findCoreFunctionList(crfntFather);
        List<CoreFunctionVO> vos = new ArrayList<CoreFunctionVO>();
        CoreFunctionVO functionVO;
        CoreFunction fatherFunction;
        for (CoreFunction coreFunction : lists) {
            functionVO = new CoreFunctionVO();
            functionVO.convertPOToVO(coreFunction);
            if (functionVO.getCrfntFather() != null) {
                fatherFunction = new CoreFunction();
                fatherFunction.setCrfntUuid(functionVO.getCrfntFather());
                fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
                if (fatherFunction != null) {
                    functionVO.setCrfntFatherName(fatherFunction.getCrfntFunName());
                }
            }
            vos.add(functionVO);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "list列表获取成功!", vos), response);
        logger.info("[CoreFunctionController]:end findCoreFunctionList");
    }

    /**
     * 获取分页列表<Page>
     *
     * @param crfntFunName
     * @param pageNum      页码
     * @param pageSize     页数
     * @return
     */
    @ApiOperation(value = "获取功能菜单分页列表", httpMethod = "GET", notes = "获取功能菜单分页列表", response = CoreFunctionVO.class)
    @RequestMapping(value = "/find/by/cnd", method = RequestMethod.GET)
    public void findCoreFunctionPage(
            @ApiParam(value = "父菜单名称", required = false) @RequestParam(value = "crfntFunName", required = false) String crfntFunName,
            @ApiParam(value = "第几页", required = false) @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false) Integer pageSize,
            HttpServletResponse response) {
        logger.info("[CoreFunctionController]:begin findCoreFunctionPage");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }

        Page<CoreFunction> page = coreFunctionService.findCoreFunctionPage(crfntFunName, pageNum, pageSize);
        Page<CoreFunctionVO> pageVO = new Page<CoreFunctionVO>(page.getPageNumber(), page.getPageSize(), page.getTotalCount());
        List<CoreFunctionVO> vos = new ArrayList<CoreFunctionVO>();
        CoreFunctionVO vo;
        CoreFunction fatherFunction;
        for (CoreFunction coreFunction : page.getResult()) {
            vo = new CoreFunctionVO();
            vo.convertPOToVO(coreFunction);
            if (vo.getCrfntFather() != null) {
                fatherFunction = new CoreFunction();
                fatherFunction.setCrfntUuid(vo.getCrfntFather());
                fatherFunction = coreFunctionService.getCoreFunction(fatherFunction);
                if (fatherFunction != null) {
                    vo.setCrfntFatherName(fatherFunction.getCrfntFunName());
                }
            }
            vos.add(vo);
        }
        pageVO.setResult(vos);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "page列表获取成功!", pageVO), response);
        logger.info("[CoreFunctionController]:end findCoreFunctionPage");
    }

}