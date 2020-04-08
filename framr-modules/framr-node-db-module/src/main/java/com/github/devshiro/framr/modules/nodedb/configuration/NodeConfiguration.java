package com.github.devshiro.framr.modules.nodedb.configuration;

import lombok.Builder;
import lombok.Data;

import java.sql.Driver;
import java.util.List;

@Data
@Builder
public class NodeConfiguration {
    private Class<? extends Driver> driverClass;
    private String url;
    private String username;
    private String password;
    private String dialect;
    private List<Class<?>> entityClasses;
}
