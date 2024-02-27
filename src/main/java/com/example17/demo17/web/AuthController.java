package com.example17.demo17.web;

import com.example17.demo17.entity.Users;
import com.example17.demo17.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * com.example17.demo17.web
 * ClassName: AuthController
 * Description:
 * Create by: wangjun
 * Date: 2024/2/20 17:35
 */
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    @GetMapping("/")
    public String authenticate(){
        return "success";
    }


    @PostMapping("login")
    public String doLogin(@RequestBody Users userLoginDTO) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            //获取用户权限信息
            String authorityString = "";
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                authorityString = authority.getAuthority();
            }

            //用户身份验证成功，生成并返回jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", userDetails.getUsername());
            claims.put("authorityString", authorityString);
            String jwtToken = jwtUtils.getJwt(claims);
            return jwtToken;
        } catch (Exception ex) {
            //用户身份验证失败，返回登陆失败提示
            return "用户名或密码错误";
        }
    }

}
