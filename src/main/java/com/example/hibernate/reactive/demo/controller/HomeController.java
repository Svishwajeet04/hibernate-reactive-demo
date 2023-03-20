package com.example.hibernate.reactive.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class HomeController {

  @GetMapping("/")
  public Flux<String> getHome(){
    return Flux.just("working fine");
  }
}
