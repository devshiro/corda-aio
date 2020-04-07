package com.github.devshiro.framr.modules.common.corda.component;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Contract extends CordappComponentBase {

    @Builder
    public Contract(Class<?> klass) {
        super(klass);
    }
}
