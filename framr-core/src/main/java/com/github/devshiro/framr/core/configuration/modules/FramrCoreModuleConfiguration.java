package com.github.devshiro.framr.core.configuration.modules;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FramrApplicationProperties.class})
public class FramrCoreModuleConfiguration {
}
