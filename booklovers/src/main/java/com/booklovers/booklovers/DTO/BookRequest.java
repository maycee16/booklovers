package com.booklovers.booklovers.DTO;

 
import lombok.Data;

@Data
public class BookRequest {

    private String title;
    private String author;
    private String description;
}