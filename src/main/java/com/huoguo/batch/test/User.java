package com.huoguo.batch.test;

import com.huoguo.batch.annotation.BatchId;
import com.huoguo.batch.annotation.BatchLogic;
import com.huoguo.batch.annotation.BatchName;
import com.huoguo.batch.enums.BatchIdEnum;

@BatchName("user")
public class User {

    @BatchId(type = BatchIdEnum.ASSIGN_ID)
    private Long id;

    private String name;

    private int sex;

    @BatchLogic
    private int isDel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
