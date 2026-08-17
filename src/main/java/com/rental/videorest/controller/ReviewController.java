/**
 * REST controller providing endpoints to submit ratings and read movie reviews.
 */
package com.rental.videorest.controller;

import com.rental.videorest.model.Review;
import com.rental.videorest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

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
     * Stores a new user review submission in the database.
     *
     * @param review review object populated from request JSON body
     * @return the saved Review entity
     */
    @PostMapping
    public Review submitReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
}