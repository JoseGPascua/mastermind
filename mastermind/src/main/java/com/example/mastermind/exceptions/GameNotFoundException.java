package com.example.mastermind.exceptions;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException() {
        super(GameErrorMessages.GAME_NOT_FOUND.getMessage());
    }
}
