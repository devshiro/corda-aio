package com.github.devshiro.framr.modules.nodedb.configuration;

import com.github.devshiro.framr.modules.nodedb.exception.InvalidTypeException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfigurationManager {

    public enum Type {
        INMEMORY
    }

    static ConfigurationManager getInstance(Type type) {
        if (Type.INMEMORY.equals(type)) {
            return InMemoryConfigurationManager.getInstance();
        } else {
            throw new InvalidTypeException("Invalid ConfigurationManager type: " + type);
        }
    }

    Optional<NodeConfiguration> get(UUID id);
    UUID store(NodeConfiguration configuration);
    void remove(UUID id);
    Optional<NodeConfiguration> findByUrl(String url);
    List<NodeConfiguration> findAll();
}
