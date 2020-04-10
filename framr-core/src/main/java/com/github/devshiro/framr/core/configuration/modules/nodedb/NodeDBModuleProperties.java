package com.github.devshiro.framr.core.configuration.modules.nodedb;

import com.github.devshiro.framr.modules.nodedb.configuration.ConfigurationManager;
import lombok.Data;

@Data
public class NodeDBModuleProperties {
    private ConfigurationManager.Type dbConfigurationManagerType;
}
