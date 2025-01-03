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

    public void setId(int id) {
        this.id = id;
    }

    public void setNumberCombination(String numberCombination) {
        this.numberCombination = numberCombination;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
