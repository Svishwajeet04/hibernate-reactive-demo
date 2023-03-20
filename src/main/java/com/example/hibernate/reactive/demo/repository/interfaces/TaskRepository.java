package com.example.hibernate.reactive.demo.repository.interfaces;

import com.example.hibernate.reactive.demo.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository {
  Mono<Task> createTask(Task task);

  Flux<Task> getAllTasks();

  Flux<Task> getAllTasksForUser(Long userId);
}
