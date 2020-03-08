package com.github.devshiro.framr.core.repository;

import java.util.ArrayList;
import java.util.List;

public class RepositorySupplier {

    private static RepositorySupplier _instance;

    private List<CordaStateRepository> stateRepositories;

    public static RepositorySupplier getInstance() {
        if (_instance == null) {
            throw new IllegalStateException("RepositorySupplier not initialized yet, forgot to call init?");
        } else {
            return _instance;
        }
    }

    public static RepositorySupplier init() {
        _instance = new RepositorySupplier();
        return _instance;
    }

    private RepositorySupplier() {
        stateRepositories = new ArrayList<>();
    }

    public CordaStateRepository register(CordaStateRepository repository) {
        stateRepositories.add(repository);
        return repository;
    }

    @SuppressWarnings({"unchecked"})
    public <T> CordaStateRepository<T, ?> get(Class<T> stateClass) {
        return stateRepositories.stream()
                .filter(repository -> stateClass.equals(repository.getStateClass()))
                .findFirst().orElseThrow(RuntimeException::new);
    }


}
