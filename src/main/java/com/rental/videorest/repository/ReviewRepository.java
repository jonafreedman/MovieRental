/**
 * Repository interface providing database operations for user Reviews.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Custom query: Find all community reviews left on a specific movie id (for Screen 3: Details Page)
    List<Review> findByMovieId(Long movieId);
}
