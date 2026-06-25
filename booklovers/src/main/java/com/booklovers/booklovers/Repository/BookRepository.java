package com.booklovers.booklovers.Repository;

 
import com.booklovers.booklovers.Entity.Book;
import com.booklovers.booklovers.Entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(Users user);

    

    
}