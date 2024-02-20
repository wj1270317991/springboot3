package com.example17.demo17.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * com.example17.demo17.entity
 * ClassName: Users
 * Description: 
 * Create by: wangjun
 * Date: 2024/2/20 17:06
 */

@Data
@TableName(value = "users")
public class Users {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "email")
    private String email;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`password`")
    private String password;
}