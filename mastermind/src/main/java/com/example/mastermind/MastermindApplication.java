package com.example.mastermind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MastermindApplication {
	public static void main(String[] args) {
		SpringApplication.run(MastermindApplication.class, args);
		System.out.println("Welcome to Mastermind's Backend Service!");
	}
}
