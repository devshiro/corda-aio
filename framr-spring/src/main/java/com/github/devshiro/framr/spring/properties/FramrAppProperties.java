package com.github.devshiro.framr.spring.properties;

import com.github.devshiro.framr.core.configuration.FramrClassCollectorConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("framr")
@Data
public class FramrAppProperties {
    @NestedConfigurationProperty
    private FramrClassCollectorConfiguration classCollector;
}
