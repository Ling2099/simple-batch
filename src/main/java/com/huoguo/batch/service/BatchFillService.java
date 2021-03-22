package com.huoguo.batch.service;

import java.util.Map;

/**
 * 填充字段接口
 * 需配置实现类，并注入Spring容器
 *
 * @author Lizhenghuang
 */
public interface BatchFillService {

    /**
     * 获取SQL新增时填充字段与值
     *
     * @return Map
     */
    Map<String, Object> batchInsertFill();

    /**
     * 获取SQL修改时填充字段与值
     *
     * @return Map
     */
    Map<String, Object> batchUpdateFill();
}
