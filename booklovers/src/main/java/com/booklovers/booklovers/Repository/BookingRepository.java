package com.booklovers.booklovers.Repository;

import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
     List<Booking> findByBorrower(Users borrower);

}