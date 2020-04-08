package com.github.devshiro.framr.modules.nodedb.hibernate;

import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;

import java.util.*;

public class HibernateSessionManager {

    private static volatile HibernateSessionManager _instance;

    public static synchronized HibernateSessionManager getInstance() {
        if (_instance == null) {
            _instance = new HibernateSessionManager();
        }
        return _instance;
    }

    private final Map<UUID, HibernateSession> sessions;

    private HibernateSessionManager() {
        sessions = new HashMap<>();
    }

    public HibernateSession openSession(NodeConfiguration configuration) {
        Objects.requireNonNull(configuration, "Provided NodeConfiguration should not be null");
        Optional<HibernateSession> existingSession = sessions.values().stream()
                .filter(session -> session.getConfiguration().equals(configuration))
                .findFirst();
        if (existingSession.isPresent()) {
            return existingSession.get();
        }
        UUID uuid = UUID.randomUUID();
        HibernateSession session = new HibernateSession(uuid, configuration);
        sessions.put(uuid, session);
        return session;
    }

    public Optional<HibernateSession> findSession(UUID id) {
        return Optional.ofNullable(sessions.get(id));
    }

    public void closeSession(UUID id) {
        findSession(id).ifPresent(HibernateSession::closeSession);
    }

    public void removeSession(UUID id) {
        Optional<HibernateSession> session = findSession(id);
        if (session.isPresent()) {
            session.get().closeSession();
            sessions.remove(id);
        }
    }

    public List<HibernateSession> getAllSessions() {
        return null;
    }
}
