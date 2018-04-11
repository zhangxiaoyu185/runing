package com.xiaoyu.lingdian.controller;

import com.xiaoyu.lingdian.tool.out.ResultMessageBuilder;
import com.xiaoyu.lingdian.vo.CoreUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 数据统计
 */
@Controller
@RequestMapping(value = "/total")
@Api(value = "total", description = "数据统计相关操作")
public class TotalDataController extends BaseController {

    /**
     * 数据统计
     *
     * @return
     */
    @ApiOperation(value = "数据统计", httpMethod = "GET", notes = "数据统计", response = CoreUserVO.class)
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public void totalData(
            HttpServletResponse response) {
        logger.info("[TotalDataController]:begin totalData");
        Map<String, Object> map = new HashMap<String, Object>();

        writeAjaxJSONResponse(ResultMessageBuilder.build(true, 1, "获取数据统计成功", map), response);
        logger.info("[TotalDataController]:end totalData");
    }
}