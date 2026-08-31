/**
 * Spring Data JPA repository for executing data operations on Loan entities.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    
	/**
     * Retrieves all loan transactions created by a specific user account ID.
     *
     * @param userId unique identifier of the user account
     * @return list of matching Loan records
     */
    List<Loan> findByUserId(Long userId);
    
    /**
     * Spring Derived Query that automatically translates 'ReturnDateIsNull' into SQL 'WHERE return_date IS NULL'
     *
     * @return list of active outstanding loans currently in customer possession
     */
    List<Loan> findByReturnDateIsNull();
    
    /**
     * Counts active unreturned loans for a specific movie in the repository.
     *
     * @param movieId unique identifier of the movie asset
     * @return total count of physical copies currently checked out by customers
     */
    long countByMovieIdAndReturnDateIsNull(Long movieId);
}
