package com.booklovers.booklovers.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookRequest;
import com.booklovers.booklovers.Entity.Books;
import com.booklovers.booklovers.Services.BooksService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @PostMapping
    public ResponseEntity<ApiResponse<Books>> createBook(
            @RequestBody BookRequest request) {

        ApiResponse<Books> response = booksService.createBook(request);

        if (!response.isStatus()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}