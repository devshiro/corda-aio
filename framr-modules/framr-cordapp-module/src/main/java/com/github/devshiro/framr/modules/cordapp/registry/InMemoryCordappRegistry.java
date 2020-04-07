package com.github.devshiro.framr.modules.cordapp.registry;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import com.github.devshiro.framr.modules.common.corda.cordapp.*;
import com.github.devshiro.framr.modules.cordapp.exception.MissingCordappException;
import com.github.devshiro.framr.modules.cordapp.loader.ClasspathCordappLoader;
import com.google.common.collect.ImmutableList;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryCordappRegistry implements CordappRegistry {

    private static volatile InMemoryCordappRegistry _instance;

    public static synchronized InMemoryCordappRegistry getInstance() {
        if (_instance == null) {
            _instance = new InMemoryCordappRegistry();
        }
        return _instance;
    }

    private final Map<UUID, Cordapp> cordapps;
    private final ClasspathCordappLoader classpathCordappLoader;

    private InMemoryCordappRegistry() {
        cordapps = new HashMap<>();
        classpathCordappLoader = ClasspathCordappLoader.getInstance();
    }


    @Override
    public List<Cordapp> getAllCordapps() {
        return ImmutableList.copyOf(cordapps.values());
    }

    @Override
    public List<Flow> getAllFlows() {
        return cordapps.values().stream()
                .filter(cordapp -> cordapp.getType().equals(CordappType.WORKFLOW) || cordapp.getType().equals(CordappType.MIXED))
                .map(cordapp -> {
                    if (cordapp.getType().equals(CordappType.WORKFLOW)) {
                        WorkflowCordapp workflowCordapp = (WorkflowCordapp) cordapp;
                        return workflowCordapp.getFlows();
                    } else {
                        MixedCordapp mixedCordapp = (MixedCordapp) cordapp;
                        return mixedCordapp.getFlows();
                    }
                })
                .flatMap(List::stream)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<Entity> getAllEntities() {
        return cordapps.values().stream()
                .filter(cordapp -> cordapp.getType().equals(CordappType.CONTRACT) || cordapp.getType().equals(CordappType.MIXED))
                .map(cordapp -> {
                    if (cordapp.getType().equals(CordappType.CONTRACT)) {
                        ContractCordapp contractCordapp = (ContractCordapp) cordapp;
                        return contractCordapp.getEntities();
                    } else {
                        MixedCordapp mixedCordapp = (MixedCordapp) cordapp;
                        return mixedCordapp.getEntities();
                    }
                })
                .flatMap(List::stream)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<State> getAllStates() {
        return cordapps.values().stream()
                .filter(cordapp -> cordapp.getType().equals(CordappType.CONTRACT) || cordapp.getType().equals(CordappType.MIXED))
                .map(cordapp -> {
                    if (cordapp.getType().equals(CordappType.CONTRACT)) {
                        ContractCordapp contractCordapp = (ContractCordapp) cordapp;
                        return contractCordapp.getStates();
                    } else {
                        MixedCordapp mixedCordapp = (MixedCordapp) cordapp;
                        return mixedCordapp.getStates();
                    }
                })
                .flatMap(List::stream)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<Contract> getAllContracts() {
        return cordapps.values().stream()
                .filter(cordapp -> cordapp.getType().equals(CordappType.CONTRACT) || cordapp.getType().equals(CordappType.MIXED))
                .map(cordapp -> {
                    if (cordapp.getType().equals(CordappType.CONTRACT)) {
                        ContractCordapp contractCordapp = (ContractCordapp) cordapp;
                        return contractCordapp.getContracts();
                    } else {
                        MixedCordapp mixedCordapp = (MixedCordapp) cordapp;
                        return mixedCordapp.getContracts();
                    }
                })
                .flatMap(List::stream)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Cordapp findCordapp(String name) throws MissingCordappException {
        Optional<Cordapp> maybeCordapp = cordapps.values().stream()
                .filter(cordapp -> cordapp.getJarName().matches(name))
                .findFirst();
        if (maybeCordapp.isPresent()) {
            return maybeCordapp.get();
        }

        // Try loading from classpath
        Cordapp cordapp = classpathCordappLoader.load(name);
        if (!cordapps.containsValue(cordapp)) {
            cordapps.put(UUID.randomUUID(), cordapp);
        }
        return cordapp;
    }
}
