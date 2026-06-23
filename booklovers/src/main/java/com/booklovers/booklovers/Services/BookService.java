package com.booklovers.booklovers.Services;

import com.booklovers.booklovers.Cloudinary.CloudinaryService;
import com.booklovers.booklovers.DTO.BookStatus;
import com.booklovers.booklovers.Entity.Book;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookRepository;
import com.booklovers.booklovers.Repository.UsersRepository;
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




    
}