package com.github.devshiro.framr.modules.noderpc.manager;

import com.github.devshiro.framr.modules.noderpc.connection.NodeRPC;
import net.corda.core.messaging.CordaRPCOps;

import java.util.Optional;
import java.util.UUID;

public interface NodeRPCManager {

    public enum Type {
        INMEMORY
    }

    static NodeRPCManager getInstance(Type type) {
        if (Type.INMEMORY.equals(type)) {
            return InMemoryNodeRPCManager.getInstance();
        } else {
            throw new IllegalArgumentException("Invalid ConfigurationManager type: " + type);
        }
    }

    CordaRPCOps start(UUID id, String username, String password);
    Optional<NodeRPC> getNode(UUID id);
    UUID registerNode(String host, int port);
    void deleteNode(UUID id);
}
