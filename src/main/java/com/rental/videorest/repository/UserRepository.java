/**
 * Repository interface providing database operations for the User entity.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Custom query: Spring automatically figures out how to find a user by their username
    Optional<User> findByUsername(String username);
}
