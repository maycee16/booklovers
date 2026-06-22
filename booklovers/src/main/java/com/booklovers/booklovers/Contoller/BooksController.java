package com.booklovers.booklovers.Contoller;

import java.util.List;

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

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Books>> createBook(
            @RequestAttribute BookRequest request) throws Exception {

        ApiResponse<Books> response = booksService.createBook(request);

        if (!response.isStatus()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

 // GET ALL BOOKS
    @GetMapping
    public ResponseEntity<ApiResponse<List<Books>>> getAllBooks() {

        ApiResponse<List<Books>> response = booksService.getAllBooks();

        return ResponseEntity.ok(response);
    }

    // GET BOOK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Books>> getBookById(@PathVariable Long id) {

        ApiResponse<Books> response = booksService.getBookById(id);

        if (!response.isStatus()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    // UPDATE BOOK
   @PutMapping(value="/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Books>> updateBook(
            @PathVariable Long id,
            @RequestAttribute BookRequest request) throws Exception {

        ApiResponse<Books> response = booksService.updateBook(id, request);

        if (!response.isStatus()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }

    // DELETE BOOK
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id) {

        ApiResponse<String> response = booksService.deleteBook(id);

        if (!response.isStatus()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}