package com.github.devshiro.framr.app;

import com.github.devshiro.framr.core.corda.CordaNodeDetails;
import com.github.devshiro.framr.core.datasource.HibernateProvider;
import com.github.devshiro.framr.demo.cordapp.schema.entity.ExampleEntity;
import com.github.devshiro.framr.spring.configuration.FramrAppConfiguration;
import com.github.devshiro.framr.spring.properties.FramrAppProperties;
import com.google.common.collect.ImmutableList;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
@EnableConfigurationProperties({FramrAppProperties.class})
@Import({FramrAppConfiguration.class})
public class FramrDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(FramrDevApplication.class, args);
//        CordaNodeDetails nodeDetails = new CordaNodeDetails();
//        nodeDetails.setRpcHost("localhost");
//        nodeDetails.setRpcPort(10003);
//        nodeDetails.setRpcUsername("user1");
//        nodeDetails.setRpcPassword("test");
//        nodeDetails.setJdbcUrl("jdbc:h2:tcp://localhost:11000/node");
//        nodeDetails.setJdbcUsername("sa");
//        nodeDetails.setJdbcPassword("");
//        Session session = HibernateProvider.get(nodeDetails, ImmutableList.of(ExampleEntity.class));
//        CriteriaBu ilder cb = session.getCriteriaBuilder();
//        CriteriaQuery cq = cb.createQuery(ExampleEntity.class);
//        Root root = cq.from(ExampleEntity.class);
//        cq.select(root);
//        List<ExampleEntity> entities = session.createQuery(cq).getResultList();
//        System.out.println("is it working?");
    }
}
