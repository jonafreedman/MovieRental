/**
 * Repository interface providing database operations and tracking tools for rental Loans.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    // Custom query: Find all loans belonging to a specific user id (for Screen 4: User History)
    List<Loan> findByUserId(Long userId);
    
    // Custom query: Find all active loans across the store where return_date IS NULL (for Screen 5: Admin Tracker)
    List<Loan> findByReturnDateIsNull();
}
