package com.github.devshiro.framr.modules.common.corda.cordapp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CordappBase implements Cordapp {
    private final String vendor;
    private final String name;
    private final String jarName;
    private final String version;
    private final CordappType type;
}
