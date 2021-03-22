package com.huoguo.batch.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Bean 操作工具类
 * 需要在项目启动时交给Spring进行管理
 *
 * @author Lizhenghuang
 */
@Component
public class BatchBean implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获取Spring容器
     *
     * @param applicationContext Spring容器
     * @throws BeansException 异常抛出
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BatchBean.applicationContext == null) {
            BatchBean.applicationContext = applicationContext;
        }
    }

    /**
     * 获取 ApplicationContext
     *
     * @return ApplicationContext
     */
    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取Spring容器Bean
     *
     * @param name 字符串
     * @return Bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过Class获取Spring容器Bean
     *
     * @param clazz Class
     * @param <T>   泛型化的Bean
     * @return Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过指定name和Class获取Spring容器Bean
     *
     * @param name  字符串
     * @param clazz Class
     * @param <T>   泛型
     * @return Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
