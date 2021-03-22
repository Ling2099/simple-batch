package com.huoguo.batch.annotation;


import java.lang.annotation.*;

/**
 * 数据库日期类型字段注解
 *
 * @author Lizhenghuang
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface BatchDate {

    /**
     * 注解默认值
     *
     * @return 列名字符串
     */
    String value() default "";

}
