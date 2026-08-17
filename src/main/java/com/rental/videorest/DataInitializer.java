/**
 * Database startup component that populates initial inventory stock and default user accounts.
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

    /**
     * Constructs the initializer with required repository dependencies.
     * 
     * @param movieRepository repository for executing movie persistence
     * @param userRepository repository for executing user persistence
     */
    public DataInitializer(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    /**
     * Executes seed logic immediately after the Spring Boot application context is fully loaded.
     *
     * @param args command-line arguments passed to the application
     * @throws Exception if database seeding fails
     */
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
