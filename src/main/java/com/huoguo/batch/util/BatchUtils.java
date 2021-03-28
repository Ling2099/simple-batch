package com.huoguo.batch.util;

import com.huoguo.batch.constant.BatchConstants;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * 工具类
 *
 * @author Lizhenghuang
 */
public final class BatchUtils {

    /**
     * 合并数组
     *
     * @param first  第一个数组
     * @param second 第二个数组
     * @param <T>    泛型
     * @return 新的数组
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 获取当前对象的父类对象
     *
     * @param clazz 当前对象
     * @return 父类对象
     */
    public static Class<?> isSuper(Class<?> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        return superClazz != null && superClazz != Object.class ? superClazz : null;
    }

    /**
     * 在反射时，排除掉序列化ID
     *
     * @param field 当前对象属性
     * @return true代表当前属性为序列化ID
     */
    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * 判断集合为包装类型或自定义对象
     *
     * @param list 实例集合
     * @return boolean
     */
    public static boolean isClazz(List<?> list) {
        Class clazz = list.get(BatchConstants.DEFAULT_INDEX_VALUE).getClass();
        return clazz == Integer.class || clazz == Long.class || clazz == String.class;
    }

    /**
     * 转换数据库与实体类映射字段
     * 下划线/小写转大写
     *
     * @param str 数据库字段
     * @return 实体类属性
     */
    public static String toUpper(String str) {
        StringBuilder builder = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if ("_".equals(ch + "")) {
                i = i + 1;
                String up = (str.charAt(i) + "").toUpperCase();
                builder.append(up);
                continue;
            }
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * 转换数据库与实体类映射字段
     * 大写转下划线/小写
     *
     * @param str 数据库字段
     * @return 实体类属性
     */
    public static String toLower(String str) {
        StringBuilder builder = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            String ch = str.charAt(i) + "";
            Matcher matcher = BatchConstants.pattern.matcher(ch);
            if (matcher.matches()) {
                builder.append("_");
            }
            builder.append(ch.toLowerCase());
        }
        return builder.toString();
    }

    /**
     * 获取对象中的属性值
     *
     * @param str 属性名
     * @param obj 参数对象
     * @return 合适的属性值
     */
    public static Object getValue(String str, Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (str.equals(field.getName())) {
                    String upper = BatchConstants.DEFAULT_GET.concat(str.substring(0, 1).toUpperCase()).concat(str.substring(1));
                    Method method = obj.getClass().getMethod(upper, new Class[]{});
                    return method.invoke(obj, new Object[]{});
                }
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串拼接
     *
     * @param sb      StringBuilder
     * @param strings String数组
     */
    public static void appends(StringBuilder sb, String... strings) {
        for (String str : strings) {
            sb.append(str);
        }
    }

    /**
     * 获取UUID
     *
     * @return 字符串
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 判别字符串是否为空或空字符串
     *
     * @param str 入参字符串
     * @return boolean
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 字符串追加引号
     *
     * @param str 当前字符串
     * @return String
     */
    public static String addStr(String str) {
        return "\'".concat(str).concat("\'");
    }
}
