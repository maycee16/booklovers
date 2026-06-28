package com.booklovers.booklovers.Services;

import com.booklovers.booklovers.Cloudinary.CloudinaryService;
import com.booklovers.booklovers.DTO.BookStatus;
import com.booklovers.booklovers.Entity.Book;
import com.booklovers.booklovers.Entity.Booking;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookRepository;
import com.booklovers.booklovers.Repository.UsersRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UsersRepository usersRepository;
    private final CloudinaryService cloudinaryService;

    public BookService(
            BookRepository bookRepository,
            UsersRepository usersRepository,
            CloudinaryService cloudinaryService
    ) {
        this.bookRepository = bookRepository;
        this.usersRepository = usersRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Book addBook(
            Long userId,
            String title,
            String author,
            String description,
            MultipartFile image
    ) throws Exception {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String imageUrl = cloudinaryService.uploadImage(image);

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setImageUrl(imageUrl);

        // Default status for newly added books
        book.setStatus(BookStatus.AVAILABLE);

        book.setUser(user);

        return bookRepository.save(book);
    }
 



    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

   public List<Book> getBooksByUser(Long userId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookRepository.findByUser(user);
    }

    // UPDATE BOOK DETAILS
    public Book updateBook(
            Long id,
            String title,
            String author,
            String description) {

        Book book = getBookById(id);

        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);

        return bookRepository.save(book);
    }

    // UPDATE BOOK STATUS
    public Book updateBookStatus(Long id, BookStatus status) {

        Book book = getBookById(id);
        book.setStatus(status);

        return bookRepository.save(book);
    }

    // UPDATE BOOK IMAGE
    public Book updateBookImage(Long id, MultipartFile image) throws Exception {

        Book book = getBookById(id);

        String imageUrl = cloudinaryService.uploadImage(image);
        book.setImageUrl(imageUrl);

        return bookRepository.save(book);
    }

    // DELETE
    public void deleteBook(Long id) {

        Book book = getBookById(id);
        bookRepository.delete(book);
    }



   // READ ALL BOOKS
public List<Book> getAllBooks() {
    return bookRepository.findAll();
}
}