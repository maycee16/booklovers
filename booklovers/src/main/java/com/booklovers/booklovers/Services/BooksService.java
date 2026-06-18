package com.booklovers.booklovers.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookRequest;
import com.booklovers.booklovers.Entity.Books;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BooksRepository;
import com.booklovers.booklovers.Repository.UsersRepository;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    public ApiResponse<Books> createBook(BookRequest request) {

        Users user = usersRepository.findById(request.getUserId())
                .orElse(null);

        if (user == null) {
            return new ApiResponse<>(
                    false,
                    "User not found",
                    null);
        }

        Books book = new Books();
        book.setBookName(request.getBookName());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setStatus(request.getStatus());
        book.setUser(user);

        Books savedBook = booksRepository.save(book);

        return new ApiResponse<>(
                true,
                "Book posted successfully",
                savedBook);
    }
}