package com.huoguo.batch.annotation;

import java.lang.annotation.*;

/**
 * 实体类的父类注解
 *
 * @author Lizhenghuang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface BatchSuper {
}
