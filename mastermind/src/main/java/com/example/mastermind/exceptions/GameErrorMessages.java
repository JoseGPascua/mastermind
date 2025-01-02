package com.example.mastermind.exceptions;

/**
 * Class that represents the error messages for the custom exceptions in the game
 */
public enum GameErrorMessages {

    GAME_ID_NOT_PROVIDED("Game ID not provided!"),
    GAME_NOT_FOUND("Game not found!"),
    INVALID_INPUT("Invalid input! Please enter a 4-digit number using characters between: "),
    INVALID_DIFFICULTY_INPUT("Invalid difficulty level, please input 1, 2, or 3");

    private final String message;

    GameErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
