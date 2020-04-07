package com.github.devshiro.framr.modules.common.corda.cordapp;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.State;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ContractCordapp extends CordappBase {

    private final List<Entity> entities;
    private final List<State> states;
    private final List<Contract> contracts;

    @Builder
    public ContractCordapp(String vendor, String name, String jarName, String version, List<Entity> entities, List<State> states, List<Contract> contracts) {
        super(vendor, name, jarName, version, CordappType.CONTRACT);
        this.entities = entities;
        this.states = states;
        this.contracts = contracts;
    }

    @Override
    public Cordapp setReference() {
        entities.forEach(entity -> entity.setCordapp(this));
        states.forEach(state -> state.setCordapp(this));
        contracts.forEach(contract -> contract.setCordapp(this));
        return this;
    }
}
