package com.huoguo.batch.service.impl;

import com.huoguo.batch.constant.BatchConstants;
import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.service.BatchService;
import com.huoguo.batch.template.AbstractTemplate;
import com.huoguo.batch.template.child.BatchDeleteTemplate;
import com.huoguo.batch.template.child.BatchInsertTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对外暴露的调用接口实现类
 *
 * @author Lizhenghuang
 */
@Service
public class BatchServiceImpl implements BatchService {

    /**
     * 批量新增
     *
     * @param list 数据集合
     * @return 是否成功
     */
    @Override
    public Boolean insertBatch(List<?> list) {
        AbstractTemplate insert = new BatchInsertTemplate();
        return insert.bacth(list, BatchConstants.DEFAULT_BATCH_SIZE, null, null);
    }

    /**
     * 批量新增
     *
     * @param list 数据集合
     * @param size 每次写操作的数据集合大小
     * @return 是否成功
     */
    @Override
    public Boolean insertBatch(List<?> list, int size) {
        AbstractTemplate insert = new BatchInsertTemplate();
        return insert.bacth(list, size, null, null);
    }

    /**
     * 批量删除
     *
     * @param list 数据集合
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, BatchConstants.DEFAULT_BATCH_SIZE, null, null);
    }

    /**
     * 批量删除
     *
     * @param list 数据集合
     * @param size 每次数据操作的集合大小
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, int size) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, size, null, null);
    }

    /**
     * 批量删除
     *
     * @param list  数据集合
     * @param clazz 类实例
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, Class<?> clazz) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, BatchConstants.DEFAULT_BATCH_SIZE, clazz, null);
    }

    /**
     * 批量删除
     *
     * @param list  数据集合
     * @param size  每次写操作的数据集合大小
     * @param clazz 类实例
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, int size, Class<?> clazz) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, size, clazz, null);
    }

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param splicer 条件构造器
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, Splicer splicer) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, BatchConstants.DEFAULT_BATCH_SIZE, null, splicer);
    }

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param size    每次数据操作的集合大小
     * @param splicer 条件构造器
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, int size, Splicer splicer) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, size, null, splicer);
    }

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param splicer 条件构造器
     * @param clazz   类实例
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, Splicer splicer, Class<?> clazz) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, BatchConstants.DEFAULT_BATCH_SIZE, clazz, splicer);
    }

    /**
     * 批量删除
     *
     * @param list    数据集合
     * @param size    每次数据操作的集合大小
     * @param splicer 条件构造器
     * @param clazz   类实例
     * @return 是否成功
     */
    @Override
    public Boolean deleteBatch(List<?> list, int size, Splicer splicer, Class<?> clazz) {
        AbstractTemplate delete = new BatchDeleteTemplate();
        return delete.bacth(list, size, clazz, splicer);
    }

    /**
     * 批量更新
     *
     * @param list 数据集合
     * @return 是否成功
     */
    @Override
    public Boolean updateBatch(List<?> list) {
//        AbstractTemplate update = new BatchUpdataTemplate();
//        return update.bacth(list, BatchConstants.DEFAULT_BATCH_SIZE, null, null);
        return true;
    }
}
