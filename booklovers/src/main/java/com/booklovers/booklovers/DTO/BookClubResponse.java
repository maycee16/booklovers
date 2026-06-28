package com.booklovers.booklovers.DTO;

 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookClubResponse {

    private Long id;

    private String clubName;

    private String description;

    private Long creatorId;

    private String creatorName;

    private String creatorEmail;

    private int members;
}