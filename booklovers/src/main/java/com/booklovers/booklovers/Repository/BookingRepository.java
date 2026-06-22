package com.booklovers.booklovers.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.booklovers.Entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByBookId(Long bookId);
}
