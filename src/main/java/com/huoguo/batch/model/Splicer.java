package com.huoguo.batch.model;


import com.huoguo.batch.constant.BatchConstants;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 条件构造器
 *
 * @author Lizhenghuang
 */
public class Splicer implements Serializable {

    private static final long serialVersionUID = 1868317904364006795L;
    private Map<String, Object> map;

    public Splicer() {
        this.map = new ConcurrentHashMap<>(BatchConstants.DEFAULT_CAPACITY);
    }

    public Splicer where() {
        return this;
    }

    public Splicer and(String column, Object val) {
        this.map.put(column, val);
        return this;
    }

    public Splicer and(String column) {
        this.map.put(column, null);
        return this;
    }

    public Splicer columns(Set<String> columns) {
        this.map.put(BatchConstants.CUSTOMIZE_COLUMN, columns);
        return this;
    }

    public Map<String, Object> getMap() {
        return map;
    }

}
