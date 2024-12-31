package com.example.mastermind.exceptions;

public enum GameErrorMessages {

    GAME_ID_NOT_PROVIDED("Game ID not provided!"),
    GAME_NOT_FOUND("Game not found!"),
    INVALID_INPUT("Invalid input! Please enter a 4-digit number using characters between 0 and 7!");

    private final String message;

    GameErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
