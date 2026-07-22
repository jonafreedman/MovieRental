/**
 * Repository interface providing database operations and custom searches for the Movie entity.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    // Custom query: Finds movies that match a category (e.g., "Sci-Fi")
    List<Movie> findByCategory(String category);
    
    // Custom query: Text-based search that ignores capital letters (e.g., searching "matrix" finds "The Matrix")
    List<Movie> findByTitleContainingIgnoreCase(String keyword);
}
