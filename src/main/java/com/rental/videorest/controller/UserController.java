/**
 * REST controller handling customer account registration and login authentication requests.
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

    /**
     * Creates a new user account if the requested username isn't already taken.
     * Sets default role to "USER" if not provided.
     *
     * @param user prospective user entity populated from request body
     * @return 200 OK with user object on success, or 400 BAD REQUEST if username exists
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists!");
        }

        // Default to standard customer role if missing
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("USER");
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    /**
     * Authenticates login credentials against stored user account details.
     *
     * @param loginDetails credentials object containing username and password
     * @return 200 OK with User payload on valid credentials, or 401 UNAUTHORIZED on error
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        Optional<User> userOpt = userRepository.findByUsername(loginDetails.getUsername());
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginDetails.getPassword())) {
            return ResponseEntity.ok(userOpt.get()); // Returns user data (including role: USER/ADMIN)
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }
}