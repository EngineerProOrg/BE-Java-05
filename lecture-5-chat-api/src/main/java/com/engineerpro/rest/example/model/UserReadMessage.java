package com.engineerpro.rest.example.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "userReadMessage", uniqueConstraints = @UniqueConstraint(columnNames = { "channelId", "userId" }))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReadMessage implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private String id;

  @ManyToOne
  @JoinColumn(name = "channelId", nullable = false)
  @JsonProperty("channel")
  private Channel channel;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  @JsonProperty("user")
  private User user;

  @Column(name = "lastReadMessage", nullable = false)
  Date lastReadMessage;

  @CreationTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;
}
