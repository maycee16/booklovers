package com.booklovers.booklovers.Contoller;

 
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.BookClubResponse;
import com.booklovers.booklovers.DTO.CreateBookClubRequest;
import com.booklovers.booklovers.DTO.JoinClubRequest;
import com.booklovers.booklovers.Services.BookClubService;

@RestController
@RequestMapping("/api/open/book-clubs")
@CrossOrigin("*")
public class BookClubController {

    private final BookClubService bookClubService;

    public BookClubController(BookClubService bookClubService) {
        this.bookClubService = bookClubService;
    }

    /**
     * Create a new Book Club
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BookClubResponse>> createClub(
            @RequestBody CreateBookClubRequest request) {

        BookClubResponse response = bookClubService.createClub(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Book club created successfully.",
                        response
                )
        );
    }

    /**
     * Get all Book Clubs
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookClubResponse>>> getAllClubs() {

        List<BookClubResponse> clubs = bookClubService.getAllClubs();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Book clubs retrieved successfully.",
                        clubs
                )
        );
    }

    /**
     * Join a Book Club
     */
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Void>> joinClub(
            @RequestBody JoinClubRequest request) {

        bookClubService.joinClub(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Successfully joined the book club.",
                        null
                )
        );
    }

}