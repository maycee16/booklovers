package com.booklovers.booklovers.Repository;

import com.booklovers.booklovers.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}