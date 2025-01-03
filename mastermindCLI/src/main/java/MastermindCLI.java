public class MastermindCLI {

    public static void main(String[] args) {
        System.out.println("Welcome to Mastermind - A Guessing Game!");
        System.out.println("--------------------------------------------------------------");

        Game game = new Game();
        game.initializeGame();
    }
}
