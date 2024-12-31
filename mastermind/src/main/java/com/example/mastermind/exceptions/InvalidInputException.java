package com.example.mastermind.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String difficulty) {
        super(GameErrorMessages.INVALID_INPUT.getMessage() + difficulty);
    }
}
