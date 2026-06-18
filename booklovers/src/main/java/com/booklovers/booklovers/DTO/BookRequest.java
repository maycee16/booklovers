package com.booklovers.booklovers.DTO;

 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private String bookName;
    private String description;
    private String author;
    private String genre;
    private String status;
    private Long userId;
}