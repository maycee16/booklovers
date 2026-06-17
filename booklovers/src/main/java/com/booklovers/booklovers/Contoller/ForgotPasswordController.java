package com.booklovers.booklovers.Contoller;

import com.booklovers.booklovers.DTO.ForgotPasswordRequest;
import com.booklovers.booklovers.DTO.ResetPasswordRequest;
import com.booklovers.booklovers.Services.ForgotPasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService service;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {

        return ResponseEntity.ok(
                service.sendOtp(request.getEmail())
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {

        return ResponseEntity.ok(
                service.resetPassword(request)
        );
    }
}