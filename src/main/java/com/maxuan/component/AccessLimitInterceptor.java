package com.maxuan.component;

import com.maxuan.service.AccessLimit;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/******************************
 *
 * 码炫课堂技术交流Q群：963060292
 * 主讲：smart哥
 *
 ******************************/
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //拿到限流注解类的参数
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if(accessLimit==null){
                return true;
            }
            //时间
            int seconds = accessLimit.seconds();
            //最大次数
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            if(needLogin){
             //判断是否登录
            }

            //获取http：//ip:8080/url
            String ip=request.getRemoteAddr();
            String key=ip+":"+request.getServletPath();

            Integer count = (Integer)redisUtil.get(key);
            //首次进入
            if(count==null||-1==count){
                redisUtil.set(key,1);
                //设置过期时间
                redisUtil.expire(key,seconds);
                return true;
            }

            //如果访问次数<最大次数，则做加1操作
            if(count<maxCount){
                redisUtil.incr(key,1);
                return true;
            }

            //此时访问次数大于等于最大次数
            if(count>=maxCount){
                System.out.println("count========"+count);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("请求过于频繁，请稍后再试！");
                return false;
            }
        }
        return true;
    }
}
