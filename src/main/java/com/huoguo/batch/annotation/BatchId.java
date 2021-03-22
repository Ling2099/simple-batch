package com.huoguo.batch.annotation;

import com.huoguo.batch.enums.BatchIdEnum;

import java.lang.annotation.*;

/**
 * 数据库主键字段注解
 *
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface BatchId {

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
     * @return 字段烈性
     */
    BatchIdEnum type() default BatchIdEnum.INPUT;
}
