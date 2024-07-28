package com.engineerpro.db.locking.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.engineerpro.db.locking.booking.model.Booking;
import com.engineerpro.db.locking.booking.model.Room;
import com.engineerpro.db.locking.booking.repository.BookingRepository;
import com.engineerpro.db.locking.booking.repository.RoomRepository;

// https://www.baeldung.com/mockito-annotations
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private RoomRepository roomRepository;

  UserService userService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    userService = new UserServiceImpl(bookingRepository, roomRepository);
  }

  @Test
  void testOptismisticBooking_whenUpdatedRoomRow() {
    // given
    int userId = 1;
    int roomId = 2;
    int version = 0;
    Room mockAvailableRoom = Room.builder().id(roomId).available(true).room("test").version(version).build();
    Booking mockCreatedBooking = Booking.builder().id(100).userId(userId).roomId(roomId).build();
    when(roomRepository.findOneByIdAndAvailable(roomId, true)).thenReturn(mockAvailableRoom);
    when(bookingRepository.save(any())).thenReturn(mockCreatedBooking);
    when(roomRepository.updateRoomAsUnavailable(roomId, version)).thenReturn(1);
    // when
    Booking booking = userService.optimisticBookRoom(userId, roomId);
    // then
    assertEquals(roomId, booking.getRoomId());
    assertEquals(userId, booking.getUserId());
  }

  @Test
  void testOptismisticBooking_whenUpdatedNoRoomRow() {
    // given
    int userId = 1;
    int roomId = 2;
    int version = 0;
    Room mockAvailableRoom = Room.builder().id(roomId).available(true).room("test").version(version).build();
    Booking mockCreatedBooking = Booking.builder().id(100).userId(userId).roomId(roomId).build();
    when(roomRepository.findOneByIdAndAvailable(roomId, true)).thenReturn(mockAvailableRoom);
    when(bookingRepository.save(any())).thenReturn(mockCreatedBooking);
    when(roomRepository.updateRoomAsUnavailable(roomId, version)).thenReturn(0);
    // when
    assertThrows(RuntimeException.class, () -> userService.optimisticBookRoom(userId, roomId));
  }

  @Test
  void testPessimisticBooking_whenSelectedAvaiableRoomRow() {
    // given
    int userId = 1;
    int roomId = 2;
    Room mockAvailableRoom = Room.builder().id(roomId).available(true).room("test").build();
    Booking mockCreatedBooking = Booking.builder().id(100).userId(userId).roomId(roomId).build();
    when(roomRepository.findByIdAndAvailable(roomId, true)).thenReturn(mockAvailableRoom);
    when(bookingRepository.save(any())).thenReturn(mockCreatedBooking);
    when(roomRepository.updateRoomAsUnavailableWhenPessimisticLocked(roomId)).thenReturn(1);
    // when
    Booking booking = userService.perssimisticBookRom(userId, roomId);
    // then
    assertEquals(roomId, booking.getRoomId());
    assertEquals(userId, booking.getUserId());
  }

  @Test
  void testPessimisticBooking_whenCannotSelectedAvailableRoom() {
    // given
    int userId = 1;
    int roomId = 2;
    when(roomRepository.findByIdAndAvailable(roomId, true)).thenReturn(null);
    // when
    assertThrows(RuntimeException.class, () -> userService.perssimisticBookRom(userId, roomId));
    // then
    verifyNoInteractions(bookingRepository);
    verify(roomRepository, times(1)).findByIdAndAvailable(roomId, true);
    verifyNoMoreInteractions(roomRepository);
  }

}
