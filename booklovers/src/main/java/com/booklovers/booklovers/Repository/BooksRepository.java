package com.booklovers.booklovers.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booklovers.booklovers.Entity.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
  }

