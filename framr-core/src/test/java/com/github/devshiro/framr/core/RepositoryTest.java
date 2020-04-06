package com.github.devshiro.framr.core;

import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.repository.CordaStateRepository;
import com.github.devshiro.framr.core.repository.RepositorySupplier;
import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import lombok.Data;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTest implements WithAssertions {

    @Data
    private class ExampleState {
        private UUID id;
        private String firstName;
        private String lastName;
    }

    private class ExampleInMemoryRepository extends CordaStateRepository<ExampleState, UUID> {

        private List<ExampleState> states = new ArrayList<>();

        public void add(ExampleState state) {
            states.add(state);
        }

        @Override
        public Class<ExampleState> getStateClass() {
            return ExampleState.class;
        }

        @Override
        public List<ExampleState> findAll() {
            return states;
        }

        @Override
        public ExampleState findOne(UUID id) {
            return states.stream().filter(s -> s.getId().equals(id))
                    .findFirst().orElseThrow(RuntimeException::new);
        }
    }

    private RepositorySupplier repositorySupplier;

    @BeforeAll
    public void setup() {
        repositorySupplier = RepositorySupplier.init();
        ExampleInMemoryRepository repository = new ExampleInMemoryRepository();
        repositorySupplier.register(repository);
    }

    @Test
    public void repositorySupplierContainsRepository() {
        assertThat(RepositorySupplier.getInstance().get(ExampleState.class)).isNotNull();
    }

    @Test
    public void repositorySupplierGivenRepositoryCanBeUsed() {
        CordaStateRepository repository = RepositorySupplier.getInstance().get(ExampleState.class);
    }
}
