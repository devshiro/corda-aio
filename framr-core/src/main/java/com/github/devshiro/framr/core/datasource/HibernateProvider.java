package com.github.devshiro.framr.core.datasource;

import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateProvider {

    public static Session get(CordaNodeDetails nodeDetails, List<Class<?>> entityClasses) {
        Configuration config = new Configuration();
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", nodeDetails.getJdbcUrl());
        config.setProperty("hibernate.connection.username", nodeDetails.getJdbcUsername());
        config.setProperty("hibernate.connection.password", nodeDetails.getJdbcPassword());
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        for (Class entityClass : entityClasses) {
            config.addAnnotatedClass(entityClass);
        }

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
}
