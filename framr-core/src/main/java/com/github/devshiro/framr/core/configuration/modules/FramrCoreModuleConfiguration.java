package com.github.devshiro.framr.core.configuration.modules;

import com.github.devshiro.framr.core.configuration.FramrApplicationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FramrApplicationProperties.class})
public class FramrCoreModuleConfiguration {
}
