package com.yan.daserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.daserver.common.CommonReq;
import com.yan.daserver.common.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("uri="+request.getRequestURI());
        try {
            String body = (new RequestWrapper(request)).getBody();
            Context context = (new ObjectMapper()).readValue(body, CommonReq.class).getContext();
            String key = context.getRole() + context.getAccountId();
            if (context.getToken().equals(redisTemplate.opsForValue().get(key))) {
                return true;
            } else {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("token失效");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理完毕后，可以对响应结果进行处理
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完全处理完毕后的清理工作
    }
}
