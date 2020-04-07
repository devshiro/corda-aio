package com.github.devshiro.framr.modules.common.corda.cordapp;

public interface Cordapp {
    String getVendor();
    String getName();
    String getJarName();
    String getVersion();
    CordappType getType();
    Cordapp setReference();
}
