package com.example17.demo17.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * com.example17.demo17.security
 * ClassName: MyPersistentTokenBasedRememberMeServices
 * Description:
 * Create by: wangjun
 * Date: 2024/2/27 17:47
 */
public class MyPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        // 这里可以在LoginFilter中读取出来，保存到request中。
        String paramValue = String.valueOf(request.getAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER));
        // 也可以在这里获取
//        try {
//            Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
//            String rememberVal = String.valueOf(userInfo.get(AbstractRememberMeServices.DEFAULT_PARAMETER));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if (paramValue != null) {
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                    || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                return true;
            }
        }
        return false;
    }

    public MyPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }
}
