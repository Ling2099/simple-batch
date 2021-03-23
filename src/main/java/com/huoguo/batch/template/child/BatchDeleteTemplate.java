package com.huoguo.batch.template.child;

import com.huoguo.batch.annotation.BatchId;
import com.huoguo.batch.annotation.BatchIgnore;
import com.huoguo.batch.annotation.BatchLogic;
import com.huoguo.batch.constant.BatchConstants;
import com.huoguo.batch.enums.BatchSqlEnum;
import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.template.AbstractTemplate;
import com.huoguo.batch.util.BatchUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批量操作 删除
 *
 * @author lizhenghuang
 */
public class BatchDeleteTemplate extends AbstractTemplate {

    /**
     * 构造SQL语句
     *
     * @param fields    类的属性数组
     * @param tableName 表名
     * @param splicer   条件构造器
     * @return
     */
    @Override
    protected Map<String, Object> getSql(Field[] fields, String tableName, Splicer splicer) {
        Map<String, Object> map = new ConcurrentHashMap<>(BatchConstants.DEFAULT_CAPACITY);

        StringBuilder column = new StringBuilder();
        StringBuilder condition = new StringBuilder();

        for (Field field : fields) {
            if (BatchUtils.isStatic(field)) {
                continue;
            }

            if (field.isAnnotationPresent(BatchIgnore.class)) {
                continue;
            }

            String name = field.getName();
            if (field.isAnnotationPresent(BatchLogic.class)) {
                BatchLogic batchLogic = field.getAnnotation(BatchLogic.class);
                BatchUtils.appends(column, name, BatchConstants.DEFAULT_EQUAL, batchLogic.after());
                continue;
            }

            if (field.isAnnotationPresent(BatchId.class)) {
                if (!BatchUtils.isEmpty(splicer)) {
                    map = splicer.getMap();
                    for (String key : map.keySet()) {
                        if (null != map.get(key)) {
                            BatchUtils.appends(condition, key, BatchConstants.DEFAULT_EQUAL,
                                    BatchUtils.addStr(map.get(key).toString()), BatchConstants.DEFAULT_AND);
                        }
                    }
                }
                BatchUtils.appends(condition, name, BatchConstants.DEFAULT_EQUAL, BatchConstants.DEFAULT_QUESTION);
                map.put(BatchConstants.DEFAULT_PRIMARY_KEY, name);
            }
        }
        String sql = String.format(BatchSqlEnum.DELETE_LIST.getSql(), tableName,
                BatchUtils.toLower(column.toString()), BatchUtils.toLower(condition.toString()));
        map.put(BatchConstants.DEFAULT_KEY_SQL, sql);
        return map;
    }

    /**
     * 批量执行
     *
     * @param preparedStatement PreparedStatement
     * @param list              数据集合
     * @param size              集合大小
     * @param batchSize         每次批量操作的数据集合大小
     * @param map               逻辑集合
     */
    @Override
    protected void setVal(PreparedStatement preparedStatement, List<?> list, int size, int batchSize, Map<String, Object> map) {
        if (BatchUtils.isClazz(list)) {
            hasNotClass(preparedStatement, list, size, batchSize);
            return;
        }
        hasClass(preparedStatement, list, size, batchSize, map);
    }

    /**
     * 集合为包装类型时的批量操作
     *
     * @param preparedStatement PreparedStatement
     * @param list              数据集合
     * @param size              集合大小
     * @param batchSize         每次批量操作的数据集合大小
     */
    private void hasNotClass(PreparedStatement preparedStatement, List<?> list, int size, int batchSize) {
        try {
            for (int i = 0; i < size; i++) {
                preparedStatement.setObject(1, list.get(i));
                preparedStatement.addBatch();
                if (i % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 集合为自定义类型时的批量操作
     *
     * @param preparedStatement PreparedStatement
     * @param list              数据集合
     * @param size              集合大小
     * @param batchSize         每次批量操作的数据集合大小
     * @param map               逻辑集合
     */
    private void hasClass(PreparedStatement preparedStatement, List<?> list, int size, int batchSize, Map<String, Object> map) {
        try {
            String name = map.get(BatchConstants.DEFAULT_PRIMARY_KEY).toString();
            for (int i = 0; i < size; i++) {
                preparedStatement.setObject(1, BatchUtils.getValue(name, list.get(i)));
                preparedStatement.addBatch();
                if (i % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
