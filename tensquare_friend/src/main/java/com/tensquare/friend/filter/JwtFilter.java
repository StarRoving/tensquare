package com.tensquare.friend.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 */

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("filter");
        String header = request.getHeader("Authorization");

        if (header != null) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if(claims.get("roles").equals("admin")){//管理员身份
                        request.setAttribute("admin_claims",claims);
                    }
                    if(claims.get("roles").equals("user")){//普通用户
                        request.setAttribute("user_claims",claims);
                    }
                }
            }
        }
        return true;
    }
}
