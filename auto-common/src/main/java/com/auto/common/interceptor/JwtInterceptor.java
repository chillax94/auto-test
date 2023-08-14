package com.auto.common.interceptor;

import com.auto.common.utils.JwtUtils;
import com.auto.common.entity.ResultCode;
import com.auto.common.exception.CommonException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        //判断请求头信息是否为空，或者是否已Bearer开头
        if(!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            String token = authorization.replace("Bearer ","");
            //解析token获取claims
            Claims claims = jwtUtils.parseJwt(token);
            if(claims != null) {
                //通过claims获取到当前用户的可访问API权限字符串
                String apis = (String) claims.get("apis");
                HandlerMethod h = (HandlerMethod) handler;
                RequestMapping annotation = h.getMethodAnnotation(RequestMapping.class);
                String name = annotation.name();
                //判断当前用户是否具有响应的请求权限
                if(apis.contains(name)) {
                    request.setAttribute("user_claims",claims);
                    return true;
                }else {
                    throw new CommonException(ResultCode.UNAUTHORISE);
                }
            }
        }
        throw new CommonException(ResultCode.MOBILEORPASSWORDERROR);
    }
}
