package com.example17.demo17.web;

import com.example17.demo17.entity.Users;
import com.example17.demo17.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.example17.demo17.web
 * ClassName: AuthController
 * Description:
 * Create by: wangjun
 * Date: 2024/2/20 17:35
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    // Login REST API
    @GetMapping("/login")
    public String authenticate(Users loginDto){
        String token = authService.login(loginDto);
        return token;
    }
}
