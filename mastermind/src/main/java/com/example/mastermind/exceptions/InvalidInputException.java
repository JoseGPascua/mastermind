package com.example.mastermind.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
        super(GameErrorMessages.INVALID_INPUT.getMessage());
    }
}
