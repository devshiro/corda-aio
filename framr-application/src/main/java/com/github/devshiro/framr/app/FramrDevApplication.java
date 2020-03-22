package com.github.devshiro.framr.app;

import com.github.devshiro.framr.spring.configuration.FramrAppConfiguration;
import com.github.devshiro.framr.spring.properties.FramrAppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
@EnableConfigurationProperties({FramrAppProperties.class})
@Import({FramrAppConfiguration.class})
public class FramrDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(FramrDevApplication.class, args);
    }
}
