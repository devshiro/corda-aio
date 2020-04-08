package com.github.devshiro.framr.modules.nodedb;

import com.github.devshiro.framr.modules.common.corda.module.FramrModuleBase;
import com.github.devshiro.framr.modules.nodedb.configuration.ConfigurationManager;
import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSession;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSessionManager;
import com.github.devshiro.framr.modules.nodedb.registration.NodeInfo;
import com.github.devshiro.framr.modules.nodedb.registration.NodeRegistration;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class FramrNodeDBModule extends FramrModuleBase {

    public static final String TYPE_ID = "FRAMR_NODE_DB_MODULE";

    private static volatile FramrNodeDBModule _instance;

    public static synchronized FramrNodeDBModule getInstance() {
        if (_instance == null) {
            _instance = new FramrNodeDBModule();
        }
        return _instance;
    }

    private final ConfigurationManager configurationManager;
    private final HibernateSessionManager sessionManager;

    private final Map<UUID, NodeInfo> registeredNodes;

    protected FramrNodeDBModule() {
        super(TYPE_ID);
        configurationManager = ConfigurationManager.getInstance(ConfigurationManager.Type.INMEMORY);
        sessionManager = HibernateSessionManager.getInstance();
        registeredNodes = new HashMap<>();
    }

    public UUID registerNode(NodeRegistration registration) {
        Objects.requireNonNull(registration, "Supplied NodeRegistration must not be null");
        // CHeck if node already registered
        Optional<NodeInfo> existingNodeInfo = registeredNodes.values().stream()
                .filter(nodeInfo -> nodeInfo.getFriendlyName().equals(registration.getFriendlyName()))
                .findAny();
        if (existingNodeInfo.isPresent()) {
            return existingNodeInfo.get().getId();
        }
        NodeConfiguration nodeConfiguration = registration.toConfiguration();
        UUID configId = configurationManager.store(nodeConfiguration);
        HibernateSession session = sessionManager.openSession(configurationManager.get(configId).get());
        UUID sessionId = session.getId();
        UUID id = UUID.randomUUID();
        NodeInfo nodeInfo = new NodeInfo(id, registration.getFriendlyName(), sessionId, configId);
        registeredNodes.put(id, nodeInfo);
        return id;
    }

    public Optional<NodeInfo> getNodeInfo(UUID id) {
        return Optional.ofNullable(registeredNodes.get(id));
    }

    public List<NodeInfo> getAllNodeInfos() {
        return ImmutableList.copyOf(registeredNodes.values());
    }

    public void deleteNodeInfo(UUID id) {
        Optional<NodeInfo> nodeInfo = getNodeInfo(id);
        if (nodeInfo.isPresent()) {
            UUID configId = nodeInfo.get().getId();
            UUID sessionId = nodeInfo.get().getSessionId();
            sessionManager.closeSession(sessionId);
            sessionManager.removeSession(sessionId);
            configurationManager.remove(configId);
        }
    }

    public HibernateSession getSession(UUID nodeId) {
        Optional<NodeInfo> nodeInfo = getNodeInfo(nodeId);
        if (!nodeInfo.isPresent()) {
            throw new IllegalArgumentException("No registered node found with id " + nodeId);
        }

        UUID sessionId = nodeInfo.get().getSessionId();
        Optional<HibernateSession> session = sessionManager.findSession(sessionId);
        return session.orElseThrow(() -> new IllegalStateException("Missing session. Maybe session have been removed?"));
    }
}
