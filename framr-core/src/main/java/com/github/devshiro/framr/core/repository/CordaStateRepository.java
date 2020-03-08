package com.github.devshiro.framr.core.repository;

import java.util.List;

public interface CordaStateRepository<T, I> {

    Class<T> getStateClass();

    public List<T> findAll();

    public T findOne(I id);
}
