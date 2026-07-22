/**
 * Component class that automatically populates the database with sample movies 
 * and user accounts upon server startup for testing purposes.
 */
package com.rental.videorest;

import com.rental.videorest.model.Movie;
import com.rental.videorest.model.User;
import com.rental.videorest.repository.MovieRepository;
import com.rental.videorest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    // Spring automatically injects the repositories here
    public DataInitializer(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Only seed data if the database is currently empty
        if (movieRepository.count() == 0) {
            System.out.println("--> Seeding default data into the database...");

            // Add Default Movies (Title, Category, Total Copies, Available Copies)
            movieRepository.save(new Movie("The Matrix", "Sci-Fi", 5, 5));
            movieRepository.save(new Movie("Inception", "Sci-Fi", 3, 3));
            movieRepository.save(new Movie("Die Hard", "Action", 4, 4));
            movieRepository.save(new Movie("The Hangover", "Comedy", 2, 2));
            movieRepository.save(new Movie("Interstellar", "Sci-Fi", 3, 3));

            // Add Default Users (Username, Password, Role)
            userRepository.save(new User("john_doe", "password123", "USER"));
            userRepository.save(new User("admin_bob", "admin456", "ADMIN"));

            System.out.println("--> Database seeding complete!");
        }
    }
}
