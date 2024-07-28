package com.engineerpro.db.locking.booking.model;

import java.io.Serializable;

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
@Table(name = "booking")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "userId", nullable = false)
  private int userId;

  @Column(name = "roomId", nullable = false)
  private int roomId;
}
