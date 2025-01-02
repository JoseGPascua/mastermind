package com.example.mastermind.exceptions;

/**
 * Custom exception that is thrown when a user's input for making a guess is invalid
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String difficulty) {
        super(GameErrorMessages.INVALID_INPUT.getMessage() + difficulty);
    }
}
