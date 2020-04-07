package com.github.devshiro.framr.modules.common.corda.component;

import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import lombok.Builder;
import lombok.Getter;

@Getter
public class State extends CordappComponentBase {

    @Builder
    public State(Class<?> klass) {
        super(klass);
    }
}
