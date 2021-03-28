package com.huoguo.batch.constant;

import java.util.regex.Pattern;

/**
 * 默认通用常量类
 *
 * @author Lizhenghuang
 */
public final class BatchConstants {

    /**
     * 默认集合第一个下标
     **/
    public static final int DEFAULT_INDEX_VALUE = 0;

    /**
     * 默认执行批量操作的数量
     **/
    public static final int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 默认主键标识符
     **/
    public static final String DEFAULT_PRIMARY_KEY = "primary key";

    /**
     * 默认Spring容器数据源名称
     **/
    public static final String DEFAULT_BEAN_DATASOURCE = "dataSource";

    /**
     * 默认值 逗号
     **/
    public static final String DEFAULT_COMMA = ", ";

    /**
     * 默认值 问号
     **/
    public static final String DEFAULT_QUESTION = "?";

    /**
     * 默认容器Key sql
     **/
    public static final String DEFAULT_KEY_SQL = "sql";

    /**
     * 默认容器Key set
     **/
    public static final String DEFAULT_KEY_LIST = "list";

    /**
     * 默认容器Key condition
     **/
    public static final String DEFAULT_CONDITION = "condition";

    /**
     * 默认反射方法
     **/
    public static final String DEFAULT_GET = "get";

    /**
     * 默认SQL条件拼接字段
     **/
    public static final String DEFAULT_EQUAL = " = ";

    /**
     * 默认SQL条件拼接字段
     **/
    public static final String DEFAULT_AND = " AND ";

    /**
     * 默认新增填充Bean Name
     **/
    public static final String FILL_NAME_INSERT = "batchInsertFill";

    /**
     * 默认修改填充Bean Name
     **/
    public static final String FILL_NAME_UPDATE = "batchUpdateFill";

    /**
     * 默认正则英文字母大写
     **/
    public static Pattern pattern = Pattern.compile("[A-Z]+");

    /**
     * 默认容器初始值
     **/
    public static final int DEFAULT_CAPACITY = 16;

    /**
     * 默认自定义列名Key
     **/
    public static final String CUSTOMIZE_COLUMN = "column";

    /**
     * 默认数据库关键字set
     **/
    public static final String DEFAULT_SET = "SET ";

}
