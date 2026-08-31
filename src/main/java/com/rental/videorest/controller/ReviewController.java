/**
 * REST controller providing endpoints to submit ratings and read movie reviews.
 */
package com.rental.videorest.controller;

import com.rental.videorest.model.Loan;
import com.rental.videorest.model.Review;
import com.rental.videorest.repository.LoanRepository;
import com.rental.videorest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private LoanRepository loanRepository;

    /**
     * Retrieves all user feedback comments and rating scores recorded against a specific movie title.
     *
     * @param movieId target movie identifier
     * @return list of Review instances associated with the given movie
     */
    @GetMapping("/movie/{movieId}")
    public List<Review> getMovieReviews(@PathVariable Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    /**
     * Handles HTTP POST requests to submit a new review for a movie.
     * Validates that both the user and movie are present in the payload, and verifies 
     * that the user has a record of renting the specified movie before persisting the review.
     * @param review object payload contained in the request body
     * @return 200 OK with the saved entity if successful
     *         400 Bad Request if the review payload is missing a user or movie
     *         403 if the user has not previously rented the movie
     */
    @PostMapping
    public ResponseEntity<?> submitReview(@RequestBody Review review) {
        if (review.getUser() == null || review.getMovie() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review payload.");
        }
        
        // Check if user has rented the movie
        List<Loan> userLoans = loanRepository.findByUserId(review.getUser().getId());
        boolean hasRented = userLoans.stream()
                .anyMatch(loan -> loan.getMovie().getId().equals(review.getMovie().getId()));

        if (!hasRented) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User must rent the movie before submitting a review.");
        }

        return ResponseEntity.ok(reviewRepository.save(review));
    }
}