package com.ihrm.common.interceptor;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * 继承HandlerInterceptorAdapter
 * preHandle:进入到控制器方法之前执行的内容
 * boolean:
 * true:可以继续执行控制器方法
 * false:拦截
 * postHandle:执行控制器方法之后执行的内容
 * afterCompletion:响应结束之前执行的内容
 * <p>
 * 1.简化获取token数据的代码编写
 * 统一的用户权限校验（是否登录）
 * 2.判断用户是否具有当前访问接口的权限
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 简化获取token数据的代码编写
     * 1.通过request获取到请求token信息
     * 2.从token中解析获取claims
     * 3.将claims绑定到request域中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.通过request获取到请求token信息
        String authorization = request.getHeader("Authorization");
        //判断请求头是否为空，或者是否以Bearer开头
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            //2.获取token数据
            String token = authorization.replace("Bearer ", "");
            //3.解析token获取claims
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null) {
                //通过claims获取到当前用户的可访问api权限字符串
                String apis = (String) claims.get("apis");
                //通过handler
                HandlerMethod h = (HandlerMethod) handler;
                //获取接口上的DeleteMapping注解
                DeleteMapping methodAnnotation = h.getMethodAnnotation(DeleteMapping.class);
                //获取当前请求接口中的name属性
                String name = methodAnnotation.name();
                //判断当前用户是否具有相应的请求权限
                if (apis.contains(name)) {
                    request.setAttribute("user_claims", claims);
                    return true;
                } else {
                    throw new CommonException(ResultCode.UNAUTHORISE);
                }
            }
        }
        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }

}
