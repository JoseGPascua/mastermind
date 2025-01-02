package com.example.mastermind.models;

/**
 * A data transfer object for {@link Game} that displays fields that will be sent back to the client
 */
public class GameDTO {
    private int id;
    private String numberCombination;
    private int attemptsLeft;
    private boolean isGameOver;

    public int getId() {
        return id;
    }

    public String getNumberCombination() {
        return numberCombination;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public GameDTO(Game game) {
        this.id = game.getId();
        this.numberCombination = game.getNumberCombination();
        this.attemptsLeft = game.getAttemptsLeft();
        this.isGameOver = game.isGameOver();
    }
}
