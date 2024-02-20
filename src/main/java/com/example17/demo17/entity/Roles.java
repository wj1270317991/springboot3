package com.example17.demo17.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * com.example17.demo17.entity
 * ClassName: Roles
 * Description: 
 * Create by: wangjun
 * Date: 2024/2/20 17:06
 */

@Data
@TableName(value = "roles")
public class Roles {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "`name`")
    private String name;
}