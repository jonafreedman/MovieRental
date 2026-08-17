/**
 * Main entry point for starting the Video Rental RESTful web service API.
 */
package com.rental.videorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentalSystemApplication {

	/**
     * Boots the embedded Tomcat web server and initializes the Spring application context.
     *
     * @param args command-line execution arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(RentalSystemApplication.class, args);
	}

}
