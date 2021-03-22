package com.huoguo.batch.template.child;

import com.huoguo.batch.annotation.BatchIgnore;
import com.huoguo.batch.annotation.BatchLogic;
import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.template.AbstractTemplate;
import com.huoguo.batch.util.BatchUtils;

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
        Splicer a = new Splicer().where().and("name", 111).and("relationId");

        StringBuilder builder = new StringBuilder();

        for (Field field : fields) {
            if (BatchUtils.isStatic(field)) {
                continue;
            }

            if (field.isAnnotationPresent(BatchIgnore.class)) {
                continue;
            }

            if (field.isAnnotationPresent(BatchLogic.class)) {
                BatchLogic batchLogic = field.getAnnotation(BatchLogic.class);
                String before = batchLogic.before();

                String name = field.getName();

                builder.append("");
            }
        }

        // 如果是空的话 就代表执行固定的
        if (BatchUtils.isEmpty(splicer)) {

        } else {

        }

        return null;
    }

    @Override
    protected void setVal(PreparedStatement preparedStatement, List<?> list, int size, int batchSize, Map<String, Object> map) {

    }
}
