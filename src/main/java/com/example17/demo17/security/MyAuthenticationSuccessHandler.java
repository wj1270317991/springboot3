package com.example17.demo17.security;

import com.alibaba.fastjson2.JSON;
import com.example17.demo17.utils.ResponseResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.example17.demo17.security
 * ClassName: MyAuthenticationSuccessHandler
 * Description:
 * Create by: wangjun
 * Date: 2024/2/21 17:41
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("AuthenticationSuccessHandlerImpl 登录认证成功时调用 ...");

        /**
         * 设置响应状态值
         */
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // JSON信息
        response.getWriter().println("成功");
    }
}
