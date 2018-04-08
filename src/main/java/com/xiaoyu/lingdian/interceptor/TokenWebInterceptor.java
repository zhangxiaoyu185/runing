package com.xiaoyu.lingdian.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 防止重复提交和安全校验拦截器
 *
 * @author: zhangyu
 * @since: 2017年6月29日
 * @history:
 */
public class TokenWebInterceptor extends HandlerInterceptorAdapter {

    /**日志*/
    private static Logger logger                  = LoggerFactory.getLogger(TokenWebInterceptor.class);

    /**
     * token校验失败提示
     */
    private static final String EXCEPTION_NO_CHECKED    = "请求已过期,请重新提交";
    /**
     * 用户没登陆
     */
    private static final String EXCEPTION_NO_LOGIN    = "请先登录";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求是否请求后台
        if (handler instanceof HandlerMethod) {
            if (!checkUrlAndParams(request, response)) {
                return false;
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 判断是否是同一个请求重复提交
     *
     * @param request 请求
     * @return true：存在，false:不存在
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private boolean checkUrlAndParams(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer sbr = new StringBuffer();
        sbr.append(request.getRequestURI());
        Enumeration enu = request.getParameterNames();  
        while(enu.hasMoreElements()){
            String paraName = (String)enu.nextElement();
            if ("articleId".equalsIgnoreCase(paraName) || "beAttentionedId".equalsIgnoreCase(paraName)) {
                sbr.append(paraName).append(request.getParameter(paraName));
            }
        }

        return true;
    }

}
