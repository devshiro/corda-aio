package com.github.devshiro.framr.core.manager;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.cordapp.FramrCordappModule;
import com.github.devshiro.framr.modules.nodedb.FramrNodeDBModule;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSession;
import com.github.devshiro.framr.modules.noderpc.FramrNodeRPCModule;
import com.github.devshiro.framr.modules.noderpc.flow.FlowInvocation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Builder
@Slf4j
public class NodeManagerProxy {
    private final UUID nodeRpcId;
    private final UUID nodeDbId;
    private final FramrCordappModule cordappModule;
    private final FramrNodeDBModule nodeDBModule;
    private final FramrNodeRPCModule nodeRPCModule;
    @Getter
    @Setter
    private String nodeName;

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

    public void doSomething() {
        log.info("Do something triggered from proxy");
    }

    public List<?> getEntities(Class<?> entityClass) {
        HibernateSession session = nodeDBModule.getSession(nodeDbId);
        return session.findAll(entityClass);
    }

    public CompletableFuture<?> startFlow(Flow flow, Object flowInput) {
        FlowInvocation flowInvocation = new FlowInvocation(flow, flowInput);
        return nodeRPCModule.startFlow(nodeRpcId, flowInvocation);
    }

    public void close() {
       // do something about cleaning up shit
    }
}
