package com.example.hibernate.reactive.demo.repository.interfaces;

import com.example.hibernate.reactive.demo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
  Mono<User> createUser(User user);

  Flux<User> getUsers();
}
