package com.booklovers.booklovers.Entity;

 
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="book_club_members",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"club_id","user_id"})
       })
public class BookClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="club_id")
    private BookClub club;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;

    @Column(nullable=false)
    private String role="MEMBER";

    @Column(nullable=false)
    private LocalDateTime joinedAt=LocalDateTime.now();
}