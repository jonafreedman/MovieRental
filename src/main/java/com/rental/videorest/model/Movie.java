/**
 * Entity class representing a physical movie/DVD entry in the catalog's inventory.
 */
package com.rental.videorest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(name = "total_copies", nullable = false)
    private int totalCopies;

    @Column(name = "available_copies", nullable = false)
    private int availableCopies;

    /**
     * Default no-argument constructor required by JPA specification.
     */
    public Movie() {}

    /**
     * Constructs a Movie instance with title metadata and initial inventory numbers.
     *
     * @param title movie title name
     * @param category primary genre category
     * @param totalCopies maximum stock count owned by store
     * @param availableCopies current count available on shelves
     */
    public Movie(String title, String category, int totalCopies, int availableCopies) {
        this.title = title;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    // Getters and Setters

    /** @return movie database primary key */
    public Long getId() { return id; }
    /** @param id movie database primary key */
    public void setId(Long id) { this.id = id; }

    /** @return title string */
    public String getTitle() { return title; }
    /** @param title title string */
    public void setTitle(String title) { this.title = title; }

    /** @return genre category string */
    public String getCategory() { return category; }
    /** @param category genre category string */
    public void setCategory(String category) { this.category = category; }

    /** @return total copies owned */
    public int getTotalCopies() { return totalCopies; }
    /** @param totalCopies total copies owned */
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    /** @return currently available inventory count */
    public int getAvailableCopies() { return availableCopies; }
    /** @param availableCopies currently available inventory count */
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}