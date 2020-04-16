package com.github.devshiro.framr.modules.noderpc;

import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.module.FramrModuleBase;
import com.github.devshiro.framr.modules.noderpc.connection.NodeRPC;
import com.github.devshiro.framr.modules.noderpc.flow.FlowInvocation;
import com.github.devshiro.framr.modules.noderpc.manager.NodeRPCManager;
import net.corda.core.flows.FlowLogic;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.messaging.FlowHandle;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FramrNodeRPCModule extends FramrModuleBase {

    public static final String TYPE_ID = "FRAMR_NODE_RPC_MODULE";

    private static volatile FramrNodeRPCModule _instance;

    public static synchronized FramrNodeRPCModule getInstance() {
        if (_instance == null) {
            _instance = new FramrNodeRPCModule(NodeRPCManager.Type.INMEMORY);
        }
        return _instance;
    }

    public static synchronized FramrNodeRPCModule getInstance(NodeRPCManager.Type type) {
        if (_instance == null) {
            _instance = new FramrNodeRPCModule(type);
        }
        return _instance;
    }

    private final NodeRPCManager nodeRPCManager;

    private FramrNodeRPCModule(NodeRPCManager.Type type) {
        super(TYPE_ID);
        nodeRPCManager = NodeRPCManager.getInstance(type);
    }

    public UUID registerNode(String host, int port) {
        return nodeRPCManager.registerNode(host, port);
    }

    public void connect(UUID nodeId, String username, String password) {
        Optional<NodeRPC> node = nodeRPCManager.getNode(nodeId);
        if (!node.isPresent()) {
            throw new IllegalArgumentException("Node with id " + nodeId + " not found.");
        }
        node.get().connect(username, password);
    }

    public void deleteNode(UUID nodeId) {
        nodeRPCManager.deleteNode(nodeId);
    }

    public CompletableFuture<?> startFlow(UUID nodeId, FlowInvocation flowInvocation) {
        Optional<NodeRPC> nodeRPC = nodeRPCManager.getNode(nodeId);
        if (!nodeRPC.isPresent()) {
            throw new IllegalArgumentException("No node found with id " + nodeId);
        }
        Optional<CordaRPCOps> ops = nodeRPC.get().getOps();
        if (!ops.isPresent()) {
            throw new IllegalStateException("Connection to node " + nodeRPC.get().getHost() + nodeRPC.get().getPort() + " haven't been established yet");
        }
        Class<? extends FlowLogic<?>> flowClass = (Class<? extends FlowLogic<?>>) flowInvocation.getFlow().getKlass();
        return ops.get().startFlowDynamic(flowClass, flowInvocation.getArg()).getReturnValue().toCompletableFuture();
    }
}
