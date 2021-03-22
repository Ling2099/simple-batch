package com.huoguo.batch.template.child;

import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.template.AbstractTemplate;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * 批量操作 删除
 *
 * @author lizhenghuang
 */
public class BatchDeleteTemplate extends AbstractTemplate {

    @Override
    protected Map<String, Object> getSql(Field[] fields, String tableName, Splicer splicer) {
        return null;
    }

    @Override
    protected void setVal(PreparedStatement preparedStatement, List<?> list, int size, int batchSize, Map<String, Object> map) {

    }
}
