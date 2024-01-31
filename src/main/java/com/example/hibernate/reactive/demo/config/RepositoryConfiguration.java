package com.example.hibernate.reactive.demo.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public EntityManagerFactory createEntityManager() {
        return Persistence.createEntityManagerFactory("taskDemo");
    }

    @Bean
    public Mutiny.SessionFactory createSessionFactory(@Autowired EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(Mutiny.SessionFactory.class);
    }
}
