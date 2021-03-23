package com.huoguo.batch.test;

import com.huoguo.batch.model.Splicer;
import com.huoguo.batch.service.BatchService;
import com.huoguo.batch.service.impl.BatchServiceImpl;
import com.huoguo.batch.util.BatchUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        Splicer splicer = new Splicer().where().and("sex", 1).and("name", "张三");

//        BatchService batchService = new BatchServiceImpl();
//        batchService.deleteBatch(list, splicer);

        String a = "我是你爸爸";
        System.out.println(BatchUtils.addStr(a));

    }
}
