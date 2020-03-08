package com.github.devshiro.framr.app.configuration;

import com.github.devshiro.framr.app.properties.FramrAppProperties;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FramrAppConfiguration {

    @Bean
    FramrConfiguration framrConfiguration(FramrAppProperties appProperties) {
        FramrConfiguration framrConfiguration = new FramrConfiguration();
        framrConfiguration.setClassCollectorConfiguration(appProperties.getClassCollector());
        return framrConfiguration;
    }

}
