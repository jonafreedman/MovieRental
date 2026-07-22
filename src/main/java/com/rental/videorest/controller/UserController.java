/**
 * REST Controller handling API requests for registration and login authentication.
 */
package com.rental.videorest.controller;

import com.rental.videorest.model.User;
import com.rental.videorest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 1. User Registration (Screen 1)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    // 2. Simple User Authentication/Login (Screen 1)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        Optional<User> userOpt = userRepository.findByUsername(loginDetails.getUsername());
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginDetails.getPassword())) {
            return ResponseEntity.ok(userOpt.get()); // Returns user data (including role: USER/ADMIN)
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }
}