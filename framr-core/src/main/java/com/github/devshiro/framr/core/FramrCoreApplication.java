package com.github.devshiro.framr.core;

import com.github.devshiro.framr.core.configuration.FramrCoreApplicationConfiguration;
import com.github.devshiro.framr.core.configuration.modules.FramrCoreModuleConfiguration;
import com.github.devshiro.framr.modules.cordapp.FramrCordappModule;
import com.github.devshiro.framr.modules.nodedb.FramrNodeDBModule;
import com.github.devshiro.framr.modules.noderpc.FramrNodeRPCModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
@Import({FramrCoreApplicationConfiguration.class, FramrCoreModuleConfiguration.class})
public class FramrCoreApplication implements CommandLineRunner {

    @Autowired
    private FramrNodeRPCModule nodeRPCModule;

    @Autowired
    private FramrCordappModule cordappModule;

    @Autowired
    private FramrNodeDBModule nodeDBModule;

    public static void main(String[] args) {
        log.info("FramrCore starting...");
        SpringApplication.run(FramrCoreApplication.class, args);
        log.info("FramrCore shutdown.");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Executing CommandLineRunner...");
    }
}
