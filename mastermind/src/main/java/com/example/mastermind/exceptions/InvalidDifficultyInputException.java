package com.example.mastermind.exceptions;

/**
 * Custom exception that is thrown when an invalid difficulty is used as an input
 */
public class InvalidDifficultyInputException extends RuntimeException{
    public InvalidDifficultyInputException(){
        super(GameErrorMessages.INVALID_DIFFICULTY_INPUT.getMessage());
    }
}
