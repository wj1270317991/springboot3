package com.example17.demo17.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example17.demo17.entity.Users;
import com.example17.demo17.mapper.UsersMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * com.example17.demo17.security
 * ClassName: CustomUserDetailsService
 * Description:
 * Create by: wangjun
 * Date: 2024/2/20 17:15
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UsersMapper usersMapper;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Users user = usersMapper.selectOne(Wrappers.<Users>lambdaQuery().eq(Users::getName, usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
