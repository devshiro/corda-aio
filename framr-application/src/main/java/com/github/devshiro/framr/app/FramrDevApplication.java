package com.github.devshiro.framr.app;

import com.github.devshiro.framr.app.properties.FramrAppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FramrAppProperties.class})
public class FramrDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(FramrDevApplication.class, args);
    }
}
