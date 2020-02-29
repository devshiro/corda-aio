package com.github.devshiro.framr.core.corda;

import lombok.extern.slf4j.Slf4j;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.utilities.NetworkHostAndPort;

@Slf4j
public class CordaConnector {

    private final CordaRPCClient client;

    private CordaRPCConnection connection;

    public CordaConnector(CordaNodeDetails nodeDetails) {
        NetworkHostAndPort hostAndPort = new NetworkHostAndPort(nodeDetails.getHost(), nodeDetails.getPort());
        client = new CordaRPCClient(hostAndPort);
        init(nodeDetails.getUsername(), nodeDetails.getPassword());
    }

    public CordaConnector(String host, int port) {
        NetworkHostAndPort hostAndPort = new NetworkHostAndPort(host, port);
        client = new CordaRPCClient(hostAndPort);
    }

    public void init(String username, String password) {
        if (connection == null) {
            connection = client.start(username, password);
            log.debug("Connection initialized successfully");
        } else {
            log.warn("Already initialized connection");
        }
    }

    public CordaRPCConnection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            throw new IllegalStateException("Connection not initialized yet. Forgot to call init()?");
        }
    }
}
