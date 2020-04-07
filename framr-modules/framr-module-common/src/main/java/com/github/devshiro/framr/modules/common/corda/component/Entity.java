package com.github.devshiro.framr.modules.common.corda.component;

import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Entity extends CordappComponentBase {

    @Builder
    public Entity(Class<?> klass) {
        super(klass);
    }
}
