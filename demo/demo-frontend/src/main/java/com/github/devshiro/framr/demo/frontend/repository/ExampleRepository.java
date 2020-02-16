package com.github.devshiro.framr.demo.frontend.repository;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;

import java.util.List;

public interface ExampleRepository {
    List<ExampleEntity> findAll();
}