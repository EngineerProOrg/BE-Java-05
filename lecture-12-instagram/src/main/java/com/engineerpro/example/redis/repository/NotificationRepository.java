package com.engineerpro.example.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.example.redis.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
