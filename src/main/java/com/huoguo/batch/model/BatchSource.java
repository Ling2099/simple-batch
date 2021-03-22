package com.huoguo.batch.model;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * 数据源
 *
 * @author Lizhenghuang
 */
@Deprecated
public class BatchSource implements Serializable {

    private static final long serialVersionUID = -624067638158861209L;

    /** 数据源 **/
    private DataSource dataSource;

    /** 连接地址 **/
    private String url;

    /** 用户名 **/
    private String usr;

    /** 密码 **/
    private String password;

    /** 驱动 **/
    private String driver;

    public BatchSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public BatchSource(String url, String usr, String password, String driver) {
        this.url = url;
        this.usr = usr;
        this.password = password;
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsr() {
        return usr;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
