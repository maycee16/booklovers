package com.booklovers.booklovers.Entity;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 
 
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String status;


    @Column(name = "reset_otp")
private String resetOtp;

@Column(name = "otp_expiry")
private LocalDateTime otpExpiry;

    // Constructors
}