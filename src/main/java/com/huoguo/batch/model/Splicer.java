package com.huoguo.batch.model;


import com.huoguo.batch.constant.BatchConstants;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 条件构造器
 *
 * @author Lizhenghuang
 */
public class Splicer implements Serializable {

    private static final long serialVersionUID = 1868317904364006795L;
    private Map<String, Object> map;

    public Splicer() {
        this.map = new LinkedHashMap<>(BatchConstants.DEFAULT_CAPACITY);
    }

    /**
     * SQL语句where后的条件构造
     *
     * @param column 字段名
     * @param val    该字段对应的值
     * @return Splicer
     */
    public Splicer and(String column, Object val) {
        this.map.put(column, val);
        return this;
    }

    /**
     * SQL语句中自定义写入字段名
     *
     * @param columns 字段名数组
     * @return Splicer
     */
    public Splicer columns(String[] columns) {
        this.map.put(BatchConstants.CUSTOMIZE_COLUMN, columns);
        return this;
    }

    /**
     * 获取当前Map集合实例
     *
     * @return Map
     */
    public Map<String, Object> getMap() {
        return map;
    }
}
