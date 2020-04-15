package com.github.devshiro.framr.core.configuration;

import com.github.devshiro.framr.modules.cordapp.FramrCordappModule;
import com.github.devshiro.framr.modules.nodedb.FramrNodeDBModule;
import com.github.devshiro.framr.modules.noderpc.FramrNodeRPCModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FramrCoreApplicationConfiguration {

    @Bean
    public FramrCordappModule framrCordappModule(FramrApplicationProperties properties) {
        return FramrCordappModule.getInstance(properties.getCordappModule().getCordappRegistryType());
    }

    @Bean
    public FramrNodeDBModule framrNodeDBModule(FramrApplicationProperties properties) {
        return FramrNodeDBModule.getInstance(properties.getNodeDBModule().getDbConfigurationManagerType());
    }

    @Bean
    public FramrNodeRPCModule framrNodeRPCModule(FramrApplicationProperties properties) {
        return FramrNodeRPCModule.getInstance(properties.getNodeRPCModule().getRpcManagerType());
    }
}
