package com.booklovers.booklovers.Entity;

 
import com.booklovers.booklovers.DTO.BookStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(length = 10000)
    private String description;

     private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
 

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private BookStatus status;
}