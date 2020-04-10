package com.github.devshiro.framr.modules.cordapp.registry;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.cordapp.exception.MissingCordappException;

import java.util.List;

public interface CordappRegistry {

    public enum Type {
        INMEMORY
    }

    static CordappRegistry getInstance(Type type) {
        if (Type.INMEMORY.equals(type)) {
            return InMemoryCordappRegistry.getInstance();
        } else {
            throw new IllegalArgumentException("Invalid ConfigurationManager type: " + type);
        }
    }

    List<Cordapp> getAllCordapps();
    List<Flow> getAllFlows();
    List<Entity> getAllEntities();
    List<State> getAllStates();
    List<Contract> getAllContracts();

    Cordapp findCordapp(String name) throws MissingCordappException;
}
