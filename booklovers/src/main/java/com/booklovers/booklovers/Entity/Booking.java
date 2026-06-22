package com.booklovers.booklovers.Entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "user_id")
    private Users user;

    // Book being booked
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books book;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.PENDING;

    private LocalDateTime bookingDate = LocalDateTime.now();
}