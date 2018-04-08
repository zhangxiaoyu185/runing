package com.xiaoyu.lingdian.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
@Api(value = "/", description = "后台首页跳转")
public class IndexController extends BaseController {

    /**
     * 跳转到admin登录页
     **/
    @ResponseBody
    @RequestMapping(value = "admin", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到admin登录页", httpMethod = "GET", notes = "跳转到admin登录页")
    public ModelAndView admin(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("login");
        return model;
    }
}