package com.example.mastermind.exceptions;

/**
 * Custom exception that is thrown when a game id is not provided
 */
public class GameIdNotProvidedException extends RuntimeException {
    public GameIdNotProvidedException() {
        super(GameErrorMessages.GAME_ID_NOT_PROVIDED.toString());
    }
}
