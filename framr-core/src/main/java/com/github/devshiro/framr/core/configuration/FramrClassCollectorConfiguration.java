package com.github.devshiro.framr.core.configuration;

import lombok.Data;

@Data
public class FramrClassCollectorConfiguration {

    private boolean useAnnotation = true;

    private String cordappPackageName;

}
