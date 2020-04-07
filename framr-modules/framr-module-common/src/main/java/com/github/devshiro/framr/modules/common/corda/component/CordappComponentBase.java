package com.github.devshiro.framr.modules.common.corda.component;

import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public abstract class CordappComponentBase {
    private final Class<?> klass;
    @Setter
    private Cordapp cordapp;
}
