package com.booklovers.booklovers.Contoller;

<<<<<<< HEAD

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookingRequest;
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Services.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // CREATE BOOKING
    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(
            @RequestBody BookingRequest request) {

        return ResponseEntity.ok(
                bookingService.createBooking(request));
    }

    // GET ALL BOOKINGS
    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings());
    }

    // GET BOOKING BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBooking(id));
    }

    // APPROVE BOOKING
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<Booking>> approveBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.approveBooking(id));
    }

    // REJECT BOOKING
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<Booking>> rejectBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.rejectBooking(id));
    }

    // DELETE BOOKING
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.deleteBooking(id));
    }
}
=======
 
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/open/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book/{bookId}/user/{userId}")
    public ResponseEntity<Booking> requestBook(
            @PathVariable Long bookId,
            @PathVariable Long userId) {

        Booking booking = bookingService.requestBook(userId, bookId);

        return ResponseEntity.ok(booking);
    }
}
>>>>>>> 074ae1688ada59d8ed50fbc10150e06db15a3459
