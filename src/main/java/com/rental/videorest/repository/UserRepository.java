/**
 * Spring Data JPA repository for managing User entity accounts and lookup authentication requests.
 */
package com.rental.videorest.repository;

import com.rental.videorest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
	/**
     * Looks up a user account record matching the provided unique username.
     *
     * @param username username key string
     * @return an Optional containing the matched User entity if found
     */
    Optional<User> findByUsername(String username);
}
