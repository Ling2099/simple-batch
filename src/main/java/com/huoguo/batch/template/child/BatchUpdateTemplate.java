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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批量操作 更新
 *
 * @author lizhenghuang
 */
public class BatchUpdateTemplate extends AbstractTemplate {

    @Override
    protected Map<String, Object> getSql(Field[] fields, String tableName, Splicer splicer) {
        Map<String, Object> map = new ConcurrentHashMap<>(BatchConstants.DEFAULT_CAPACITY);

        StringBuilder column = new StringBuilder();
        StringBuilder condition = new StringBuilder();

        boolean hasNull = !BatchUtils.isEmpty(splicer);
        if (hasNull) {
            Map<String, Object> splicerMap = splicer.getMap();
            if (splicerMap.containsKey(BatchConstants.CUSTOMIZE_COLUMN)) {
                String[] str = (String[]) splicerMap.get(BatchConstants.CUSTOMIZE_COLUMN);
                int len = str.length - 1;

                for (int i = 0; i <= len; i++) {
                    column.append(BatchConstants.DEFAULT_SET).append(BatchUtils.toLower(str[i])).append(BatchConstants.DEFAULT_EQUAL).append(BatchConstants.DEFAULT_QUESTION);
                    if (i != len) {
                        column.append(BatchConstants.DEFAULT_COMMA);
                    }
                }
            }

            for (String str : splicerMap.keySet()) {
                if (!BatchConstants.CUSTOMIZE_COLUMN.equals(str)) {
                    condition.append(BatchUtils.toLower(str)).append(BatchConstants.DEFAULT_EQUAL).append(BatchUtils.addStr(map.get(str).toString())).append(BatchConstants.DEFAULT_AND);
                }
            }
        }

        constructionSql(fields, column, condition, hasNull);

//        for (Field field : fields) {
//            if (BatchUtils.isStatic(field)) {
//                continue;
//            }
//
//            if (field.isAnnotationPresent(BatchIgnore.class)) {
//                continue;
//            }
//
//            String name = field.getName();
//            if (field.isAnnotationPresent(BatchFill.class)) {
//                Map<String, Object> bean = (Map) BatchBean.getBean(BatchConstants.FILL_NAME_INSERT);
//                if (!bean.isEmpty()) {
//                    column.append(BatchConstants.DEFAULT_SET).append(name).append(BatchConstants.DEFAULT_EQUAL).append(BatchUtils.addStr(bean.get(name).toString())).append(",");
//                }
//                continue;
//            }
//
//            if (field.isAnnotationPresent(BatchId.class)) {
//                condition.append(name).append(BatchConstants.DEFAULT_EQUAL).append(BatchConstants.DEFAULT_QUESTION);
//                continue;
//            }
//            column.append(BatchConstants.DEFAULT_SET).append(name).append(BatchConstants.DEFAULT_EQUAL).append(BatchConstants.DEFAULT_QUESTION).append("?");
//        }

        String sql = String.format(BatchSqlEnum.UPDATE_LIST.getSql(), tableName, column.toString(), condition.toString());

        System.out.println(sql);
        return null;
    }

    private void constructionSql(Field[] fields, StringBuilder column, StringBuilder condition, boolean hasNull) {
        int len = fields.length - 1;
        boolean hasLen = column.toString().length() > 0;
        for (int i = 0; i <= len; i++) {
            if (BatchUtils.isStatic(fields[i])) {
                continue;
            }

            if (fields[i].isAnnotationPresent(BatchIgnore.class)) {
                continue;
            }

            if (fields[i].isAnnotationPresent(BatchLogic.class)) {
                continue;
            }

            String name = fields[i].getName();
            if (fields[i].isAnnotationPresent(BatchId.class)) {
                condition.append(BatchUtils.toLower(name)).append(BatchConstants.DEFAULT_EQUAL).append(BatchConstants.DEFAULT_QUESTION);
                continue;
            }

            boolean hasFill = fields[i].isAnnotationPresent(BatchFill.class);
            if (!hasNull && !hasFill) {
                column.append(BatchConstants.DEFAULT_SET).append(BatchUtils.toLower(name)).append(BatchConstants.DEFAULT_EQUAL).append(BatchConstants.DEFAULT_QUESTION);
                if (i != len) {
                    column.append(BatchConstants.DEFAULT_COMMA);
                }
                continue;
            }

            if (hasFill) {
                Map<String, Object> bean = (Map) BatchBean.getBean(BatchConstants.FILL_NAME_INSERT);
                if (!bean.isEmpty()) {
                    if (i == 0 && hasLen && hasNull) {
                        column.append(BatchConstants.DEFAULT_COMMA);
                    }
                    column.append(BatchConstants.DEFAULT_SET).append(BatchUtils.toLower(name)).append(BatchConstants.DEFAULT_EQUAL).append(BatchConstants.DEFAULT_QUESTION);
                    // column.append(BatchConstants.DEFAULT_SET).append(BatchUtils.toLower(name)).append(BatchConstants.DEFAULT_EQUAL).append(BatchUtils.addStr(bean.get(name).toString()));
                    if (i != len) {
                        column.append(BatchConstants.DEFAULT_COMMA);
                    }
                }
            }
        }
    }

    @Override
    protected void setVal(PreparedStatement preparedStatement, List<?> list, int size, int batchSize, Map<String, Object> map) {

    }
}
