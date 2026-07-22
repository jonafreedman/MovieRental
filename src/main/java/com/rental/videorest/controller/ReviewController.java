/**
 * REST Controller managing user-submitted community ratings and comments.
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

    // 1. Get all customer feedback submitted against a single title (Screen 3)
    @GetMapping("/movie/{movieId}")
    public List<Review> getMovieReviews(@PathVariable Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    // 2. Submit a new written review & score rating (Screen 3)
    @PostMapping
    public Review submitReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
}