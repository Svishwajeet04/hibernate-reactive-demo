package com.example.hibernate.reactive.demo.repository.impl;

import com.example.hibernate.reactive.demo.model.Task;
import com.example.hibernate.reactive.demo.model.User;
import com.example.hibernate.reactive.demo.repository.interfaces.TaskRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.ZonedDateTime;

@Service
@Slf4j
public class TaskRepoImpl implements TaskRepository {

  private final Mutiny.SessionFactory sessionFactory;

  public TaskRepoImpl(@Autowired Mutiny.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Mono<Task> createTask(Task task) {
    return sessionFactory.withSession(session ->
        {
          User k = task.getUser();
          return session
              .createQuery("from User where id = ?1", User.class)
              .setParameter(1, k.getId())
              .getSingleResult().onFailure().recoverWithNull()
            .onItem().ifNull().failWith(()-> new UserPrincipalNotFoundException(""))
            .onItem().ifNotNull()
            .transform(user -> {
              log.info("user {}" , user);
              task.setUser(user);
              task.setCreatedTime(ZonedDateTime.now());
              return task;
            })
            .chain(session::persist)
            .chain(session::flush)
            .replaceWith(task);
        }).convert().with(UniReactorConverters.toMono());
  }

  @Override
  public Flux<Task> getAllTasks() {
    return sessionFactory.withSession(session ->
        session.createQuery("from Task", Task.class).getResultList())
        .convert().with(UniReactorConverters.toMono()).flatMapMany((Flux::fromIterable));
  }

  @Override
  public Flux<Task> getAllTasksForUser(Long userId) {
    return sessionFactory.withSession(session ->
            session.createQuery("from Task t where t.user.id = ?1", Task.class).setParameter(1 , userId)
                .getResultList())
        .convert().with(UniReactorConverters.toMono()).flatMapMany((Flux::fromIterable));
  }
}
