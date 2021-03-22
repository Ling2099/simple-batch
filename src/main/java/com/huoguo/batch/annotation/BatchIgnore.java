package com.huoguo.batch.annotation;

import java.lang.annotation.*;

/**
 * 忽略字段注解
 *
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface BatchIgnore {
}
