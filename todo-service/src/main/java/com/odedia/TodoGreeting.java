package com.odedia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class TodoGreeting {
	private String greeting;
	
	public TodoGreeting(@Value("${greeting:Hello}") String greeting) {
		this.greeting = greeting;
	}
	
	@GetMapping("/greeting")
	String greeting() {
		log.info("Returning info from service...");
		return greeting;
	}
}
