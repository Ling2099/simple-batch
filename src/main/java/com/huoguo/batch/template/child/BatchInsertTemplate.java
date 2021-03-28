package com.huoguo.batch.template.child;

import com.huoguo.batch.annotation.BatchFill;
import com.huoguo.batch.annotation.BatchId;
import com.huoguo.batch.annotation.BatchIgnore;
import com.huoguo.batch.annotation.BatchLogic;
import com.huoguo.batch.constant.BatchConstants;
import com.huoguo.batch.enums.BatchSqlEnum;
import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.template.AbstractTemplate;
import com.huoguo.batch.util.BatchBean;
import com.huoguo.batch.util.BatchSnow;
import com.huoguo.batch.util.BatchUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批量操作 插入
 *
 * @author lizhenghuang
 */
public class BatchInsertTemplate extends AbstractTemplate {

    /**
     * 构造SQL语句
     *
     * @param fields    类的属性数组
     * @param tableName 表名
     * @param splicer   条件构造器
     * @return Map
     */
    @Override
    protected Map<String, Object> getSql(Field[] fields, final String tableName, Splicer splicer) {
        int num = 0;
        int len = fields.length;
        Map<String, Object> map = new ConcurrentHashMap<>(BatchConstants.DEFAULT_CAPACITY);
        List<String> list = new ArrayList<>(len);
        StringBuilder column = new StringBuilder();
        StringBuilder value = new StringBuilder();

        for (Field field : fields) {
            num++;
            if (BatchUtils.isStatic(field)) {
                continue;
            }

            if (field.isAnnotationPresent(BatchIgnore.class)) {
                continue;
            }

            String name = field.getName();
            if (field.isAnnotationPresent(BatchId.class)) {
                BatchId batchId = field.getAnnotation(BatchId.class);
                int key = batchId.type().getKey();

                if (key == 0) {
                    continue;
                }

                if (key == 2 || key == 3) {
                    map.put(BatchConstants.DEFAULT_PRIMARY_KEY, key);
                    name = BatchConstants.DEFAULT_PRIMARY_KEY;
                }
            }

            if (field.isAnnotationPresent(BatchLogic.class)) {
                BatchLogic batchLogic = field.getAnnotation(BatchLogic.class);
                map.put(name, batchLogic.before());
            }

            if (field.isAnnotationPresent(BatchFill.class)) {
                Map<String, Object> bean = (Map) BatchBean.getBean(BatchConstants.FILL_NAME_INSERT);
                if (!bean.isEmpty()) {
                    map.putAll(bean);
                }
            }

            column.append(BatchUtils.toLower(field.getName()));
            value.append(BatchConstants.DEFAULT_QUESTION);
            if (num != len) {
                column.append(BatchConstants.DEFAULT_COMMA);
                value.append(BatchConstants.DEFAULT_COMMA);
            }
            list.add(name);
        }
        String sql = String.format(BatchSqlEnum.INSERT_LIST.getSql(), tableName, column.toString(), value.toString());

        map.put(BatchConstants.DEFAULT_KEY_SQL, sql);
        map.put(BatchConstants.DEFAULT_KEY_LIST, list);
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
        List<String> listStr = (List) map.get(BatchConstants.DEFAULT_KEY_LIST);
        int len = listStr.size();
        int num = 1;
        Object val;

        try {
            for (int i = 0; i < size; i++) {
                Object obj = list.get(i);
                for (String str : listStr) {
                    if (!BatchConstants.DEFAULT_PRIMARY_KEY.equals(str)) {
                        val = map.getOrDefault(str, BatchUtils.getValue(str, obj));
                    } else {
                        val = this.idValue(map.get(str));
                    }
                    preparedStatement.setObject(num, val);

                    num++;
                    if (num > len) {
                        num = 1;
                    }
                }
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
     * 获取ID（雪花ID或UUID）
     *
     * @param key 枚举ID判断的值
     * @return Object
     */
    private Object idValue(Object key) {
        return Integer.parseInt(String.valueOf(key)) == 2 ? BatchSnow.genId() : BatchUtils.getUuid();
    }
}
