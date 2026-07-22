/**
 * REST Controller enforcing core business rule tracking for checking out and returning rentals.
 */
package com.rental.videorest.controller;

import com.rental.videorest.model.Loan;
import com.rental.videorest.model.Movie;
import com.rental.videorest.model.User;
import com.rental.videorest.repository.LoanRepository;
import com.rental.videorest.repository.MovieRepository;
import com.rental.videorest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    // 1. Rent a DVD (Screen 3)
    @PostMapping("/rent")
    public ResponseEntity<?> rentMovie(@RequestParam Long userId, @RequestParam Long movieModifierId) {
        Movie movie = movieRepository.findById(movieModifierId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (movie == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Movie record not found.");
        }

        // Business Rule 1: Tight Inventory Control
        if (movie.getAvailableCopies() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie is currently out of stock!");
        }

        // Decrement physical shelves stock counter
        movie.setAvailableCopies(movie.getAvailableCopies() - 1);
        movieRepository.save(movie);

        Loan dynamicLoan = new Loan(user, movie, LocalDateTime.now());
        return ResponseEntity.ok(loanRepository.save(dynamicLoan));
    }

    // 2. Return a DVD (Screen 5: Admin Dash)
    @PutMapping("/return/{loanId}")
    public ResponseEntity<?> returnMovie(@PathVariable Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan record details missing.");
        }

        if (loan.getReturnDate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This copy has already been checked back in.");
        }

        // Close transaction logging timeframe
        loan.setReturnDate(LocalDateTime.now());
        
        // Restore inventory shelf balance levels
        Movie movie = loan.getMovie();
        movie.setAvailableCopies(movie.getAvailableCopies() + 1);
        
        movieRepository.save(movie);
        return ResponseEntity.ok(loanRepository.save(loan));
    }

    // 3. User Personal Logs (Screen 4)
    @GetMapping("/user/{userId}")
    public List<Loan> getUserLogs(@PathVariable Long userId) {
        return loanRepository.findByUserId(userId);
    }

    // 4. Admin Global Live Tracking Dashboard Monitor (Screen 5)
    @GetMapping("/active")
    public List<Loan> getActiveLoans() {
        return loanRepository.findByReturnDateIsNull();
    }
}