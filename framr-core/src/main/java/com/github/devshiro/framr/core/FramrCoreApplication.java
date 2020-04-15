package com.github.devshiro.framr.core;

import com.github.devshiro.framr.core.configuration.FramrApplicationProperties;
import com.github.devshiro.framr.core.configuration.FramrCoreApplicationConfiguration;
import com.github.devshiro.framr.core.configuration.modules.FramrCoreModuleConfiguration;
import com.github.devshiro.framr.core.manager.NodeManager;
import com.github.devshiro.framr.core.manager.NodeManagerProxy;
import com.github.devshiro.framr.core.ui.component.NewNodeLayout;
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

import java.io.IOException;
import java.util.Optional;

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

    @Autowired
    private FramrApplicationProperties properties;

    @Autowired
    private NodeManager nodeManager;


    public static void main(String[] args) throws IOException {
        log.info("FramrCore starting...");
        SpringApplication.run(FramrCoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Executing FramrCore Initizr...");
        for (String cordapp : properties.getCordapps()) {
            cordappModule.loadCordapp(cordapp);
            log.info("Loaded cordapp: {}", cordapp);
        }

        log.info("Preconnect...");
        for (NewNodeLayout.NodeConnectionDetails initConnection : properties.getInitConnections()) {
            Optional<NodeManagerProxy> proxy = nodeManager.register(initConnection);
            if(proxy.isPresent()) {
                log.info("Connection created with {}:{}", initConnection.getRpcHost(), initConnection.getRpcPort());
            } else {
                log.warn("Failed to connect to {}:{}", initConnection.getRpcHost(), initConnection.getRpcPort());
            }
        }
    }
}
