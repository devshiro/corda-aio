package com.github.devshiro.framr.core.configuration;

import com.github.devshiro.framr.core.configuration.modules.cordapp.CordappModuleProperties;
import com.github.devshiro.framr.core.configuration.modules.nodedb.NodeDBModuleProperties;
import com.github.devshiro.framr.core.configuration.modules.noderpc.NodeRPCModuleProperties;
import com.github.devshiro.framr.core.ui.component.NewNodeLayout;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "framr")
public class FramrApplicationProperties {

    private List<String> cordapps;

    private List<NewNodeLayout.NodeConnectionDetails> initConnections;

    @NestedConfigurationProperty
    private CordappModuleProperties cordappModule;

    @NestedConfigurationProperty
    private NodeRPCModuleProperties nodeRPCModule;

    @NestedConfigurationProperty
    private NodeDBModuleProperties nodeDBModule;
}
