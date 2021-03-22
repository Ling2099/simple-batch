package com.huoguo.batch.service;

import com.huoguo.batch.model.Splicer;

import java.util.List;

/**
 * 对外暴露的调用接口
 *
 * @author Lizhenghuang
 */
public interface BatchService {

    /**
     * 批量新增
     *
     * @param list 数据集合
     * @return 是否成功
     */
    Boolean insertBatch(List<?> list);

    /**
     * 批量新增
     *
     * @param list 数据集合
     * @param size 每次写操作的数据集合大小
     * @return 是否成功
     */
    Boolean insertBatch(List<?> list, int size);

    /**
     * 批量删除
     *
     * @param list 数据集合
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list);

    /**
     * 批量删除
     *
     * @param list 数据集合
     * @param size 每次数据操作的集合大小
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, int size);

    /**
     * 批量删除
     *
     * @param list  数据集合
     * @param clazz 类实例
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, Class<?> clazz);

    /**
     * 批量删除
     *
     * @param list  数据集合
     * @param clazz 类实例
     * @param size  每次写操作的数据集合大小
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, int size, Class<?> clazz);

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param splicer 条件构造器
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, Splicer splicer);

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param size    每次数据操作的集合大小
     * @param splicer 条件构造器
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, int size, Splicer splicer);

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param splicer 条件构造器
     * @param clazz   类实例
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, Splicer splicer, Class<?> clazz);

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param size    每次数据操作的集合大小
     * @param splicer 条件构造器
     * @param clazz   类实例
     * @return 是否成功
     */
    Boolean deleteBatch(List<?> list, int size, Splicer splicer, Class<?> clazz);

    /**
     * 批量更新
     *
     * @param list 数据集合
     * @return 是否成功
     */
    Boolean updateBatch(List<?> list);

}
