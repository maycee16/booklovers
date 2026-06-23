package com.booklovers.booklovers.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.booklovers.booklovers.DTO.BookingStatus;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User requesting the book
    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Users borrower;

    // Book being requested
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime bookingDate;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}