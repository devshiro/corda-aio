package com.github.devshiro.framr.modules.nodedb;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSession;
import com.google.common.collect.ImmutableList;
import net.corda.nodeapi.internal.persistence.HibernateSchemaChangeException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HibernateSessionTest implements WithAssertions {
    @Test
    public void testHibernateSessionCanFetchData() {
        NodeConfiguration configuration = NodeConfiguration.builder()
                .driverClass(org.h2.Driver.class)
                .dialect("org.hibernate.dialect.H2Dialect")
                .url("jdbc:h2:C:/Users/xmark/Documents/Projects/framr/framr-modules/framr-node-db-module/src/test/resources/persistence")
                .username("sa")
                .password("")
                .entityClasses(ImmutableList.of(ExampleEntity.class))
                .build();

        HibernateSession session = new HibernateSession(UUID.randomUUID(), configuration);
        assertThat(session).isNotNull();
        List<ExampleEntity> entities = session.findAll(ExampleEntity.class);
        assertThat(entities).isNotEmpty();
    }
}
