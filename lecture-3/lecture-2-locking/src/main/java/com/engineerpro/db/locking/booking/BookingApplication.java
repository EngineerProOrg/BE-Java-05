package com.engineerpro.db.locking.booking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.engineerpro.db.locking.booking.model.Booking;
import com.engineerpro.db.locking.booking.model.Room;
import com.engineerpro.db.locking.booking.repository.BookingRepository;
import com.engineerpro.db.locking.booking.repository.RoomRepository;
import com.engineerpro.db.locking.booking.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BookingApplication {
	private static int TRANSACTIONS_PER_MACHINE = 5;

	public static class BookingRunnable implements Runnable {
		private int userId;
		private int roomId;
		private UserService service;

		public BookingRunnable(UserService service, int userId, int roomId) {
			this.userId = userId;
			this.service = service;
			this.roomId = roomId;
		}

		public void run() {
			// change here to switch between optimistic and pessimistic lock
			Booking booking = service.optimisticBookRoom(userId, roomId);
			// Booking booking = service.perssimisticBookRom(userId, roomId);
			log.info("userId={}, booking id={}", userId, booking.getId());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(BookingApplication.class, args);

		UserService service = context.getBean(UserService.class);
		RoomRepository roomRepository = context.getBean(RoomRepository.class);
		BookingRepository bookingRepository = context.getBean(BookingRepository.class);

		Room createdRoom = roomRepository.save(Room.builder().room("Room1").available(true).build());
		log.info("created room={}", createdRoom);

		// service.optimisticBookRoom(1, createdRoom.getId());
		List<Thread> allThreads = new ArrayList<>();
		for (int userId = 1; userId <= TRANSACTIONS_PER_MACHINE; userId++) {
			Thread t = new Thread(new BookingRunnable(service, userId, createdRoom.getId()));
			log.info("start thread userId={}", userId);
			t.start();
			allThreads.add(t);
		}
		for (Thread t : allThreads) {
			t.join();
		}
	}

}
