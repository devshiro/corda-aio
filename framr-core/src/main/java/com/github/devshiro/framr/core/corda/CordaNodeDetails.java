package com.github.devshiro.framr.core.corda;

import lombok.Data;

@Data
public class CordaNodeDetails {
    private String rpcHost;
    private int rpcPort;
    private String rpcUsername;
    private String rpcPassword;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
}
