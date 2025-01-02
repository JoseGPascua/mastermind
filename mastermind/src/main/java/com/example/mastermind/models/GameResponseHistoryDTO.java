package com.example.mastermind.models;

import java.util.List;

/**
 * A data transfer object that displays a {@link List} of {@link GameResponseDTO} which will be sent back to the client
 */
public class GameResponseHistoryDTO {

    private int gameId;
    private List<GameResponseDTO> responseHistory;

    public GameResponseHistoryDTO(Game game) {
        this.gameId = game.getId();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public List<GameResponseDTO> getResponseHistory() {
        return responseHistory;
    }

    public void setResponseHistory(List<GameResponseDTO> responseHistory) {
        this.responseHistory = responseHistory;
    }

}
