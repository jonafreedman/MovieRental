/**
 * Spring Data JPA repository for executing data operations on Review entities.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
	/**
     * Retrieves all community feedback reviews submitted for a specific movie.
     *
     * @param movieId target movie identifier
     * @return list of associated Review entities
     */
    List<Review> findByMovieId(Long movieId);
}
