package com.example.hibernate.reactive.demo.repository.impl;

import com.example.hibernate.reactive.demo.model.User;
import com.example.hibernate.reactive.demo.repository.interfaces.UserRepository;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Service
public class UserRepoImpl implements UserRepository {

  private final Mutiny.SessionFactory sessionFactory;

  public UserRepoImpl(@Autowired Mutiny.SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Mono<User> createUser(User user) {
    return sessionFactory.withSession(session ->
        session.persist(user)
            .chain(session::flush)
            .replaceWith(user))
        .convert()
            .with(getUniMonoFunction());
  }

  private static <I> Function<Uni<I>, Mono<I>> getUniMonoFunction() {
    return userUni -> Mono.fromCompletionStage(userUni.convert().toCompletableFuture());
  }

  @Override
  public Flux<User> getUsers() {
    return monoToFluxUsingFlatMapMany(
            sessionFactory.withSession(session -> session.createQuery("from User u" , User.class)
        .getResultList())
                    .convert().with(getUniMonoFunction()));
  }

  private <T> Flux<T> monoToFluxUsingFlatMapMany(Mono<List<T>> monoList) {
    return monoList
        .flatMapMany(Flux::fromIterable);
  }
}
