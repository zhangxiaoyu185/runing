package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xiaoyu.lingdian.controller.BaseController;
import com.xiaoyu.lingdian.entity.CoreRegion;
import com.xiaoyu.lingdian.service.CoreRegionService;
import com.xiaoyu.lingdian.vo.CoreRegionVO;

@Controller
@RequestMapping(value = "/coreRegion")
@Api(value = "coreRegion", description = "区域相关操作")
public class CoreRegionController extends BaseController {

    /**
     * 区域表
     */
    @Autowired
    private CoreRegionService coreRegionService;

    /**
     * 获取单个区域
     *
     * @param crrgnUuid 标识UUID
     * @return
     */
    @ApiOperation(value = "获取单个区域", httpMethod = "GET", notes = "获取单个区域", response = CoreRegionVO.class)
    @RequestMapping(value = "/views", method = RequestMethod.GET)
    public void viewsCoreRegion(
            @ApiParam(value = "区域标识UUID", required = true) @RequestParam(value = "crrgnUuid", required = true) String crrgnUuid,
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin viewsCoreRegion");
        CoreRegion coreRegion = new CoreRegion();
        coreRegion.setCrrgnUuid(crrgnUuid);
        coreRegion = coreRegionService.getCoreRegion(coreRegion);

        CoreRegionVO coreRegionVO = new CoreRegionVO();
        coreRegionVO.convertPOToVO(coreRegion);

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取单个信息成功", coreRegionVO), response);
        logger.info("[CoreRegionController]:end viewsCoreRegion");
    }

    /**
     * 获取国家
     *
     * @param response
     */
    @ApiOperation(value = "获取所有国家", httpMethod = "GET", notes = "获取所有国家", response = CoreRegionVO.class)
    @RequestMapping(value = "/country/list", method = RequestMethod.GET)
    public void listCountry(
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listCountry");
        List<CoreRegion> areas = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 1, null);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取国家成功", areas), response);
        logger.info("[CoreRegionController]:end listCountry");
    }

    /**
     * 获取省份
     *
     * @param countryId 中国传‘1’
     * @param response
     */
    @ApiOperation(value = "获取国家下所有省份", httpMethod = "GET", notes = "获取国家下所有省份", response = CoreRegionVO.class)
    @RequestMapping(value = "/province/list/{countryId}", method = RequestMethod.GET)
    public void listProvinces(
            @ApiParam(value = "国家标识UUID", required = true) @PathVariable("countryId") String countryId,
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listProvinces");
        List<CoreRegion> areas = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 2, countryId);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取省份成功", areas), response);
        logger.info("[CoreRegionController]:end listProvinces");
    }

    /**
     * 获取城市
     *
     * @param provinceId
     * @param response
     */
    @ApiOperation(value = "获取省份下所有城市", httpMethod = "GET", notes = "获取省份下所有城市", response = CoreRegionVO.class)
    @RequestMapping(value = "/city/list/{provinceId}", method = RequestMethod.GET)
    public void listCities(
            @ApiParam(value = "省份标识UUID", required = true) @PathVariable("provinceId") String provinceId,
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listCities");
        List<CoreRegion> areas = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 3, provinceId);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取城市成功", areas), response);
        logger.info("[CoreRegionController]:end listCities");
    }

    /**
     * 获取全部城市
     *
     * @param response
     */
    @ApiOperation(value = "获取全部城市", httpMethod = "GET", notes = "获取全部城市", response = CoreRegionVO.class)
    @RequestMapping(value = "/city/listAllCities", method = RequestMethod.GET)
    public void listAllCities(
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listAllCities");
        List<CoreRegion> areas = this.coreRegionService.findCoreRegionByType(3);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取全部城市成功", areas), response);
        logger.info("[CoreRegionController]:end listAllCities");
    }

    /**
     * 获取区县
     *
     * @param cityId
     * @param response
     */
    @ApiOperation(value = "获取城市下的所有区县", httpMethod = "GET", notes = "获取城市下的所有区县", response = CoreRegionVO.class)
    @RequestMapping(value = "/district/list/{cityId}", method = RequestMethod.GET)
    public void listCounties(
            @ApiParam(value = "城市标识UUID", required = true) @PathVariable("cityId") String cityId,
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listCounties");
        List<CoreRegion> areas = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 4, cityId);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取县/县级市/区成功", areas), response);
        logger.info("[CoreRegionController]:end listCounties");
    }

    /**
     * 获取全部区县
     *
     * @param response
     */
    @ApiOperation(value = "获取全部区县", httpMethod = "GET", notes = "获取全部区县", response = CoreRegionVO.class)
    @RequestMapping(value = "/district/listAllCounties", method = RequestMethod.GET)
    public void listAllCounties(
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listAllCounties");
        List<CoreRegion> areas = this.coreRegionService.findCoreRegionByType(4);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取全部县/县级市/区成功", areas), response);
        logger.info("[CoreRegionController]:end listAllCounties");
    }

    /**
     * 获取省市组合
     *
     * @param response
     */
    @ApiOperation(value = "获取省市组合", httpMethod = "GET", notes = "获取省市组合", response = CoreRegionVO.class)
    @RequestMapping(value = "/province/and/city", method = RequestMethod.GET)
    public void listProvinceAndCity(
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listProvinceAndCity");
        Map<String, List<CoreRegion>> map = new HashMap<String, List<CoreRegion>>();
        List<CoreRegion> provinces = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 2, "1");
        for (CoreRegion coreRegion : provinces) {
            List<CoreRegion> areas = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 3, coreRegion.getCrrgnUuid());
            map.put(coreRegion.getCrrgnUuid(), areas);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取省市组合成功", map), response);
        logger.info("[CoreRegionController]:end listProvinceAndCity");
    }

    /**
     * 获取城市区县组合
     *
     * @param response
     */
    @ApiOperation(value = "获取城市区县组合", httpMethod = "GET", notes = "获取城市区县组合", response = CoreRegionVO.class)
    @RequestMapping(value = "/city/and/district", method = RequestMethod.GET)
    public void listCituAndDistrict(
            HttpServletResponse response) {
        logger.info("[CoreRegionController]:begin listCituAndDistrict");
        Map<String, List<CoreRegion>> map = new HashMap<String, List<CoreRegion>>();
        List<CoreRegion> citys = this.coreRegionService.findCoreRegionByType(3);
        for (CoreRegion coreRegion : citys) {
            List<CoreRegion> areas = this.coreRegionService.findCoreRegionByParentAndTypeAndName(null, 4, coreRegion.getCrrgnUuid());
            map.put(coreRegion.getCrrgnUuid(), areas);
        }

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取城市区县组合", map), response);
        logger.info("[CoreRegionController]:end listCituAndDistrict");
    }

}