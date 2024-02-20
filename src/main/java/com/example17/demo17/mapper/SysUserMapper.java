package com.example17.demo17.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example17.demo17.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * com.example17.demo17.mapper
 * ClassName: SysUserMapper
 * Description:
 * Create by: wangjun
 * Date: 2024/2/20 14:20
 */

public interface SysUserMapper extends BaseMapper<SysUser> {
    Optional<UserDetails> findByEmail(String username);
}
