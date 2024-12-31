package com.example.mastermind.exceptions;

public class InvalidDifficultyInputException extends RuntimeException{
    public InvalidDifficultyInputException(){
        super(GameErrorMessages.INVALID_DIFFICULTY_INPUT.getMessage());
    }
}
