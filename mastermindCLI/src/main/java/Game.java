import java.util.Scanner;

public class Game {

    private static final String BASE_URL = "http://localhost:8080/";
    private final Scanner scanner = new Scanner(System.in);

    public Game() {}

    public void initializeGame() {
        displayGameOptions(scanner);
    }

    private static void displayGameOptions(Scanner scanner) {
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Please select an option:");
            System.out.println("1: Create New Game");
            System.out.println("2: Make a Guess");
            System.out.println("3: Check Previous Feedback");
            System.out.println("4: Exit");
            System.out.println("--------------------------------------------------------------");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createNewGame(scanner);
                case 2 -> createNewGuess(scanner);
                case 3 -> getFeedbackHistory();
                case 4 -> {
                    System.out.println("Thanks for playing, see you next time!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void createNewGame(Scanner scanner) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Selected Option 1");
    }

    private static void createNewGuess(Scanner scanner) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Selected Option 2");
    }

    private static void getFeedbackHistory() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Selected Option 3");
    }
}
