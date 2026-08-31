/**
 * REST controller managing rental checkouts, returns, and historical user transaction logs.
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

    /**
     * Processes a new movie rental request by validating stock availability and generating a loan record.
     *
     * @param userId primary key ID of the customer requesting the rental
     * @param movieId primary key ID of the requested movie title
     * @return 200 OK with Loan payload on success, 404 NOT FOUND if user/movie doesn't exist, or 400 BAD REQUEST if out of stock
     */
    @PostMapping("/rent")
    public ResponseEntity<?> rentMovie(@RequestParam Long userId, @RequestParam Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (movie == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Movie record not found.");
        }

        // Out of stock check
        if (movie.getAvailableCopies() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie is currently out of stock!");
        }

        // Decrement physical shelves stock counter
        movie.setAvailableCopies(movie.getAvailableCopies() - 1);
        movieRepository.save(movie);

        Loan dynamicLoan = new Loan(user, movie, LocalDateTime.now());
        return ResponseEntity.ok(loanRepository.save(dynamicLoan));
    }

    /**
     * Processes a DVD return transaction, closing the loan duration and incrementing remaining stock.
     *
     * @param loanId unique identifier of the target active loan record
     * @return 200 OK with updated Loan details, 404 NOT FOUND if missing, or 400 BAD REQUEST if already returned
     */
    @PutMapping("/return/{loanId}")
    public ResponseEntity<?> returnMovie(@PathVariable Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan record details missing.");
        }

        if (loan.getReturnDate() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This copy has already been checked back in.");
        }

        // Set time of return
        loan.setReturnDate(LocalDateTime.now());
        
        // Back in stock
        Movie movie = loan.getMovie();
        movie.setAvailableCopies(movie.getAvailableCopies() + 1);
        
        movieRepository.save(movie);
        return ResponseEntity.ok(loanRepository.save(loan));
    }

    /**
     * Retrieves all past and present rental transactions linked to a specific user account.
     *
     * @param userId unique identifier of the user
     * @return list of matching Loan records
     */
    @GetMapping("/user/{userId}")
    public List<Loan> getUserLogs(@PathVariable Long userId) {
        return loanRepository.findByUserId(userId);
    }

    /**
     * Retrieves all active loans across the store network that haven't been returned yet.
     *
     * @return list of current outstanding Loan records
     */
    @GetMapping("/active")
    public List<Loan> getActiveLoans() {
        return loanRepository.findByReturnDateIsNull();
    }
}