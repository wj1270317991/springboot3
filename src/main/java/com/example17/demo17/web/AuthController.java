package com.example17.demo17.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
public class AuthController {

    @GetMapping("/")
    public String authenticate(){
        return "success";
    }

}
