package com.github.devshiro.framr.modules.noderpc.connection;

import lombok.Getter;
import lombok.NonNull;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.client.rpc.CordaRPCConnection;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;

import java.util.Optional;
import java.util.UUID;


public class NodeRPC {
    @Getter
    private final UUID id;
    @Getter
    private final String host;
    @Getter
    private final int port;


    private final NetworkHostAndPort networkHostAndPort;
    private final CordaRPCClient client;
    private CordaRPCOps ops;
    private CordaRPCConnection connection;

    public NodeRPC(@NonNull UUID id, @NonNull String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;

        networkHostAndPort = new NetworkHostAndPort(host, port);
        client = new CordaRPCClient(networkHostAndPort);
    }

    public void connect(String username, String password) {
        if (connection == null) {
            connection = client.start(username, password);
            ops = connection.getProxy();
        }
    }

    public void close() {
        if (connection != null) {
            connection.notifyServerAndClose();
            connection = null;
            ops = null;
        }
    }

    public Optional<CordaRPCOps> getOps() {
        return Optional.ofNullable(ops);
    }
}
