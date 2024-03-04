package com.example17.demo17.config;

import com.example17.demo17.security.JwtAuthenticationFilter;
import com.example17.demo17.utils.SpringContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * com.example17.demo17.config
 * ClassName: SecurityConfig
 * Description:
 * Create by: wangjun
 * Date: 2024/2/21 14:06
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() //登录放行
                        .anyRequest().permitAll()
                )
                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .rememberMe(re->re.alwaysRemember(true))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(e->{
                    e.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json; charset=utf-8");
                        String value = new ObjectMapper().writeValueAsString("权限不足！");
                        response.getWriter().write(value);
                    });
                    e.authenticationEntryPoint((request, response, authException) -> {
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("application/json; charset=utf-8");
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        String value = new ObjectMapper().writeValueAsString("未携带token！");
                        response.getWriter().write(value);
                    });
                })
        ;

        //通过上下文获取AuthenticationManager
        //通过上下文获取AuthenticationManager
        AuthenticationManager authenticationManager = SpringContextUtils.getBean("authenticationManager");
        //添加自定义token验证过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User
//                .withUsername("admin")
//                .password("1234")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * 身份认证管理器，调用authenticate()方法完成认证
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    /**
     * 处理身份验证
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }


    /**
     * 静态资源放行
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/doc.html",
                "/doc.html/**",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/webjars/**",
                "/authenticate",
                "/swagger-ui.html/**",
                "/swagger-resources",
                "/swagger-resources/**"
        );
    }
}
