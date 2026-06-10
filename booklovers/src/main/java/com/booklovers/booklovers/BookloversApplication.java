package com.booklovers.booklovers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BookloversApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookloversApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		System.out.println("==================================");
		System.out.println("✅ Maycee Project Run Successful");
		System.out.println("==================================");
	}
}