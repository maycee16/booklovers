package com.booklovers.booklovers.Contoller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booklovers.booklovers.DTO.ApiResponse;
import com.booklovers.booklovers.DTO.ForgotPasswordRequest;
import com.booklovers.booklovers.DTO.ResetPasswordRequest;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Services.UsersService;

@RestController
@RequestMapping("/api")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/open/users")
    public ResponseEntity<ApiResponse<Object>> createUser(
            @RequestBody Users user) {

        Users created = usersService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "User created successfully",
                        created
                ));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Object>> getAllUsers() {

        List<Users> users = usersService.getAllUsers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Users retrieved successfully",
                        users
                )
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> getUserById(
            @PathVariable Long id) {

        return usersService.getUserById(id)
                .map(user -> ResponseEntity.ok(
                        new ApiResponse<Object>(
                                true,
                                "User found",
                                user
                        )))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(
                                false,
                                "User not found",
                                null
                        )));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> updateUser(
            @PathVariable Long id,
            @RequestBody Users user) {

        try {

            Users updated = usersService.updateUser(id, user);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "User updated successfully",
                            updated
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(
                            false,
                            e.getMessage(),
                            null
                    ));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(
            @PathVariable Long id) {

        try {

            usersService.deleteUser(id);

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "User deleted successfully",
                            null
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(
                            false,
                            e.getMessage(),
                            null
                    ));
        }
    }

    @PostMapping("/open/forgot-password")
    public ResponseEntity<ApiResponse<Object>> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        return ResponseEntity.ok(
                usersService.forgotPassword(
                        request.getEmail()
                )
        );
    }

    @PostMapping("/open/reset-password")
    public ResponseEntity<ApiResponse<Object>> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        return ResponseEntity.ok(
                usersService.resetPassword(
                        request.getEmail(),
                        request.getOtp(),
                        request.getNewPassword()
                )
        );
    }
}