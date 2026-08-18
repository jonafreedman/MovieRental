/**
 * REST controller handling catalog discovery, genre filtering, keyword searches, and movie creation.
 */
package com.rental.videorest.controller;

import com.rental.videorest.model.Movie;
import com.rental.videorest.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Now explicitly used below!
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Fetches all registered movies in the catalog, optionally filtered by category genre.
     *
     * @param category optional genre string filter (e.g., "Sci-Fi")
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
     * Registers a new movie entry into the catalog and sets initial available copies equal to total copies.
     *
     * @param movie movie entity populated from request JSON body
     * @return 200 OK containing the persisted Movie entity
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if (movie.getAvailableCopies() <= 0) {
            movie.setAvailableCopies(movie.getTotalCopies());
        }
        return ResponseEntity.ok(movieRepository.save(movie));
    }
}