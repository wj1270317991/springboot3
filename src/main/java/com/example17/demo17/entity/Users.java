package com.example17.demo17.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * com.example17.demo17.entity
 * ClassName: Users
 * Description:
 * Create by: wangjun
 * Date: 2024/2/20 17:06
 */

@Data
@TableName(value = "users")
public class Users implements UserDetails {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "email")
    private String email;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`password`")
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
