package com.example.mastermind.exceptions;

public class GameIdNotProvidedException extends RuntimeException {
    public GameIdNotProvidedException() {
        super(GameErrorMessages.GAME_ID_NOT_PROVIDED.toString());
    }
}
