package com.github.devshiro.framr.core.corda;

import lombok.Data;

@Data
public class CordaNodeDetails {
    private String host;
    private int port;
    private String username;
    private String password;
}
