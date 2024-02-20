package com.example17.demo17.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example17.demo17.entity.Users;
import com.example17.demo17.mapper.UsersMapper;
import com.example17.demo17.service.UsersService;
/**
 * com.example17.demo17.service.impl
 * ClassName: UsersServiceImpl
 * Description:
 * Create by: wangjun
 * Date: 2024/2/20 17:06
 */

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService{

}
