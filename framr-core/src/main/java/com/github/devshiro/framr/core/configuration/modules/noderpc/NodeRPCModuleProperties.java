package com.github.devshiro.framr.core.configuration.modules.noderpc;

import com.github.devshiro.framr.modules.noderpc.manager.NodeRPCManager;
import lombok.Data;

@Data
public class NodeRPCModuleProperties {
    private NodeRPCManager.Type rpcManagerType;
}
