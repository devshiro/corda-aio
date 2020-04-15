package com.github.devshiro.framr.modules.cordapp.loader;

import com.github.devshiro.framr.modules.common.corda.cordapp.Cordapp;

public interface Loader {

    Cordapp loadFromClasspath(String cordappName);
}
