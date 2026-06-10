package com.booklovers.booklovers.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users createUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use: " + user.getEmail());
        }
        // TODO: hash password before saving e.g. passwordEncoder.encode(user.getPassword())
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
        return usersRepository.findById(id).map(existing -> {
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
            // TODO: hash password if changed
            existing.setPassword(updatedUser.getPassword());
            return usersRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        usersRepository.deleteById(id);
    }
}


