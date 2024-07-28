package com.engineerpro.db.locking.booking.service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engineerpro.db.locking.booking.model.Booking;
import com.engineerpro.db.locking.booking.model.Room;
import com.engineerpro.db.locking.booking.repository.BookingRepository;
import com.engineerpro.db.locking.booking.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Transactional
  @Override
  public Booking optimisticBookRoom(int userId, int roomId) {
    log.info("userid={} start booking", userId);
    Room room = roomRepository.findOneByIdAndAvailable(roomId, true);
    log.info("userid={} selected a room = {}, start saving data", userId, room);
    Booking booking = Booking.builder().roomId(roomId).userId(userId).build();
    bookingRepository.save(booking);
    // delay transaction
    log.info("start delay");
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      log.error("error when delay", e);
    }
    log.info("finished delay");
    int updatedRows = roomRepository.updateRoomAsUnavailable(roomId, room.getVersion());
    log.info("userId={}, updatedRows={}", userId, updatedRows);
    if (updatedRows == 0) {
      log.info("user={} cannot update room, rollback", userId);
      throw new RuntimeException(String.format("user=%d cannot update room, need rollback", userId));
    }
    log.info("user={} succesfully booked room={}", userId, roomId);
    return booking;
  }

  @Transactional
  @Override
  public Booking perssimisticBookRom(int userId, int roomId) {
    log.info("userid={} start booking", userId);
    Room room = roomRepository.findByIdAndAvailable(roomId, true);
    if (Objects.isNull(room)) {
      log.info("No room available with id={}", roomId);
      throw new RuntimeException(String.format("No room available with id=%d", roomId));
    }
    log.info("userid={} selected a room = {}, start saving data", userId, room);
    Booking booking = Booking.builder().roomId(roomId).userId(userId).build();
    bookingRepository.save(booking);
    // delay transaction
    log.info("start delay");
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      log.error("error when delay", e);
    }
    log.info("finished delay");
    int updatedRows = roomRepository.updateRoomAsUnavailableWhenPessimisticLocked(roomId);
    if (updatedRows == 0) {
      // this case should never happen because row has been lock already
      log.info("user={} cannot update room, rollback", userId);
      throw new RuntimeException(String.format("user=%d cannot update room, need rollback", userId));
    }
    log.info("saved updated={}", updatedRows);
    log.info("user={} succesfully booked room={}", userId, roomId);
    return booking;
  }

}
