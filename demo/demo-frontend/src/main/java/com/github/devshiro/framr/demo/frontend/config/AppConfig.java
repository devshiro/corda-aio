package com.github.devshiro.framr.demo.frontend.config;

import com.github.devshiro.framr.core.configuration.FramrClassCollectorConfiguration;
import com.github.devshiro.framr.core.configuration.FramrConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Configuration
public class AppConfig {

    @Bean
    FramrConfiguration configuration() {
        FramrConfiguration framrConfiguration = new FramrConfiguration();
        FramrClassCollectorConfiguration classCollectorConfiguration = new FramrClassCollectorConfiguration();
        classCollectorConfiguration.setUseAnnotation(false);
        classCollectorConfiguration.setCordappPackageName("com.github.devshiro.framr.demo");
        framrConfiguration.setClassCollectorConfiguration(classCollectorConfiguration);
        return framrConfiguration;
    }

//    @Bean
//    @Primary
//    EntityManager entityManager() {
//        return Persistence.createEntityManagerFactory("demo_corda")
//                .createEntityManager();
//    }
}
