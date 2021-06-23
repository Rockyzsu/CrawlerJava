package com.sp.main;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Rocky
 */
public class MyDataSource {
    public static DataSource getDataSource(String connectURI){
        BasicDataSource ds = new BasicDataSource();
         //MySQL的jdbc驱动
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        //所要连接的数据库名
        ds.setUsername("root");
        //MySQL的登陆密码
        ds.setPassword("root");
        ds.setUrl(connectURI);
        return ds;
    }
}