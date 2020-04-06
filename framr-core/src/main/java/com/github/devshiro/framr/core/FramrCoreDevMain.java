package com.github.devshiro.framr.core;

import com.github.devshiro.framr.core.corda.CordaConnector;
import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.corda.CordaNodeInformation;
import com.github.devshiro.framr.core.datasource.HibernateProvider;
import com.github.devshiro.framr.core.datasource.HikariDataSourceProvider;
import net.corda.core.messaging.CordaRPCOps;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FramrCoreDevMain {
    public static void main(String[] args) {
        CordaConnector connector = new CordaConnector("localhost", 10003);
        connector.init("user1","test");
        CordaRPCOps rpcOps = connector.getConnection().getProxy();
        CordaNodeInformation nodeInformation = new CordaNodeInformation(rpcOps);

        CordaNodeDetails nodeDetails = new CordaNodeDetails();
        nodeDetails.setRpcHost("localhost");
        nodeDetails.setRpcPort(10003);
        nodeDetails.setRpcUsername("user1");
        nodeDetails.setRpcPassword("test");
        nodeDetails.setJdbcUrl("jdbc:h2:tcp://localhost:11000/node");
        nodeDetails.setJdbcUsername("sa");
        nodeDetails.setJdbcPassword("");
        nodeInformation.getNetworkIdentities();
    }
}
