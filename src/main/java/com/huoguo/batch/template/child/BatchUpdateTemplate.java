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
import com.huoguo.batch.util.BatchUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批量操作 更新
 *
 * @author lizhenghuang
 */
public class BatchUpdateTemplate extends AbstractTemplate {

    /**
     * 构造SQL语句
     *
     * @param fields    类的属性数组
     * @param tableName 表名
     * @param splicer   条件构造器
     * @return Map
     */
    @Override
    protected Map<String, Object> getSql(Field[] fields, String tableName, Splicer splicer) {
        Map<String, Object> map = new ConcurrentHashMap<>(BatchConstants.DEFAULT_CAPACITY);
        int len = fields.length;
        List<String> list = new ArrayList<>(len);
        List<String> conditionList = new ArrayList<>(len);

        StringBuilder column = new StringBuilder().append(BatchConstants.DEFAULT_SET);
        StringBuilder condition = new StringBuilder();

        String columns = null;
        if (BatchUtils.isEmpty(splicer)) {
            this.getSql(fields, map, column, condition, list, conditionList);
        } else {
            Map<String, Object> splicerMap = splicer.getMap();
            if (splicerMap.containsKey(BatchConstants.CUSTOMIZE_COLUMN)) {
                String[] str = (String[]) splicerMap.get(BatchConstants.CUSTOMIZE_COLUMN);
                if (!BatchUtils.isEmpty(str)) {
                    for (String s : str) {
                        list.add(s);
                        this.appendsVal(column, s);
                    }
                }

                Map<String, Object> bean = (Map) BatchBean.getBean(BatchConstants.FILL_NAME_UPDATE);
                for (String s : bean.keySet()) {
                    this.appendsVal(column, s);
                    conditionList.add(s);
                    map.putAll(bean);
                }

                for (Field field : fields) {
                    if (field.isAnnotationPresent(BatchId.class)) {
                        String id = field.getName();
                        BatchUtils.appends(condition, BatchUtils.toLower(id), BatchConstants.DEFAULT_EQUAL, BatchConstants.DEFAULT_QUESTION);
                        map.put(id, BatchConstants.DEFAULT_PRIMARY_KEY);
                        conditionList.add(id);
                        break;
                    }
                }
            } else {
                this.getSql(fields, map, column, condition, list, conditionList);
            }
            this.setCondition(condition, map, splicerMap, conditionList);
        }
        columns = column.toString().substring(0, column.toString().length() - 2);
        String sql = String.format(BatchSqlEnum.UPDATE_LIST.getSql(), tableName, columns, condition.toString());

        map.put(BatchConstants.DEFAULT_KEY_SQL, sql);
        map.put(BatchConstants.DEFAULT_KEY_LIST, list);
        map.put(BatchConstants.DEFAULT_CONDITION, conditionList);
        return map;
    }

    /**
     * 设置自定义where条件
     *
     * @param condition     条件字符串
     * @param map           返回结果容器
     * @param splicerMap    构造器容器
     * @param conditionList 条件实例属性集合
     */
    private void setCondition(StringBuilder condition, Map<String, Object> map, Map<String, Object> splicerMap, List<String> conditionList) {
        for (String s : splicerMap.keySet()) {
            if (!BatchConstants.CUSTOMIZE_COLUMN.equals(s)) {
                BatchUtils.appends(condition, BatchConstants.DEFAULT_AND, BatchUtils.toLower(s),
                        BatchConstants.DEFAULT_EQUAL, BatchConstants.DEFAULT_QUESTION);
                map.putAll(splicerMap);
                conditionList.add(s);
            }
        }
    }

    /**
     * 解析并构建SQL
     *
     * @param fields        Field[]
     * @param map           返回结果容器
     * @param column        StringBuilder
     * @param condition     StringBuilder
     * @param list          实例属性集合
     * @param conditionList 条件实例属性集合
     */
    private void getSql(Field[] fields, Map<String, Object> map, StringBuilder column,
                        StringBuilder condition, List<String> list, List<String> conditionList) {
        for (Field field : fields) {
            if (BatchUtils.isStatic(field)) {
                continue;
            }

            if (field.isAnnotationPresent(BatchIgnore.class)) {
                continue;
            }

            if (field.isAnnotationPresent(BatchLogic.class)) {
                continue;
            }

            String name = field.getName();
            if (field.isAnnotationPresent(BatchId.class)) {
                BatchUtils.appends(condition, BatchUtils.toLower(name), BatchConstants.DEFAULT_EQUAL,
                        BatchConstants.DEFAULT_QUESTION);
                map.put(name, BatchConstants.DEFAULT_PRIMARY_KEY);
                conditionList.add(name);
                continue;
            }

            if (field.isAnnotationPresent(BatchFill.class)) {
                BatchFill batchFill = field.getAnnotation(BatchFill.class);
                if (batchFill.update()) {
                    Map<String, Object> bean = (Map) BatchBean.getBean(BatchConstants.FILL_NAME_UPDATE);
                    if (!BatchUtils.isEmpty(bean) && bean.containsKey(name)) {
                        list.add(name);
                        this.appendsVal(column, name);
                        map.putAll(bean);
                    }
                }
                continue;
            }
            list.add(name);
            this.appendsVal(column, name);
        }
        map.put(BatchConstants.DEFAULT_KEY_LIST, list);
    }

    /**
     * 构建前半部分SQL
     *
     * @param column StringBuilder
     * @param name   列名
     */
    private void appendsVal(StringBuilder column, String name) {
        BatchUtils.appends(column, BatchUtils.toLower(name),
                BatchConstants.DEFAULT_EQUAL, BatchConstants.DEFAULT_QUESTION, BatchConstants.DEFAULT_COMMA);
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
        List<String> condition = (List) map.get(BatchConstants.DEFAULT_CONDITION);
        int len = listStr.size() + condition.size();
        int num = 1;
        Object val;

        try {
            for (int i = 0; i < size; i++) {
                Object obj = list.get(i);
                for (String str : listStr) {
                    val = map.getOrDefault(str, BatchUtils.getValue(str, obj));
                    preparedStatement.setObject(num, val);
                    num++;
                }

                for (String s : condition) {
                    if (BatchConstants.DEFAULT_PRIMARY_KEY.equals(map.get(s))) {
                        val = BatchUtils.getValue(s, obj);
                    } else {
                        val = map.containsKey(s) ? map.get(s) : BatchUtils.getValue(s, obj);

                    }
                    preparedStatement.setObject(num, val);
                    num++;
                }

                if (num > len) {
                    num = 1;
                }

                preparedStatement.addBatch();

                if (i % batchSize == 0) {
                    preparedStatement.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
