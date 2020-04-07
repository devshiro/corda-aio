package com.github.devshiro.framr.modules.cordapp.exception;

public class MissingCordappException extends Exception {
    public MissingCordappException(Object id) {
        super("The requested cordapp [" + id.toString() + "] cannot be found in the registry or cannot be loaded");
    }
}
