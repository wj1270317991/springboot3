//package com.example17.demo17.web;
//
//import cn.dev33.satoken.oauth2.config.SaOAuth2ServerConfig;
//import cn.dev33.satoken.oauth2.consts.GrantType;
//import cn.dev33.satoken.oauth2.data.model.loader.SaClientModel;
//import cn.dev33.satoken.oauth2.strategy.SaOAuth2Strategy;
//import cn.dev33.satoken.secure.SaSecureUtil;
//import cn.dev33.satoken.stp.SaTokenInfo;
//import cn.dev33.satoken.stp.StpUtil;
//import cn.dev33.satoken.stp.parameter.SaLoginParameter;
//import cn.dev33.satoken.util.SaFoxUtil;
//import cn.dev33.satoken.util.SaResult;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * com.example17.demo17.web
// * ClassName: AuthController
// * Description:
// * Create by: wangjun
// * Date: 2024/2/20 17:35
// */
//@RequiredArgsConstructor
//@RestController
//public class AuthController {
//
//    // 判断当前是否登录
//    @RequestMapping("/sso/isLogin")
//    public Object isLogin() {
//        return SaResult.data(StpUtil.isLogin()).set("loginId", StpUtil.getLoginIdDefaultNull());
//    }
//
//
//    @PostMapping("/login")
//    public SaResult login(String username, String password){
//        //根据用户id登录，第1步，先登录上
//        StpUtil.login("fff");
//
//
//        StpUtil.disable(10001, "comment", 86400);
//
//        // 第2步，获取 Token  相关参数
//        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
//
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("1111111111111");
//
//        BCrypt.hashpw("1111", BCrypt.gensalt());
//
//        // 第3步，返回给前端
//        return SaResult.data(tokenInfo);
//    }
//
//}
