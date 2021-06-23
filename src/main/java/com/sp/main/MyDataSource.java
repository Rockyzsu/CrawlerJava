package com.sp.main;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author Rocky
 */
public class MyDataSource {
    public static DataSource getDataSource(String connectURI){
        BasicDataSource ds = new BasicDataSource();
         //MySQL��jdbc����
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        //��Ҫ���ӵ����ݿ���
        ds.setUsername("root");
        //MySQL�ĵ�½����
        ds.setPassword("root");
        ds.setUrl(connectURI);
        return ds;
    }
}