package com.example17.demo17.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * com.example17.demo17.utils
 * ClassName: JwtUtils
 * Description:
 * Create by: wangjun
 * Date: 2024/2/27 10:28
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final String signingKey = "3F4428472B4B6250655368566D5971337336763979244226452948404D635166";
    private final Long expire = 3600L * 24L;

    /**
     * @Description: 生成令牌
     * @Author: 翰戈.summer
     * @Date: 2023/11/16
     * @Param: Map
     * @Return: String jwt
     */
    public String getJwt(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims) //设置载荷内容
                .signWith(SignatureAlgorithm.HS256, signingKey) //设置签名算法
                .setExpiration(new Date(System.currentTimeMillis() + expire)) //设置有效时间
                .compact();
    }

    /**
     * @Description: 解析令牌
     * @Author: 翰戈.summer
     * @Date: 2023/11/16
     * @Param: String jwt
     * @Return: Claims claims
     */
    public Claims parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(signingKey) //指定签名密钥
                .parseClaimsJws(jwt) //开始解析令牌
                .getBody();
    }
}
