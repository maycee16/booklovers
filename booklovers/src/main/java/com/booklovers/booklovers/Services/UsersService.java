package com.booklovers.booklovers.Services;

import com.booklovers.booklovers.DTO.LoginRequest;
import com.booklovers.booklovers.DTO.LoginResponse;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.UsersRepository;
import com.booklovers.booklovers.Utility.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
 
@Service
public class UsersService {



    private final JwtUtil jwtUtil;

     private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(
            UsersRepository usersRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Users createUser(Users user) {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new RuntimeException("Name is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users updateUser(Long id, Users updatedUser) {

        return usersRepository.findById(id)
                .map(existing -> {

                    if (updatedUser.getName() != null &&
                            !updatedUser.getName().trim().isEmpty()) {
                        existing.setName(updatedUser.getName());
                    }

                    if (updatedUser.getEmail() != null &&
                            !updatedUser.getEmail().trim().isEmpty()) {

                        Optional<Users> userWithEmail =
                                usersRepository.findByEmail(updatedUser.getEmail());

                        if (userWithEmail.isPresent() &&
                                !userWithEmail.get().getId().equals(id)) {
                            throw new RuntimeException("Email already in use");
                        }

                        existing.setEmail(updatedUser.getEmail());
                    }

                    if (updatedUser.getPassword() != null &&
                            !updatedUser.getPassword().trim().isEmpty()) {

                        if (updatedUser.getPassword().length() < 6) {
                            throw new RuntimeException(
                                    "Password must be at least 6 characters"
                            );
                        }

                        existing.setPassword(
                                passwordEncoder.encode(updatedUser.getPassword())
                        );
                    }

                    return usersRepository.save(existing);
                })
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {

        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        usersRepository.deleteById(id);
    }




public LoginResponse login(LoginRequest request) throws Exception {

    Users user = usersRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new RuntimeException("Invalid email or password"));

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        throw new RuntimeException("Invalid email or password");
    }

    String token = jwtUtil.generateToken(
            user.getId(),
            user.getEmail(),
            user.getName()
    );

    return new LoginResponse(
            token,
            user.getId(),
            user.getName()
    );
}
}
