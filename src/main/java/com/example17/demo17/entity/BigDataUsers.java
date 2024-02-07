package com.example17.demo17.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * com.example17.demo17.entity
 * ClassName: BigDataUsers
 * Description:
 * Create by: wangjun
 * Date: 2024/2/5 14:15
 */

@Data
@TableName(value = "big_data_users")
public class BigDataUsers {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("名称")
    @TableField(value = "`name`")
    private String name;

    @ExcelProperty("年龄")
    @TableField(value = "age")
    private Integer age;

    @ExcelProperty("邮箱")
    @TableField(value = "email")
    private String email;
}
