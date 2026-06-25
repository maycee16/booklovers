package com.booklovers.booklovers.Contoller;

 
import com.booklovers.booklovers.Entity.Book;
import com.booklovers.booklovers.Services.BookService;
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


    
}