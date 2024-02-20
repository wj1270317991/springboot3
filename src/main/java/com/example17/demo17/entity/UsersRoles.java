package com.example17.demo17.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * com.example17.demo17.entity
 * ClassName: UsersRoles
 * Description: 
 * Create by: wangjun
 * Date: 2024/2/20 17:06
 */

@Data
@TableName(value = "users_roles")
public class UsersRoles {
    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "role_id")
    private Long roleId;
}