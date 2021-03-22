package com.huoguo.batch.annotation;

import java.lang.annotation.*;

/**
 * 数据库表名注解
 *
 * @author Lizhenghuang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BatchName {

    /**
     * 注解默认值
     *
     * @return 表名字符串
     */
    String value() default "";
}
