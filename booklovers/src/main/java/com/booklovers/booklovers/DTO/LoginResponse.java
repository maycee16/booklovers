package com.booklovers.booklovers.DTO;
 
public class LoginResponse {

    private String token;
    private Long id;
    private String name;

    public LoginResponse(String token, Long id, String name) {
        this.token = token;
        this.id = id;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}