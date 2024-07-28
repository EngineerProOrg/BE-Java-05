package com.engineerpro.db.locking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.engineerpro.db.locking.booking.model.Room;

import jakarta.persistence.LockModeType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
  // use for optimistic lock
  Room findOneByIdAndAvailable(int id, boolean available);

  // use for pessimistic lock, can uncomment to test
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Room findByIdAndAvailable(int id, boolean available);

  @Modifying
  @Query("update Room set available = false, version = version + 1 where id = :id and version = :version")
  int updateRoomAsUnavailable(@Param(value = "id") int id, @Param(value = "version") int version);

  @Modifying
  @Query("update Room set available = false where id = :id and available = true")
  int updateRoomAsUnavailableWhenPessimisticLocked(@Param(value = "id") int id);
}
