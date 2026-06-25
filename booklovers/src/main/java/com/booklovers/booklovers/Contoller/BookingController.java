package com.booklovers.booklovers.Contoller;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookingStatus;
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/open/bookings")
@CrossOrigin("*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book/{bookId}/user/{userId}")
    public ResponseEntity<ApiResponse<Booking>> requestBook(
            @PathVariable Long bookId,
            @PathVariable Long userId) {

        Booking booking = bookingService.requestBook(userId, bookId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Book requested successfully",
                        booking
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Bookings retrieved successfully",
                        bookingService.getAllBookings()
                )
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Booking retrieved successfully",
                        bookingService.getBookingById(bookingId)
                )
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookingsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User bookings retrieved successfully",
                        bookingService.getBookingsByUser(userId)
                )
        );
    }

    @PutMapping("/{bookingId}/approve")
    public ResponseEntity<ApiResponse<Booking>> approveBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Booking approved successfully",
                        bookingService.approveBooking(bookingId)
                )
        );
    }

    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<ApiResponse<Booking>> rejectBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Booking rejected successfully",
                        bookingService.rejectBooking(bookingId)
                )
        );
    }

    @PutMapping("/{bookingId}/return")
    public ResponseEntity<ApiResponse<Booking>> returnBook(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Book returned successfully",
                        bookingService.returnBook(bookingId)
                )
        );
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<ApiResponse<Booking>> updateStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Booking status updated successfully",
                        bookingService.updateBookingStatus(
                                bookingId,
                                status
                        )
                )
        );
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(
            @PathVariable Long bookingId) {

        bookingService.deleteBooking(bookingId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Booking deleted successfully",
                        null
                )
        );
    }
}