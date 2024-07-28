package com.engineerpro.db.locking.booking.service;

import org.springframework.stereotype.Service;

import com.engineerpro.db.locking.booking.model.Booking;

@Service
public interface UserService {
  Booking optimisticBookRoom(int userId, int roomId);
  Booking perssimisticBookRom(int userId, int roomId);
}
