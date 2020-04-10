package com.github.devshiro.framr.core.configuration.modules.cordapp;

import com.github.devshiro.framr.modules.cordapp.registry.CordappRegistry;
import lombok.Data;

@Data
public class CordappModuleProperties {
    private CordappRegistry.Type cordappRegistryType;
}
