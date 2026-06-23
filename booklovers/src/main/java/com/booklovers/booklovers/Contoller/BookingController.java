package com.booklovers.booklovers.Contoller;


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
