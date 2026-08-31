/**
 * Entity class representing a user profile account.
 */
package com.rental.videorest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // Standard values: 'USER' or 'ADMIN'

    /**
     * Default no-argument constructor required by JPA specification.
     */
    public User() {}

    /**
     * Constructs a User entity with access credentials and system permission role.
     *
     * @param username unique account sign-in name
     * @param password account secret key
     * @param role authority profile level ('USER' / 'ADMIN')
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters

    /** @return user primary key ID */
    public Long getId() { return id; }
    /** @param id user primary key ID */
    public void setId(Long id) { this.id = id; }

    /** @return unique username handle */
    public String getUsername() { return username; }
    /** @param username unique username handle */
    public void setUsername(String username) { this.username = username; }

    /** @return user account password string */
    public String getPassword() { return password; }
    /** @param password user account password string */
    public void setPassword(String password) { this.password = password; }

    /** @return user authorization access role */
    public String getRole() { return role; }
    /** @param role user authorization access role */
    public void setRole(String role) { this.role = role; }
}