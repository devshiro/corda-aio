package com.github.devshiro.framr.core.datasource;

import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HikariDataSourceProvider {

    private static Map<CordaNodeDetails, HikariDataSource> dataSourceMap = new HashMap<>();

    public static Connection getConnection(CordaNodeDetails nodeDetails) throws SQLException {
        return dataSourceMap.computeIfAbsent(nodeDetails, nodeDetails1 -> {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(nodeDetails.getJdbcUrl());
            config.setUsername(nodeDetails.getJdbcUsername());
            config.setPassword(nodeDetails.getJdbcPassword());
            return new HikariDataSource(config);
        }).getConnection();
    }
}
