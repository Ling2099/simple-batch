package com.huoguo.batch.enums;

/**
 * 主键类型枚举
 *
 * @author Lizhenghuang
 */
public enum BatchIdEnum {

    /**
     * 主键 自增ID
     **/
    AUTO(0, "AUTO"),

    /**
     * 主键 用户输入ID
     **/
    INPUT(1, "INPUT"),

    /**
     * 主键 雪花ID
     **/
    ASSIGN_ID(2, "ASSIGN_ID"),

    /**
     * 主键 UUID
     **/
    ASSIGN_UUID(3, "ASSIGN_UUID");

    private final int key;
    private final String value;

    BatchIdEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return value;
    }
}
