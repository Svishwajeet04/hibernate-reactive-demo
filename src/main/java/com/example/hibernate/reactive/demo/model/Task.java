package com.example.hibernate.reactive.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne(targetEntity = User.class)
  private User user;

  @Column(columnDefinition = "timestamp")
  private ZonedDateTime createdTime;

  @Column(columnDefinition = "text")
  private String description;

  @Column(columnDefinition = "timestamp")
  private ZonedDateTime completeTime;

  @Column(columnDefinition = "timestamp")
  private ZonedDateTime estimatedEndTime;
}
