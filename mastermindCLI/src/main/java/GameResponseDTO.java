public class GameResponseDTO {
    private String userInput;
    private String response;
    private int attemptsLeft;
    private int totalScore;

    public String getUserInput() {
        return userInput;
    }

    public String getResponse() {
        return response;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return "\n" +
                "User Input: " + userInput + ", " +
                "Response: " + response + ", " +
                "AttemptsLeft: " + attemptsLeft + ", " +
                "TotalScore: " + totalScore;
    }
}
