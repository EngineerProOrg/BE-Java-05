package com.engineerpro.rest.example.model;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class App implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "apiClientKey", unique = true)
  private String apiClientKey;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "isActive")
  private Boolean isActive;

  @CreationTimestamp
  @JsonIgnore
  private Instant createdAt;
}
