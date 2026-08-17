/**
 * Entity class representing a rental loan transaction between a user and a physical movie copy.
 */
package com.rental.videorest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @Column(name = "return_date") 
    private LocalDateTime returnDate; // Nullable: null means the DVD is currently checked out

    /**
     * Default no-argument constructor required by JPA specification.
     */
    public Loan() {}

    /**
     * Constructs an active Loan record initialized with a checkout timestamp.
     *
     * @param user borrower account
     * @param movie rented movie entity
     * @param rentDate timestamp of the transaction
     */
    public Loan(User user, Movie movie, LocalDateTime rentDate) {
        this.user = user;
        this.movie = movie;
        this.rentDate = rentDate;
        this.returnDate = null; // Stays null until admin checks it back in
    }

    // Getters and Setters

    /** @return loan database primary key */
    public Long getId() { return id; }
    /** @param id loan database primary key */
    public void setId(Long id) { this.id = id; }

    /** @return user associated with this loan */
    public User getUser() { return user; }
    /** @param user user associated with this loan */
    public void setUser(User user) { this.user = user; }

    /** @return movie associated with this loan */
    public Movie getMovie() { return movie; }
    /** @param movie movie associated with this loan */
    public void setMovie(Movie movie) { this.movie = movie; }

    /** @return rental initiation date and time */
    public LocalDateTime getRentDate() { return rentDate; }
    /** @param rentDate rental initiation date and time */
    public void setRentDate(LocalDateTime rentDate) { this.rentDate = rentDate; }

    /** @return check-in date and time, or null if outstanding */
    public LocalDateTime getReturnDate() { return returnDate; }
    /** @param returnDate check-in date and time */
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
}
