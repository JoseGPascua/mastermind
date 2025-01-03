import java.util.List;

public class GameResponseHistoryDTO {

    private int gameId;
    private List<GameResponseDTO> responseHistory;

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