/**
 * Spring Data JPA repository for executing data operations and searches on Movie entities.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    
	/**
     * Retrieves catalog movies belonging to a specified genre category string.
     *
     * @param category exact category name filter
     * @return list of matching Movie entities
     */
    List<Movie> findByCategory(String category);
    
    /**
     * Executes case-insensitive SQL LIKE search querying movie titles containing substring text
     *
     * @param keyword title substring search term
     * @return list of matching Movie entities
     */
    List<Movie> findByTitleContainingIgnoreCase(String keyword);
}
