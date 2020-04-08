package com.github.devshiro.framr.modules.nodedb.configuration;

import com.google.common.collect.ImmutableList;

import java.util.*;

public class InMemoryConfigurationManager implements ConfigurationManager {

    private static volatile InMemoryConfigurationManager _instance;

    public static synchronized InMemoryConfigurationManager getInstance() {
        if (_instance == null) {
            _instance = new InMemoryConfigurationManager();
        }
        return _instance;
    }

    private final Map<UUID, NodeConfiguration> configurations;

    private InMemoryConfigurationManager() {
        configurations = new HashMap<>();
    }

    @Override
    public Optional<NodeConfiguration> get(UUID id) {
        return Optional.ofNullable(configurations.get(id));
    }

    @Override
    public UUID store(NodeConfiguration configuration) {
        if (configurations.containsValue(configuration)) {
            return configurations.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(configuration))
                    .findFirst()
                    .get()
                    .getKey();
        }
        UUID uuid = UUID.randomUUID();
        configurations.put(uuid, configuration);
        return uuid;
    }

    @Override
    public Optional<NodeConfiguration> findByUrl(String url) {
        return configurations.values()
                .stream()
                .filter(configuration -> configuration.getUrl().equals(url))
                .findFirst();
    }

    @Override
    public List<NodeConfiguration> findAll() {
        return ImmutableList.copyOf(configurations.values());
    }

    @Override
    public void remove(UUID id) {
        Optional<NodeConfiguration> configuration = get(id);
        if(configuration.isPresent()) {
            configurations.remove(id);
        }
    }
}
