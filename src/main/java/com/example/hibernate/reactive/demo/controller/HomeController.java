package com.example.hibernate.reactive.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class HomeController {

  @GetMapping("/")
  public Flux<String> getHome(ServerWebExchange serverWebExchange){
    return Flux.just("working fine ", serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
  }
}
