package com.github.devshiro.framr.demo.frontend.repository;

import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

//@Component
public class ExampleRepositoryImpl implements ExampleRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ExampleEntity> findAll() {
        return entityManager.createQuery("SELECT e FROM ExampleEntity e").getResultList();
    }
}
