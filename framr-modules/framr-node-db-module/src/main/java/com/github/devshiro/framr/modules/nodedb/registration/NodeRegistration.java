package com.github.devshiro.framr.modules.nodedb.registration;

import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;
import lombok.Builder;
import lombok.Getter;

import java.sql.Driver;
import java.util.List;

@Getter
@Builder
public class NodeRegistration {
    private final String friendlyName;
    private final Class<? extends Driver> driverClass;
    private final String url;
    private final String username;
    private final String password;
    private final String dialect;
    private final List<Class<?>> entityClasses;


    public NodeConfiguration toConfiguration() {
        return NodeConfiguration.builder()
                .dialect(getDialect())
                .driverClass(getDriverClass())
                .entityClasses(getEntityClasses())
                .password(getPassword())
                .url(getUrl())
                .username(getUsername())
                .build();
    }
}
