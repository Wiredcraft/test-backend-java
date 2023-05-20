package com.test.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.util.DriverDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author zhangrucheng on 2023/5/20
 */
public class DruidDataSourceFactory extends UnpooledDataSourceFactory {

    @Override
    public DataSource getDataSource() {
        try {
            ((DruidDataSource) this.dataSource).init();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        return this.dataSource;
    }

    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }
}
