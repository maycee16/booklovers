package com.booklovers.booklovers.Services;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookingRequest;
import com.booklovers.booklovers.Entity.BookStatus;
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Entity.BookingStatus;
import com.booklovers.booklovers.Entity.Books;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookingRepository;
import com.booklovers.booklovers.Repository.BooksRepository;
import com.booklovers.booklovers.Repository.UsersRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    // CREATE BOOKING
    public ApiResponse<Booking> createBooking(BookingRequest request) {

        Users user = usersRepository.findById(request.getUserId()).orElse(null);

        if (user == null) {
            return new ApiResponse<>(false, "User not found", null);
        }

        Books book = booksRepository.findById(request.getBookId()).orElse(null);

        if (book == null) {
            return new ApiResponse<>(false, "Book not found", null);
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            return new ApiResponse<>(false, "Book is not available", null);
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBook(book);
        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);

        Booking saved = bookingRepository.save(booking);

        return new ApiResponse<>(true, "Booking request sent", saved);
    }

    // GET ALL BOOKINGS
    public ApiResponse<List<Booking>> getAllBookings() {

        return new ApiResponse<>(
                true,
                "Bookings retrieved successfully",
                bookingRepository.findAll());
    }

    // GET BOOKING BY ID
    public ApiResponse<Booking> getBooking(Long id) {

        return bookingRepository.findById(id)
                .map(b -> new ApiResponse<>(true, "Booking found", b))
                .orElse(new ApiResponse<>(false, "Booking not found", null));
    }

    // APPROVE BOOKING
    public ApiResponse<Booking> approveBooking(Long id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null) {
            return new ApiResponse<>(false, "Booking not found", null);
        }

        booking.setBookingStatus(BookingStatus.APPROVED);

        Books book = booking.getBook();
        book.setStatus(BookStatus.BOOKED);

        booksRepository.save(book);

        bookingRepository.save(booking);

        return new ApiResponse<>(true, "Booking approved", booking);
    }

    // REJECT BOOKING
    public ApiResponse<Booking> rejectBooking(Long id) {

        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking == null) {
            return new ApiResponse<>(false, "Booking not found", null);
        }

        booking.setBookingStatus(BookingStatus.REJECTED);

        bookingRepository.save(booking);

        return new ApiResponse<>(true, "Booking rejected", booking);
    }

    // DELETE BOOKING
    public ApiResponse<String> deleteBooking(Long id) {

        if (!bookingRepository.existsById(id)) {

            return new ApiResponse<>(false, "Booking not found", null);
        }

        bookingRepository.deleteById(id);

        return new ApiResponse<>(true, "Booking deleted", null);
    }
}