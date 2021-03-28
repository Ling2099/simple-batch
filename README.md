<p align="center">
  简单工具，简单使用
</p>



## 由来 | Origin

虽然国内外大厂、大佬的插件、工具多如牛毛，但我还是决定搞一个属于自己的，  
伟大领袖毛主席曾经说过：“实践是检验真理的唯一标准”，只有自己动手做过，体验过，不管结果如何，  
才能在由此得到提升，从而认识自己的不足与待提高的地方。  
所以，再造一遍轮子，尽管这个轮子不怎么完美，甚至缺陷，但路走多了，总有削圆的时候。

---

## 简介 | Intro

- **主键策略**：提供四种主键策略（用户输入、自增ID、雪花ID、UUID）
- **自动填充**：提供字段值的自动填充支持
- **逻辑删除**：提供默认的逻辑删除值或自定义
- **简化操作**：通过添加实体类注解即可达到效果
> 工作环境依赖于SpringMVC或Spring Boot、Mybatis或Mybatis Plus框架
> 更多说明请移步：<http://baidu.com>

---

## 说明 | Instructions

|注解|说明|
|:----|:----|
|**@BatchName**|数据库表名的注解|
|**@BatchId**|主键策略的注解|
|**@BatchFill**|自动填充字段的注解|
|**@BatchLogic**|逻辑删除字段的注解|
|**@BatchIgnore**|需要手动忽略字段的注解|
|**@BatchSuper**|实体类所继承的父类注解|
---

## 演示 | demonstration

* Maven依赖
```xml
<dependency>
    <groupId>com.github.Ling2099</groupId>
    <artifactId>simple-batch</artifactId>
    <version>1.0.0</version>
</dependency>
```
* 开启扫描  
```java
@ComponentScan("com.huoguo.batch")
```
* 自动注入  
```java
@Autowired
private BatchService batchService;
```
* 实体类注解
```java
/**
 * BatchName注解用于声明此model所对应的数据库表名
 * 该实体类演示使用了Lombok插件
 *
 * @author lizhenghuang
 */
@Data
@Accessors(chain = true)
@BatchName("user")
public class User {
    
    // 当前使用主键策略为雪花ID
    @BatchId(type = BatchIdEnum.ASSIGN_ID)
    private Long id;

    private String name;

    private int age;

    private String msg;

    private int sex;

    // 此注解声明该成员变量不参批量操作
    @BatchIgnore
    private String card;

    // 此注解声明此成员变量为逻辑删除字段
    @BatchLogic
    private int isDel;
}  
```
* 批量新增
```java
public boolean save() {
    List<User> list = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
        User user = new User().setAge(i).setMsg("批量新增" + i)
            .setName("张三" + i).setCard("编号" + i).setSex(i);
        list.add(user);
    }
    return batchService.insertBatch(list);
}
```
* 批量修改
```java
public boolean update() {
    List<User> list = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
        User user = new User().setId(i).setAge(i * 2).setMsg("批量修改" + i)
            .setName("李四" + i).setCard("编号" + i * 2).setSex(i * 2);
        list.add(user);
    }
    return batchService.updateBatch(list);
}
```
* 逻辑删除
```java
public boolean delete() {
    List<User> list = new ArrayList<>();
    for (int i = 0; i < 100000; i++) {
        User user = new User().setId(i);
        list.add(user);
    }
    return batchService.deleteBatch(list);
}
```

## 期望 | Futures

> 还请各位大佬多多提出建议，帮助完善各个功能，谢谢！
