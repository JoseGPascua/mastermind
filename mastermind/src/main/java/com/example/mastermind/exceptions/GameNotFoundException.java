package com.example.mastermind.exceptions;

/**
 * Custom exception that is thrown when a game cannot be found in the game repository
 */
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {
        super(GameErrorMessages.GAME_NOT_FOUND.getMessage());
    }
}
