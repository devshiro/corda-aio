package com.github.devshiro.framr.core.corda;

import lombok.Getter;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.node.NodeInfo;

import java.util.ArrayList;
import java.util.List;

public class CordaNodeInformation {

    @Getter
    private final List<Party> networkIdentities;

    public CordaNodeInformation(CordaRPCOps ops) {
        networkIdentities = ops.networkMapSnapshot().stream()
                .map(NodeInfo::getLegalIdentities)
                .reduce(new ArrayList<>(), (collected, parties) -> {
                    collected.addAll(parties);
                    return collected;
                });
    }
}
