package com.example.mastermind.models;

public class GameResponseDTO {
    private String userInput;
    private String response;
    private int attemptsLeft;

    public GameResponseDTO(GameResponse gameResponse) {
        this.userInput = gameResponse.getUserInput();
        this.response = gameResponse.getResponse();
        this.attemptsLeft = gameResponse.getAttemptsLeft();
    }

    public String getUserInput() {
        return userInput;
    }

    public String getResponse() {
        return response;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }
}
