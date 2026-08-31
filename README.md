# Video Rental Store - RESTful API Backend

A Spring Boot RESTful web service backend for the Video Rental Store Application. This service provides endpoints for user authentication, catalog and inventory management, loan tracking, and movie reviews, persisting data via Spring Data JPA with an H2 database.

---

## Features & Architecture

* **Framework & Tooling:** Java 17+, Spring Boot 3.x, Maven / Gradle.
* **Database & Persistence:** Spring Data JPA with an embedded local file-based H2 database (`jdbc:h2:file:./data/rentaldb`).
* **Authentication & User Management:** User registration, password authentication, and role-based privilege handling (`USER` vs `ADMIN`).
* **Movie & Inventory Management:** Full catalog retrieval, genre filtering, title searching, and safe physical copy stock updates.
* **Loan Operations:** DVD checkout with real-time stock availability verification, return processing, active loans tracking, and customer rental history.
* **Review System:** 1–5 star rating submissions and customer critiques, restricted to users who have previously rented the movie.
* **Data Initializer:** Automatically seeds initial catalog stock and default administrative / user accounts on startup.

---

## Project Structure

```text
com.rental.videorest/
 RentalSystemApplication.java    # Main Spring Boot Application Entry Point
 DataInitializer.java            # Startup database seeder
 controller/                     # REST Endpoints
    LoanController.java         # Rental checkouts, returns, and tracking logs
    MovieController.java        # Catalog search, movie creation, and stock updates
    ReviewController.java       # Rating submissions and movie reviews
    UserController.java         # Authentication and account management
 model/                          # JPA Entities
    Loan.java                   # Transaction loan entity
    Movie.java                  # DVD inventory entity
    Review.java                 # Customer rating & feedback entity
    User.java                   # User profile entity
 repository/                     # Spring Data JPA Repositories
     LoanRepository.java
     MovieRepository.java
     ReviewRepository.java
     UserRepository.java
```

---

## API Endpoints Specification

### User Management & Auth (`/api/users`)
* `POST /api/users/register` — Register a new account (Default role: `USER`).
* `POST /api/users/login` — Authenticate user credentials.
* `GET /api/users` — Retrieve all registered users.
* `PUT /api/users/{id}/role?newRole={role}` — Update a user's role (`USER` / `ADMIN`).

### Movie Catalog (`/api/movies`)
* `GET /api/movies` — Fetch all catalog movies (Optional `?category=...` query).
* `GET /api/movies/search?keyword={text}` — Search movies by title.
* `POST /api/movies` — Add a new movie title to the catalog.
* `PUT /api/movies/{id}/stock?newCount={count}` — Update available stock for a movie.

### Loans & Transactions (`/api/loans`)
* `POST /api/loans/rent?userId={id}&movieId={id}` — Checkout a movie DVD.
* `PUT /api/loans/return/{loanId}` — Check back in a rented DVD.
* `GET /api/loans/user/{userId}` — Retrieve rental history for a given user.
* `GET /api/loans/active` — Retrieve all currently active/unreturned store loans.

### Reviews & Ratings (`/api/reviews`)
* `GET /api/reviews/movie/{movieId}` — Fetch all reviews for a specific movie.
* `POST /api/reviews` — Submit a star rating and comment (Requires user rental validation).

---

## Configuration & Database Setup

The backend configuration is defined in `src/main/resources/application.properties`:

```properties
spring.application.name=demo
spring.datasource.url=jdbc:h2:file:./data/rentaldb
spring.jpa.hibernate.ddl-auto=update
```

### Seed Credentials (Default Data)
Upon first run, `DataInitializer` populates the database with:
* **Admin Account:** Username: `admin` | Password: `admin123`
* **User Account:** Username: `john_doe` | Password: `password123`
* Default catalog movies across *Sci-Fi*, *Action*, and *Comedy*.

---

## Running the Backend

### Prerequisites
* JDK 17 or higher
* Maven / IDE (Eclipse, IntelliJ IDEA, VS Code)

### Execution
1. Open the backend project in Eclipse or your preferred IDE.
2. Run `RentalSystemApplication.java` as a **Java Application** or **Spring Boot App**.
3. The server will start on `http://localhost:8080`.
