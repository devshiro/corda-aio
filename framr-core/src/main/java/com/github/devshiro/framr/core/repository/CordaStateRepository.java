package com.github.devshiro.framr.core.repository;

import java.util.List;

public abstract class CordaStateRepository<T, I> {

    public abstract Class<T> getStateClass();

    public abstract List<T> findAll();

    public abstract T findOne(I id);
}
