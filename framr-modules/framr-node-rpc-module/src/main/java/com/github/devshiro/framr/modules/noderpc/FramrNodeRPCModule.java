package com.github.devshiro.framr.modules.noderpc;

import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.module.FramrModuleBase;
import com.github.devshiro.framr.modules.noderpc.connection.NodeRPC;
import com.github.devshiro.framr.modules.noderpc.flow.FlowInvocation;
import com.github.devshiro.framr.modules.noderpc.manager.NodeRPCManager;
import net.corda.core.flows.FlowLogic;
import net.corda.core.messaging.CordaRPCOps;

import java.util.Optional;
import java.util.UUID;

public class FramrNodeRPCModule extends FramrModuleBase {

    public static final String TYPE_ID = "FRAMR_NODE_RPC_MODULE";

    private static volatile FramrNodeRPCModule _instance;

    public static synchronized FramrNodeRPCModule getInstance() {
        if (_instance == null) {
            _instance = new FramrNodeRPCModule();
        }
        return _instance;
    }

    private final NodeRPCManager nodeRPCManager;

    private FramrNodeRPCModule() {
        super(TYPE_ID);
        nodeRPCManager = NodeRPCManager.getInstance(NodeRPCManager.Type.INMEMORY);
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

    public void startFlow(UUID nodeId, FlowInvocation flowInvocation) {
        Optional<NodeRPC> nodeRPC = nodeRPCManager.getNode(nodeId);
        if (!nodeRPC.isPresent()) {
            throw new IllegalArgumentException("No node found with id " + nodeId);
        }
        Optional<CordaRPCOps> ops = nodeRPC.get().getOps();
        if (!ops.isPresent()) {
            throw new IllegalStateException("Connection to node " + nodeRPC.get().getHost() + nodeRPC.get().getPort() + " haven't been established yet");
        }
        Class<? extends FlowLogic<?>> flowClass = (Class<? extends FlowLogic<?>>) flowInvocation.getFlow().getKlass();
        ops.get().startFlowDynamic(flowClass, flowInvocation.getArg());
    }
}
