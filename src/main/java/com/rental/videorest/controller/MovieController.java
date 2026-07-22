/**
 * REST Controller providing API endpoints for browsing, searching, and managing the movie catalog.
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

    // 1. Get all movies OR filter by category (Wrapped in ResponseEntity)
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(movieRepository.findByCategory(category));
        }
        return ResponseEntity.ok(movieRepository.findAll());
    }

    // 2. Search movies by keyword title (Wrapped in ResponseEntity)
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String keyword) {
        return ResponseEntity.ok(movieRepository.findByTitleContainingIgnoreCase(keyword));
    }

    // 3. Add a new movie title (Wrapped in ResponseEntity)
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        movie.setAvailableCopies(movie.getTotalCopies());
        return ResponseEntity.ok(movieRepository.save(movie));
    }
}