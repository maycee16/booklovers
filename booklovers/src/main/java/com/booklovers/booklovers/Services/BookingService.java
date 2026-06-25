package com.booklovers.booklovers.Services;

 
import com.booklovers.booklovers.DTO.BookStatus;
import com.booklovers.booklovers.DTO.BookingStatus;
import com.booklovers.booklovers.Entity.Book;
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookRepository;
import com.booklovers.booklovers.Repository.BookingRepository;
import com.booklovers.booklovers.Repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookRepository bookRepository;
    private final UsersRepository usersRepository;

    public BookingService(
            BookingRepository bookingRepository,
            BookRepository bookRepository,
            UsersRepository usersRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.bookRepository = bookRepository;
        this.usersRepository = usersRepository;
    }

    public Booking requestBook(Long userId, Long bookId) {

        Users borrower = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Cannot borrow your own book
        if (book.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot request your own book");
        }

        // Must be available
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available");
        }

        Booking booking = new Booking();
        booking.setBorrower(borrower);
        booking.setBook(book);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        // Reserve book
        book.setStatus(BookStatus.RESERVED);
        bookRepository.save(book);

        return bookingRepository.save(booking);
    }
    // READ ALL BOOKINGS
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // READ BOOKING BY ID
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // READ BOOKINGS BY BORROWER
    public List<Booking> getBookingsByUser(Long userId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByBorrower(user);
    }

    // APPROVE REQUEST
    public Booking approveBooking(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(BookingStatus.APPROVED);

        return bookingRepository.save(booking);
    }

    // REJECT REQUEST
    public Booking rejectBooking(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(BookingStatus.REJECTED);

        Book book = booking.getBook();
        book.setStatus(BookStatus.AVAILABLE);

        bookRepository.save(book);

        return bookingRepository.save(booking);
    }

    // RETURN BOOK
    public Booking returnBook(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(BookingStatus.RETURNED);

        Book book = booking.getBook();
        book.setStatus(BookStatus.AVAILABLE);

        bookRepository.save(book);

        return bookingRepository.save(booking);
    }

    // UPDATE STATUS GENERICALLY
    public Booking updateBookingStatus(
            Long bookingId,
            BookingStatus status
    ) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(status);

        if (status == BookingStatus.REJECTED ||
                status == BookingStatus.RETURNED) {

            Book book = booking.getBook();
            book.setStatus(BookStatus.AVAILABLE);
            bookRepository.save(book);
        }

        return bookingRepository.save(booking);
    }

    // DELETE BOOKING
    public void deleteBooking(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        if (booking.getStatus() == BookingStatus.PENDING ||
                booking.getStatus() == BookingStatus.APPROVED) {

            Book book = booking.getBook();
            book.setStatus(BookStatus.AVAILABLE);
            bookRepository.save(book);
        }

        bookingRepository.delete(booking);
    }

}
