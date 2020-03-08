package com.github.devshiro.framr.app.properties;

import com.github.devshiro.framr.core.configuration.FramrClassCollectorConfiguration;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("framr")
@Data
public class FramrAppProperties {
    @NestedConfigurationProperty
    private FramrClassCollectorConfiguration classCollector;
}
