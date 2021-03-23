package com.huoguo.batch.template;

import com.huoguo.batch.annotation.BatchName;
import com.huoguo.batch.annotation.BatchSuper;
import com.huoguo.batch.constant.BatchConstants;
import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.util.BatchBean;
import com.huoguo.batch.util.BatchUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 模板模式执行数据库的批量操作
 *
 * @author Lizhenghuang
 */
public abstract class AbstractTemplate {

    /**
     * 模板模式对外提供的方法
     *
     * @param list      数据集合
     * @param batchSize 每次批量操作的数据集合大小
     * @param clazz     类实例
     * @param splicer   条件构造器
     * @return 是否成功
     */
    public boolean bacth(List<?> list, int batchSize, Class<?> clazz, Splicer splicer) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("The current collection is empty");
        }
        return this.handle(list, batchSize, clazz == null ? this.getClazz(list) : clazz, splicer);
    }

    /**
     * 获取表名、列名、相对应的值，用此来拼接SQL，并执行数据库操作
     *
     * @param list      数据集合
     * @param batchSize 每次批量操作的数据集合大小
     * @param clazz     类实例
     * @param splicer   条件构造器
     * @return 是否成功
     */
    private boolean handle(List<?> list, int batchSize, Class<?> clazz, Splicer splicer) {
        Map<String, Object> map = this.getSql(this.getField(clazz), this.getTableName(clazz), splicer);
        // return this.execute(list, batchSize, map);
        return true;
    }

    /**
     * 获取Class类型
     *
     * @param list 数据集合
     * @return 参数中的class
     */
    private Class<?> getClazz(List<?> list) {
        return list.get(BatchConstants.DEFAULT_INDEX_VALUE).getClass();
    }

    /**
     * 获取对象数据
     *
     * @param clazz 当前对象
     * @return 数组
     */
    private Field[] getField(Class<?> clazz) {
        Class<?> superClass = BatchUtils.isSuper(clazz);
        if (superClass != null && superClass.isAnnotationPresent(BatchSuper.class)) {
            return BatchUtils.concat(clazz.getDeclaredFields(), superClass.getDeclaredFields());
        }
        return clazz.getDeclaredFields();
    }

    /**
     * 获取表名
     *
     * @param clazz 类
     * @return 表名字符串
     */
    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(BatchName.class)) {
            throw new RuntimeException("The ORM relational mapping object cannot be resolved");
        }
        return clazz.getAnnotation(BatchName.class).value();
    }

    /**
     * 获取组装好的SQL
     *
     * @param fields    类的属性数组
     * @param tableName 表名
     * @param splicer   条件构造器
     * @return
     */
    protected abstract Map<String, Object> getSql(Field[] fields, String tableName, Splicer splicer);


    /**
     * 设置SQL的值
     *
     * @param preparedStatement PreparedStatement
     * @param list              数据集合
     * @param size              集合大小
     * @param batchSize         每次批量操作的数据集合大小
     * @param map               逻辑集合
     */
    protected abstract void setVal(PreparedStatement preparedStatement, List<?> list, int size, int batchSize, Map<String, Object> map);

    /**
     * 执行数据库操作
     *
     * @return 是否成功
     */
    private boolean execute(List<?> list, int batchSize, Map<String, Object> map) {
        DataSource dataSource = (DataSource) BatchBean.getBean(BatchConstants.DEFAULT_BEAN_DATASOURCE);
        Connection conn = null;
        PreparedStatement ptm = null;
        int size = list.size();
        boolean result = false;

        try {
            conn = dataSource.getConnection();
            ptm = conn.prepareStatement(map.get(BatchConstants.DEFAULT_KEY_SQL).toString());
            conn.setAutoCommit(false);
            this.setVal(ptm, list, size, batchSize, map);
            ptm.executeBatch();
            conn.commit();
            result = true;
        } catch (SQLException sql) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (ptm != null) {
                    ptm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
