package com.huoguo.batch.annotation;

import java.lang.annotation.*;

/**
 * 逻辑删除字段注解
 *
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface BatchLogic {

    /**
     * 注解默认值
     *
     * @return 列名字符串
     */
    @Deprecated
    String value() default "";

    /**
     * 注解默认值
     *
     * @return 默认值 删除前
     */
    String before() default "0";

    /**
     * 注解默认值
     *
     * @return 默认值 删除后
     */
    String after() default "1";
}
