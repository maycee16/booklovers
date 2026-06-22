package com.booklovers.booklovers.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booklovers.booklovers.Cloudinary.CloudinaryService;
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
    private CloudinaryService cloudinaryService;

    @Autowired
    private UsersRepository usersRepository;

    public ApiResponse<Books> createBook(BookRequest request)throws Exception {

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

   if (request.getImage() != null && !request.getImage().isEmpty()) {

            String imageUrl = cloudinaryService.uploadImage(request.getImage());

            book.setImageUrl(imageUrl);
        }
        Books savedBook = booksRepository.save(book);

        return new ApiResponse<>(
                true,
                "Book posted successfully",
                savedBook);
    }
   public ApiResponse<List<Books>> getAllBooks() {

        List<Books> books = booksRepository.findAll();

        return new ApiResponse<>(true, "Books retrieved successfully", books);
    }

    // GET BY ID
    public ApiResponse<Books> getBookById(Long id) {

        return booksRepository.findById(id)
                .map(book -> new ApiResponse<>(true, "Book found", book))
                .orElse(new ApiResponse<>(false, "Book not found", null));
    }

    // UPDATE
    public ApiResponse<Books> updateBook(Long id, BookRequest request)throws Exception {

        return booksRepository.findById(id)
                .map(book -> {

                    book.setBookName(request.getBookName());
                    book.setAuthor(request.getAuthor());
                    book.setDescription(request.getDescription());
                    book.setGenre(request.getGenre());
                    book.setStatus(request.getStatus());
                    try {
                  
                    if (request.getImage() != null && !request.getImage().isEmpty()) {

                        String imageUrl = cloudinaryService.uploadImage(request.getImage());

                        book.setImageUrl(imageUrl);
                    } 
                } catch (Exception e) {
        throw new RuntimeException("Image upload failed", e);
    }

                    Books updatedBook = booksRepository.save(book);

                    return new ApiResponse<>(true, "Book updated successfully", updatedBook);

                }).orElse(new ApiResponse<>(false, "Book not found", null));
    }

    // DELETE
    public ApiResponse<String> deleteBook(Long id) {

        if (!booksRepository.existsById(id)) {

            return new ApiResponse<>(false, "Book not found", null);
        }

        booksRepository.deleteById(id);

        return new ApiResponse<>(true, "Book deleted successfully", null);
    }
}