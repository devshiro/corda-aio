package com.github.devshiro.framr.core;

import com.github.devshiro.framr.core.corda.CordaConnector;
import com.github.devshiro.framr.core.corda.CordaNodeInformation;

public class FramrCoreDevMain {
    public static void main(String[] args) {
        CordaConnector connector = new CordaConnector("localhost", 10005);
        connector.init("user1","test");
        CordaNodeInformation nodeInformation = new CordaNodeInformation(connector.getConnection().getProxy());
        nodeInformation.getNetworkIdentities();
    }
}
