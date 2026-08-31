/**
 * REST controller handling catalog search, genre filtering, keyword searches, movie creation, and stock updates.
 */
package com.rental.videorest.controller;

import com.rental.videorest.model.Movie;
import com.rental.videorest.repository.LoanRepository;
import com.rental.videorest.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private LoanRepository loanRepository;

    /**
     * Fetches all registered movies in the catalog, optionally filtered by category genre.
     *
     * @param category optional genre string filter 
     * @return 200 OK containing the list of matching Movie entities
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(movieRepository.findByCategory(category));
        }
        return ResponseEntity.ok(movieRepository.findAll());
    }

    /**
     * Searches for movies whose title contains the given keyword substring case-insensitively.
     *
     * @param keyword title substring query
     * @return 200 OK containing matched Movie entities
     */
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String keyword) {
        return ResponseEntity.ok(movieRepository.findByTitleContainingIgnoreCase(keyword));
    }

    /**
     * Registers a new movie entry into the catalog, ensuring valid parameters and initial copy counts
     *
     * @param movie entity populated from request JSON body
     * @return 200 OK containing saved Movie entity, or 400 BAD REQUEST / 500 INTERNAL SERVER ERROR on failure
     */
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        if (movie == null || movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie title cannot be empty.");
        }

        if (movie.getAvailableCopies() <= 0) {
            movie.setAvailableCopies(movie.getTotalCopies());
        }

        try {
            Movie savedMovie = movieRepository.save(movie);
            return ResponseEntity.ok(savedMovie);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to register movie asset in database.");
        }
    }

    /**
     * Updates physical available stock for a movie, validating against active out-on-loan copies.
     *
     * @param movieId target movie primary key
     * @param newCount requested updated physical copy total
     * @return 200 OK with updated Movie payload, or 400 BAD REQUEST if update violates active loans
     */
    @PutMapping("/{movieId}/stock")
    public ResponseEntity<?> updateMovieStock(@PathVariable Long movieId, @RequestParam int newCount) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie entity not found.");
        }

        long activeLoansCount = loanRepository.countByMovieIdAndReturnDateIsNull(movieId);

        if (newCount < activeLoansCount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Stock cannot be lower than active out-on-loan copies (" + activeLoansCount + ").");
        }

        // Adjust total and available copies 
        int currentlyRented = movie.getTotalCopies() - movie.getAvailableCopies();
        movie.setTotalCopies(newCount);
        movie.setAvailableCopies(newCount - currentlyRented);
        
        return ResponseEntity.ok(movieRepository.save(movie));
    }
}