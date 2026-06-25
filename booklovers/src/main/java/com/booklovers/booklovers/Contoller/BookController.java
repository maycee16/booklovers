package com.booklovers.booklovers.Contoller;

 
import com.booklovers.booklovers.DTO.BookStatus;
import com.booklovers.booklovers.Entity.Book;
import com.booklovers.booklovers.Services.BookService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/open/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(
            value = "/user/{userId}",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<Book> addBook(
            @PathVariable Long userId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image
    ) throws Exception {

        Book savedBook = bookService.addBook(
                userId,
                title,
                author,
                description,
                image
        );

        return ResponseEntity.ok(savedBook);
    }
  @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // GET BOOK BY ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Long bookId) {

        return ResponseEntity.ok(
                bookService.getBookById(bookId)
        );
    }

    // GET BOOKS BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Book>> getBooksByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bookService.getBooksByUser(userId)
        );
    }

    // UPDATE BOOK DETAILS
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long bookId,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String description
    ) {

        return ResponseEntity.ok(
                bookService.updateBook(
                        bookId,
                        title,
                        author,
                        description
                )
        );
    }

    // UPDATE BOOK STATUS
    @PutMapping("/{bookId}/status")
    public ResponseEntity<Book> updateStatus(
            @PathVariable Long bookId,
            @RequestParam BookStatus status
    ) {

        return ResponseEntity.ok(
                bookService.updateBookStatus(
                        bookId,
                        status
                )
        );
    }

    // UPDATE BOOK IMAGE
    @PutMapping(
            value = "/{bookId}/image",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<Book> updateImage(
            @PathVariable Long bookId,
            @RequestParam MultipartFile image
    ) throws Exception {

        return ResponseEntity.ok(
                bookService.updateBookImage(
                        bookId,
                        image
                )
        );
    }

    // DELETE BOOK
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long bookId
    ) {

        bookService.deleteBook(bookId);

        return ResponseEntity.ok(
                "Book deleted successfully"
        );
    }
}

    
