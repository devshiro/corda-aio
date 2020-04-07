package com.github.devshiro.framr.modules.common.corda.component;

import com.github.devshiro.framr.annotation.FramrStartableFlow;
import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import com.github.devshiro.framr.modules.common.corda.exception.ModuleRuntimeException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Flow extends CordappComponentBase {

    private final String displayName;
    private final Class<?> flowInputClass;

    @Builder
    public Flow(Class<?> klass) {
        super(klass);
        FramrStartableFlow flowAnnotation = klass.getAnnotation(FramrStartableFlow.class);
        if (flowAnnotation == null) {
            throw new ModuleRuntimeException("Provided class [" + klass + "] does not have @FramrStartableFlow annotation");
        }
        displayName = flowAnnotation.displayName();
        flowInputClass = flowAnnotation.inputClass();
    }
}
