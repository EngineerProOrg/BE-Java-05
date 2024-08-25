package com.engineerpro.rest.example.model;

import java.io.Serializable;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channel", uniqueConstraints = @UniqueConstraint(columnNames = { "appId", "clientReferenceId" }))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Channel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "appId", nullable = false)
  @JsonProperty("app")
  private App app;

  @Column(name = "clientReferenceId", nullable = false)
  private String clientReferenceId;

  @CreationTimestamp
  private Instant createdAt;
}
