package com.github.devshiro.framr.modules.nodedb;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NodeConfigurationTest implements WithAssertions {

    @Test
    public void testNodeConfiguration() {
        NodeConfiguration testConfiguration = NodeConfiguration.builder()
                .driverClass(org.h2.Driver.class)
                .dialect("org.hibernate.dialect.H2Dialect")
                .entityClasses(ImmutableList.of(ExampleEntity.class))
                .url("jdbc:h2:persistence")
                .username("sa")
                .password("")
                .build();

        assertThat(testConfiguration.getDriverClass().getCanonicalName()).isEqualTo("org.h2.Driver");
    }
}
