package com.github.devshiro.framr.modules.nodedb;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSession;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSessionManager;
import com.google.common.collect.ImmutableList;
import net.corda.nodeapi.internal.persistence.HibernateSchemaChangeException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateSessionManagerTest implements WithAssertions {

    private HibernateSessionManager manager;
    private HibernateSession session;
    private UUID sessionId;

    @BeforeAll
    public void setup() {
        NodeConfiguration configuration = NodeConfiguration.builder()
                .driverClass("org.h2.Driver.class")
                .dialect("org.hibernate.dialect.H2Dialect")
                .url("jdbc:h2:C:/Users/xmark/Documents/Projects/framr/framr-modules/framr-node-db-module/src/test/resources/persistence")
                .username("sa")
                .password("")
                .entityClasses(ImmutableList.of(ExampleEntity.class))
                .build();

        manager = HibernateSessionManager.getInstance();
        session = manager.openSession(configuration);
        assertThat(session).isNotNull();
        sessionId = session.getId();
    }

    @Test
    public void testSessionReopen() {
        session.closeSession();
        assertThat(session.findAll(ExampleEntity.class)).isNotEmpty();
    }

    @Test
    public void testGetExistingSession() {
        Optional<HibernateSession> fetchedSession = manager.findSession(sessionId);
        assertThat(fetchedSession).isNotEmpty();
        assertThat(fetchedSession.get()).isEqualTo(session);
    }

    @Test
    public void testNonExistingSession() {
        UUID nonExistingId = UUID.randomUUID();
        while (nonExistingId.equals(sessionId)) {
            nonExistingId = UUID.randomUUID();
        }
        assertThat(manager.findSession(nonExistingId)).isEmpty();
    }
}
