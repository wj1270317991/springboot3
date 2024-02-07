package com.example17.demo17.annotations;

import java.lang.annotation.*;

/**
 * com.example17.demo17.annotations
 * ClassName: NotRepeat
 * Description: 幂等性校验注解
 * Create by: wangjun
 * Date: 2024/2/6 18:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotRepeat {
}
