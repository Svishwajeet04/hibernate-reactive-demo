package com.example.hibernate.reactive.demo.repository;

import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class Config {

  @Bean
  public EntityManagerFactory createEntityManager(){
    return Persistence.createEntityManagerFactory("taskDemo");
  }

  @Bean
  public Mutiny.SessionFactory createSessionFactory(@Autowired EntityManagerFactory entityManagerFactory){
    return entityManagerFactory.unwrap(Mutiny.SessionFactory.class);
  }
}
