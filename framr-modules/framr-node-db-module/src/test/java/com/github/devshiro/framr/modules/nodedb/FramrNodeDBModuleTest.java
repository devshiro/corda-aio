package com.github.devshiro.framr.modules.nodedb;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.github.devshiro.framr.modules.nodedb.hibernate.HibernateSession;
import com.github.devshiro.framr.modules.nodedb.registration.NodeRegistration;
import com.google.common.collect.ImmutableList;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.util.List;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FramrNodeDBModuleTest implements WithAssertions {

    @Test
    public void testModule() {
        FramrNodeDBModule module = FramrNodeDBModule.getInstance();

        File persistenceFile = new File(getClass().getClassLoader().getResource("persistence.mv.db").getFile());
        String db = persistenceFile.getAbsolutePath().substring(0, persistenceFile.getAbsolutePath().length() - 6);

        String url = "jdbc:h2:"+db;
        String user = "sa";
        String password = "";
        NodeRegistration registration = NodeRegistration.builder()
                .friendlyName("File DB")
                .url(url)
                .dialect("org.hibernate.dialect.H2Dialect")
                .driverClass(org.h2.Driver.class)
                .entityClasses(ImmutableList.of(ExampleEntity.class))
                .username(user)
                .password(password)
                .build();

        UUID id = module.registerNode(registration);
        assertThat(id).isNotNull();

        HibernateSession session = module.getSession(id);
        assertThat(session).isNotNull();
        List<ExampleEntity> entities = session.findAll(ExampleEntity.class);
        assertThat(entities).isNotEmpty();
    }

}
