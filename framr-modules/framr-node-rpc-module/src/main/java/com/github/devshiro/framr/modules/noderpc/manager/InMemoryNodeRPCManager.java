package com.github.devshiro.framr.modules.noderpc.manager;

import com.github.devshiro.framr.modules.noderpc.connection.NodeRPC;
import net.corda.core.messaging.CordaRPCOps;

import java.util.*;

public class InMemoryNodeRPCManager implements NodeRPCManager {

    private static volatile InMemoryNodeRPCManager _instance;

    public static synchronized InMemoryNodeRPCManager getInstance() {
        if (_instance == null) {
            _instance = new InMemoryNodeRPCManager();
        }
        return _instance;
    }

    private final Map<UUID, NodeRPC> rpcs;

    private InMemoryNodeRPCManager() {
        rpcs = new HashMap<>();
    }

    @Override
    public UUID registerNode(String host, int port) {
        Objects.requireNonNull(host, "Host cannot be null");
        Optional<NodeRPC> existingNodeRPC = rpcs.values().stream()
                .filter(node -> node.getHost().equals(host) && node.getPort() == port)
                .findFirst();
        if (existingNodeRPC.isPresent()) {
            return existingNodeRPC.get().getId();
        }
        UUID rpcId = UUID.randomUUID();
        NodeRPC nodeRPC = new NodeRPC(rpcId, host, port);
        rpcs.put(rpcId, nodeRPC);
        return rpcId;
    }

    @Override
    public Optional<NodeRPC> getNode(UUID id) {
        return Optional.ofNullable(rpcs.get(id));
    }

    @Override
    public CordaRPCOps start(UUID id, String username, String password) {
        Optional<NodeRPC> nodeRPC = getNode(id);
        if (!nodeRPC.isPresent()) {
            throw new IllegalArgumentException("Node with provided id [" + id + "] does not exist");
        }

        nodeRPC.get().connect(username, password);
        return nodeRPC.get().getOps().orElseThrow(() -> new IllegalStateException("Connection cannot be initialized. Check provided information"));
    }

    @Override
    public void deleteNode(UUID id) {
        Optional<NodeRPC> nodeRPC = getNode(id);
        if (nodeRPC.isPresent()) {
            nodeRPC.get().close();
            rpcs.remove(id);
        }
    }
}
