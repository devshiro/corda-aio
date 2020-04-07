package com.github.devshiro.framr.modules.common.corda.cordapp;

import com.github.devshiro.framr.modules.common.corda.component.Contract;
import com.github.devshiro.framr.modules.common.corda.component.Entity;
import com.github.devshiro.framr.modules.common.corda.component.Flow;
import com.github.devshiro.framr.modules.common.corda.component.State;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MixedCordapp extends CordappBase {

    private final List<Flow> flows;
    private final List<Entity> entities;
    private final List<State> states;
    private final List<Contract> contracts;

    @Builder
    public MixedCordapp(String vendor, String name, String jarName, String version, List<Flow> flows, List<Entity> entities, List<State> states, List<Contract> contracts) {
        super(vendor, name, jarName, version, CordappType.MIXED);
        this.flows = flows;
        this.entities = entities;
        this.states = states;
        this.contracts = contracts;
    }

    @Override
    public Cordapp setReference() {
        flows.forEach(flow -> flow.setCordapp(this));
        entities.forEach(entity -> entity.setCordapp(this));
        states.forEach(state -> state.setCordapp(this));
        contracts.forEach(contract -> contract.setCordapp(this));
        return this;
    }
}
