package com.github.devshiro.framr.modules.nodedb.registration;

import lombok.Data;

import java.util.UUID;

@Data
public class NodeInfo {
    private final UUID id;
    private final String friendlyName;
    private final UUID sessionId;
    private final UUID configurationId;
}
