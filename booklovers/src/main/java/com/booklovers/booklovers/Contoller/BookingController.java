package com.booklovers.booklovers.Contoller;

 
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