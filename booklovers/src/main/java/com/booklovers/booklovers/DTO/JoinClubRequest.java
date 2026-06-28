package com.booklovers.booklovers.DTO;

 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinClubRequest {

    private Long clubId;

    private Long userId;
}