package com.example17.demo17.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example17.demo17.entity.Users;
import com.example17.demo17.mapper.UsersMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;


/**
 * com.example17.demo17.security
 * ClassName: DBUserDetailsManager
 * Description:
 * Create by: wangjun
 * Date: 2024/2/21 15:49
 */
@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    @Resource
    UsersMapper usersMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersMapper.selectOne(Wrappers.<Users>lambdaQuery().eq(Users::getName, username));
        return user;
    }
}
