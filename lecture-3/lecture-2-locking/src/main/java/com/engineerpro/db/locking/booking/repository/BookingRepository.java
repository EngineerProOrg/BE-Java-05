package com.engineerpro.db.locking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engineerpro.db.locking.booking.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
