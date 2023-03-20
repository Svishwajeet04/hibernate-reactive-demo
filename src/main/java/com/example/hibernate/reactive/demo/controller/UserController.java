package com.example.hibernate.reactive.demo.controller;

import com.example.hibernate.reactive.demo.model.User;
import com.example.hibernate.reactive.demo.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @PostMapping("")
  public Mono<User> createUser(@RequestBody User user){
    return userRepository.createUser(user);
  }

  @GetMapping("")
  public Flux<User> getUsers(){
    return userRepository.getUsers();
  }
}
