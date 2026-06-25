package com.booklovers.booklovers.DTO;

import lombok.Data;

@Data
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String description;
    private String imageUrl;
    private String status;

    private Long userId;
    private String userName;
}