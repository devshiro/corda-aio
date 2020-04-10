package com.github.devshiro.framr.modules.cordapp;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.common.corda.cordapp.CordappType;
import com.github.devshiro.framr.modules.common.corda.module.FramrModuleBase;
import com.github.devshiro.framr.modules.cordapp.exception.MissingCordappException;
import com.github.devshiro.framr.modules.cordapp.loader.ClasspathCordappLoader;
import com.github.devshiro.framr.modules.cordapp.registry.CordappRegistry;
import com.github.devshiro.framr.modules.cordapp.registry.InMemoryCordappRegistry;
import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class FramrCordappModule extends FramrModuleBase {

    public static final String TYPE_ID = "FRAMR_CORDAPP_MODULE";

    @Getter
    private final ClasspathCordappLoader classpathCordappLoader;

    private final CordappRegistry cordappRegistry;

    private static volatile FramrCordappModule _instance;

    public static synchronized FramrCordappModule getInstance(CordappRegistry.Type type) {
        if (_instance == null) {
            _instance = new FramrCordappModule(type);
        }
        return _instance;
    }

    public static synchronized FramrCordappModule getInstance() {
        if (_instance == null) {
            _instance = new FramrCordappModule(CordappRegistry.Type.INMEMORY);
        }
        return _instance;
    }

    private FramrCordappModule(CordappRegistry.Type type) {
        super(TYPE_ID);
        classpathCordappLoader = ClasspathCordappLoader.getInstance();
        cordappRegistry = CordappRegistry.getInstance(type);
    }

    public List<Flow> getFlows() {
        return cordappRegistry.getAllFlows();
    }

    public List<Entity> getEntities() {
        return cordappRegistry.getAllEntities();
    }

    public List<State> getStates() {
        return cordappRegistry.getAllStates();
    }

    public List<Contract> getContracts() {
        return cordappRegistry.getAllContracts();
    }

    public List<Cordapp> getCordapps() {
        return cordappRegistry.getAllCordapps();
    }

    public List<Cordapp> getCordappsByType(CordappType type) {
        return cordappRegistry.getAllCordapps().stream()
                .filter(cordapp -> cordapp.getType().equals(type))
                .collect(ImmutableList.toImmutableList());
    }

    public Cordapp loadCordapp(String name) throws MissingCordappException {
        return cordappRegistry.findCordapp(name);
    }
}
