package com.engineerpro.example.redis.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "from_user", nullable = false)
  @JsonProperty("from_user")
  Profile fromUser;

  @ManyToOne
  @JoinColumn(name = "to_user", nullable = false)
  @JsonProperty("to_user")
  Profile toUser;

  @NotNull
  @Enumerated(EnumType.STRING)
  NotificationType notificationType;

  @NotNull
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  @JsonIgnore
  private Post post;
}
