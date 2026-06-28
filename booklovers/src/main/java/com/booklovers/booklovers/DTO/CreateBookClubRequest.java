package com.booklovers.booklovers.DTO;

 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookClubRequest {

    private String clubName;

    private String description;

    private Long createdBy;
}