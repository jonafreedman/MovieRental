/**
 * Entity class representing a user rating score and written comment for a specific movie title.
 */
package com.rental.videorest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private int rating; // Numeric range scale: 1 to 5

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    /**
     * Default no-argument constructor required by JPA specification.
     */
    public Review() {}

    /**
     * Constructs a Review instance with author, movie target, score rating, and feedback text.
     *
     * @param user author account
     * @param movie target movie
     * @param rating numerical rating score (1-5)
     * @param reviewText detailed review comment
     */
    public Review(User user, Movie movie, int rating, String reviewText) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    // Getters and Setters

    /** @return review primary key */
    public Long getId() { return id; }
    /** @param id review primary key */
    public void setId(Long id) { this.id = id; }

    /** @return author user account */
    public User getUser() { return user; }
    /** @param user author user account */
    public void setUser(User user) { this.user = user; }

    /** @return reviewed movie title */
    public Movie getMovie() { return movie; }
    /** @param movie reviewed movie title */
    public void setMovie(Movie movie) { this.movie = movie; }

    /** @return score rating from 1 to 5 */
    public int getRating() { return rating; }
    /** @param rating score rating from 1 to 5 */
    public void setRating(int rating) { this.rating = rating; }

    /** @return written feedback body */
    public String getReviewText() { return reviewText; }
    /** @param reviewText written feedback body */
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}