package com.example17.demo17.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "menu")
@ExcelIgnoreUnannotated
public class Menu {

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Long id;


    @TableField(value = "MENU_ID")
    private Long menuId;


    @TableField(value = "CODE")
    private String code;


    @TableField(value = "TITLE")
    private String title;

    @TableField(value = "DISPLAY_ORDER")
    private Integer displayOrder;

    @TableField(value = "PARENT_ID")
    private Long parentId;

    @TableField(value = "ENABLE_FLAG")
    private String enableFlag;

    @TableField(value = "CREATED_DATE")
    private Date createdDate;

    @TableField(value = "LAST_UPDATE")
    private Date lastUpdate;

    @TableField(value = "SOURCE_SYSTEM")
    private String sourceSystem;


    @ExcelProperty("一级菜单")
    private String one;

    @ExcelProperty("二级菜单")
    private String two;

    @ExcelProperty("三级菜单")
    private String three;
}
