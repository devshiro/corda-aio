package com.github.devshiro.framr.core.manager;

import com.github.devshiro.framr.core.ui.tab.NewNodeLayout;
import com.github.devshiro.framr.modules.common.corda.component.*;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.cordapp.FramrCordappModule;
import com.github.devshiro.framr.modules.nodedb.FramrNodeDBModule;
import com.github.devshiro.framr.modules.nodedb.registration.NodeRegistration;
import com.github.devshiro.framr.modules.noderpc.FramrNodeRPCModule;
import com.vaadin.ui.Notification;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NodeManager {

    @Autowired
    private FramrCordappModule cordappModule;

    @Autowired
    private FramrNodeDBModule nodeDBModule;

    @Autowired
    private FramrNodeRPCModule nodeRPCModule;

    @Getter
    private Set<NodeManagerProxy> nodeManagerProxies = new HashSet<>();

    public List<Cordapp> getCordapps() {
        return cordappModule.getCordapps();
    }

    public List<Flow> getFlows() {
        return cordappModule.getFlows();
    }

    public List<Entity> getEntities() {
        return cordappModule.getEntities();
    }

    public List<State> getStates() {
        return cordappModule.getStates();
    }

    public List<Contract> getContracts() {
        return cordappModule.getContracts();
    }

    public Optional<NodeManagerProxy> register(NewNodeLayout.NodeConnectionDetails nodeConnectionDetails) {
        log.info("Connection requested with: " + nodeConnectionDetails.toString());
        UUID rpcId = null;
        UUID dbId = null;
        try {
            rpcId = nodeRPCModule.registerNode(nodeConnectionDetails.getRpcHost(), nodeConnectionDetails.getRpcPort());
        } catch (Exception e) {
            log.error("Cannot connect to node: {}", e.getMessage());
            Notification.show("Cannot connect to node", Notification.Type.ERROR_MESSAGE);
            return Optional.empty();
        }
        try {
            nodeRPCModule.connect(rpcId, nodeConnectionDetails.getRpcUsername(), nodeConnectionDetails.getRpcPassword());
            dbId = nodeDBModule.registerNode(NodeRegistration.builder()
                    .dialect(nodeConnectionDetails.getDialect())
                    .driverClass(nodeConnectionDetails.getDbDriver())
                    .entityClasses(cordappModule.getEntities().stream().map(CordappComponentBase::getKlass).collect(Collectors.toList()))
                    .friendlyName(nodeConnectionDetails.getRpcHost() + ":" + nodeConnectionDetails.getRpcHost())
                    .url(nodeConnectionDetails.getDbUrl())
                    .username(nodeConnectionDetails.getDbUsername())
                    .password(nodeConnectionDetails.getDbPassword())
                    .build());
        } catch (Exception e) {
            nodeRPCModule.deleteNode(rpcId);
            log.error("Cannot register node: {}", e.getMessage());
            Notification.show("Cannot register node", Notification.Type.ERROR_MESSAGE);
            return Optional.empty();
        }
        NodeManagerProxy proxy = NodeManagerProxy.builder()
                .cordappModule(cordappModule)
                .nodeDBModule(nodeDBModule)
                .nodeRPCModule(nodeRPCModule)
                .nodeDbId(dbId)
                .nodeRpcId(rpcId)
                .nodeName(nodeConnectionDetails.getRpcHost())
                .build();
        nodeManagerProxies.add(proxy);
        return Optional.of(proxy);
    }

}
