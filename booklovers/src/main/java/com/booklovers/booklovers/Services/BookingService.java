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

<<<<<<< HEAD
import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookingRequest;
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Entity.BookingStatus;
import com.booklovers.booklovers.Entity.Books;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookingRepository;
import com.booklovers.booklovers.Repository.BooksRepository;
import com.booklovers.booklovers.Repository.UsersRepository;
=======
import java.time.LocalDateTime;
>>>>>>> 074ae1688ada59d8ed50fbc10150e06db15a3459

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

<<<<<<< HEAD
        if (book.getStatus() != BookingStatus.AVAILABLE) {
            return new ApiResponse<>(false, "Book is not available", null);
=======
        // Must be available
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available");
>>>>>>> 074ae1688ada59d8ed50fbc10150e06db15a3459
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
}