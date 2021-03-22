package com.huoguo.batch.annotation;

import java.lang.annotation.*;

/**
 * 自动填充注解
 *
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface BatchFill {

    /**
     * 字段名字符串
     *
     * @return 字段名字符串
     */
    @Deprecated
    String value() default "";

    /**
     * 新增时是否填充该字段
     *
     * @return 默认不填充
     */
    boolean insert() default false;

    /**
     * 修改时是否填充该字段
     *
     * @return 默认不填充
     */
    boolean update() default false;

}
