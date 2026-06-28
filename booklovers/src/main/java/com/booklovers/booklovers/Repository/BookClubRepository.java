package com.booklovers.booklovers.Repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.booklovers.Entity.BookClub;

public interface BookClubRepository
        extends JpaRepository<BookClub,Long>{

}