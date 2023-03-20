package com.example.hibernate.reactive.demo.controller;

import com.example.hibernate.reactive.demo.model.Task;
import com.example.hibernate.reactive.demo.repository.interfaces.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  TaskRepository taskRepository;

  @PostMapping
  public Mono<Task> createTask(@RequestBody Task task){
    return taskRepository.createTask(task);
  }

  @GetMapping()
  public Flux<Task> getAllTasks(){
    return taskRepository.getAllTasks();
  }

  @GetMapping("/{userId}")
  public Flux<Task> getAllTasks(@PathVariable Long userId){
    return taskRepository.getAllTasksForUser(userId);
  }
}
