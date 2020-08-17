package com.wuliang.ncov.core.config;

import cn.hutool.core.util.StrUtil;
import com.wuliang.ncov.core.redis.RedisDaoImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Resource
    private RedisDaoImpl redisDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        if (!StrUtil.contains(url, "error") && !StrUtil.contains(url, ".js")
                && !StrUtil.contains(url, ".css") && !StrUtil.contains(url, "swagger")
                && !StrUtil.contains(url, "webjars") && !StrUtil.contains(url, "favicon")
        ) {
            redisDao.hmSetIncrement(url, url, Long.valueOf(1));
            redisDao.hmSetIncrement("total", "total", Long.valueOf(1));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) throws Exception {
    }
}
