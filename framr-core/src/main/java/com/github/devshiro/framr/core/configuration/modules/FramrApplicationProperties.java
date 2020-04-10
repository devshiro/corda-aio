package com.github.devshiro.framr.core.configuration.modules;

import com.github.devshiro.framr.core.configuration.modules.cordapp.CordappModuleProperties;
import com.github.devshiro.framr.core.configuration.modules.nodedb.NodeDBModuleProperties;
import com.github.devshiro.framr.core.configuration.modules.noderpc.NodeRPCModuleProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties(prefix = "framr")
public class FramrApplicationProperties {

    @NestedConfigurationProperty
    private CordappModuleProperties cordappModule;

    @NestedConfigurationProperty
    private NodeRPCModuleProperties nodeRPCModule;

    @NestedConfigurationProperty
    private NodeDBModuleProperties nodeDBModule;
}
