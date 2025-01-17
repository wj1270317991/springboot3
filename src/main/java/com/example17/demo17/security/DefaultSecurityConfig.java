package com.example17.demo17.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * com.example17.demo17.security
 * User: wangjun
 * Date: 2025/1/17 - 14:51
 * Description:
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {


    /**
     * 用于认证的Spring Security过滤器链。
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/assets/**","/webjars/**","/actuator/**","/oauth2/**","/login").permitAll()
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());


        return http.build();
    }




    /**
     * 配置内存用户
     * @param passwordEncoder 密码管理器
     */
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {


        UserDetails userDetails = User.withUsername("dailymart")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();


        return new InMemoryUserDetailsManager(userDetails);
    }




}
