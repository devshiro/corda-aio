package com.github.devshiro.framr.modules.nodedb.hibernate;

import com.github.devshiro.framr.modules.nodedb.configuration.NodeConfiguration;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;


public class HibernateSession {
    @Getter
    private final UUID id;
    @Getter
    private final NodeConfiguration configuration;

    private final Configuration hibernateCfg;

    private SessionFactory sessionFactory;
    private Session session;
    private CriteriaBuilder criteriaBuilder;

    @Builder
    public HibernateSession(UUID id, NodeConfiguration configuration) {
        this.id = id;
        this.configuration = configuration;
        hibernateCfg = buildHibernateCfg(configuration);
        sessionFactory = hibernateCfg.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        checkSession();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);
        return session.createQuery(query).getResultList();
    }

    public void closeSession() {
        if (session.isOpen()) {
            session.close();
        }
    }

    private void checkSession() {
        if (session == null) {
            session = sessionFactory.openSession();
            criteriaBuilder = session.getCriteriaBuilder();
        }
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
            criteriaBuilder = session.getCriteriaBuilder();
        }
        if (criteriaBuilder == null) {
            criteriaBuilder = session.getCriteriaBuilder();
        }
    }

    private Configuration buildHibernateCfg(NodeConfiguration configuration) {
        Configuration config = new Configuration();
        config.setProperty("hibernate.connection.driver_class", configuration.getDriverClass().getCanonicalName());
        config.setProperty("hibernate.connection.url", configuration.getUrl());
        config.setProperty("hibernate.connection.username", configuration.getUsername());
        config.setProperty("hibernate.connection.password", configuration.getPassword());
        config.setProperty("hibernate.dialect", configuration.getDialect());
        configuration.getEntityClasses().forEach(config::addAnnotatedClass);
        return config;
    }
}
