package com.wala.db.cus.mapper;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidManager {
	private DruidManager() {
	}

	private static DruidManager single = null;
	private DruidDataSource dataSource;

	public synchronized static DruidManager getInstance() {
		if (single == null) {
			single = new DruidManager();
			single.initPool();
		}
		return single;
	}

	private void initPool() {
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.sap.db.jdbc.Driver");
		dataSource.setUsername("SYSTEM");
		dataSource.setPassword("9Fy83kx4");
		dataSource.setUrl("jdbc:sap://35.229.155.64:39013?databaseName=SYSTEMDB&currentschema=LINEBOT&reconnect=true");
		dataSource.setMaxWait(3000);
		dataSource.setInitialSize(0);
		dataSource.setMaxIdle(30);
		dataSource.setMinIdle(30);
		dataSource.setMaxActive(60);
		dataSource.setValidationQuery("select * from dummy");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTimeBetweenEvictionRunsMillis(30000);
		dataSource.setMinEvictableIdleTimeMillis(1800000);
		dataSource.setNumTestsPerEvictionRun(10);
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeout(60);
		dataSource.setLogAbandoned(true);
		
		// 启用监控统计功能
		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		} // for mysql
		dataSource.setPoolPreparedStatements(false);
	}

	// 要考虑多线程的情况
	public Connection getConnection() {
		Connection connection = null;
		try {
			synchronized (dataSource) {
				connection = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}
