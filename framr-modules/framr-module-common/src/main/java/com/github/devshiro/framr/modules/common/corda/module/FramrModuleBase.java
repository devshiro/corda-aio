package com.github.devshiro.framr.modules.common.corda.module;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FramrModuleBase {

    @Getter
    private final String TYPE;

    public Class<? extends FramrModuleBase> getModuleClass() {
        return this.getClass();
    }
}
