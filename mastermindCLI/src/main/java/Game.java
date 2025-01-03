import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Game {

    private static String activeGameId;
    private static final String BASE_URL = "http://localhost:8080/";
    private final Scanner scanner = new Scanner(System.in);

    public Game() {}

    /**
     * Method to initialize the game in the main class
     */
    public void initializeGame() {
        displayObjective();
        displayGameOptions(scanner);
    }

    /**
     * Method to display the game options
     *
     * @param scanner Represents a scanner which helps in getting a user's input
     */
    private static void displayGameOptions(Scanner scanner) {
        while (true) {
            displayOptionsMessage();

            String option = "";
            boolean validInput = false;

            while (!validInput) {
                option = scanner.nextLine();
                if (option.matches("^[1-5]$")) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input, please select a number 1-5");
                    System.out.println("--------------------------------------------------------------");
                    displayOptionsMessage();
                }
            }

            int selectedOption = Integer.parseInt(option);

            switch (selectedOption) {
                case 1 -> createNewGame(scanner);
                case 2 -> createNewGuess(scanner);
                case 3 -> getFeedbackHistory();
                case 4 -> displayObjective();
                case 5 -> {
                    System.out.println("Thanks for playing, see you next time!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Helper method to display the message for the available game options
     */
    private static void displayOptionsMessage() {
        System.out.println("Please select an option:");
        System.out.println("1: Create New Game");
        System.out.println("2: Make a Guess");
        System.out.println("3: Check Previous Feedback");
        System.out.println("4: Provide Rules");
        System.out.println("5: Exit");
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * Method to create a new game
     *
     * @param scanner Represents a scanner which helps in getting a user's input
     */
    private static void createNewGame(Scanner scanner) {
        String difficulty;

        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Please enter a difficulty: ");
            difficulty = scanner.nextLine();

            if (difficulty.matches("^[123]$")) {
                break;
            } else {
                System.out.println("Invalid difficulty! Please input 1, 2, or 3");
            }
        }

        HttpClient client = HttpClient.newHttpClient();
        String url = BASE_URL + "new-game?difficulty=" + difficulty;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            GameDTO gameDTO = objectMapper.readValue(response.body(), GameDTO.class);
            activeGameId = String.valueOf(gameDTO.getId());

            System.out.println("--------------------------------------------------------------");
            System.out.println("Game Created with the Game ID: " + activeGameId);
            System.out.println("Attempts Left: " + gameDTO.getAttemptsLeft());
            System.out.println("--------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("The Game Server is currently offline, try again later...");
            System.out.println("--------------------------------------------------------------");
        }
    }

    /**
     * Method to create a new guess
     *
     * @param scanner Represents a scanner which helps in getting a user's input
     */
    private static void createNewGuess(Scanner scanner) {
        if (activeGameId == null) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("There is currently no game, please create a new game.");
            System.out.println("--------------------------------------------------------------");
            return;
        }

        System.out.println("Enter your guess: ");
        String guess = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        String url = BASE_URL + "new-guess?gameId=" + activeGameId + "&userInput=" + guess;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            GameResponseDTO gameResponseDTO = objectMapper.readValue(response.body(), GameResponseDTO.class);

            if (response.statusCode() == 201 || response.statusCode() == 400) {
                System.out.println("--------------------------------------------------------------");
                System.out.println("Your Guess: " + gameResponseDTO.getUserInput());
                System.out.println("Response: " + gameResponseDTO.getResponse());
                System.out.println("Attempts Left: " + gameResponseDTO.getAttemptsLeft());
                System.out.println("Total Score: " + gameResponseDTO.getTotalScore());
                System.out.println("--------------------------------------------------------------");

                if (gameResponseDTO.getResponse().contains("Congratulations")) {
                    promptUserToKeepPlaying(scanner);
                }
            }

            if (response.statusCode() == 403) {
                System.out.println("Response: " + gameResponseDTO.getResponse());
                System.out.println("Please create a new game");
                System.out.println("--------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error trying to make a guess: " + e.getMessage());
            System.out.println("--------------------------------------------------------------");
        }

    }

    /**
     * Method that prompts the user to keep playing or end the application. This method is only called
     * when the user wins the game.
     *
     * @param scanner Represents a scanner which helps in getting a user's input
     */
    private static void promptUserToKeepPlaying(Scanner scanner) {
        System.out.println("Would you like to play again? (y/n)");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "y" -> activeGameId = null;
            case "n" -> {
                System.out.println("Thanks for playing, see you next time!");
                System.exit(0);}
            default -> {
                System.out.println("Please enter a valid input");
                promptUserToKeepPlaying(scanner);
            }
        }
    }

    /**
     * Method to get the feedback history
     */
    private static void getFeedbackHistory() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Fetching Feedback History...");

        try {
            HttpClient client = HttpClient.newHttpClient();
            String uri = BASE_URL + "history?gameId=" + activeGameId;

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("Content-Type", "application/json")
                    .uri(URI.create(uri))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Please Create and Play a game first!");
                System.out.println("--------------------------------------------------------------");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            GameResponseHistoryDTO gameResponseHistoryDTO =
                    mapper.readValue(response.body(), GameResponseHistoryDTO.class);


            for (int i = 0; i < gameResponseHistoryDTO.getResponseHistory().size(); i++) {
                System.out.print(gameResponseHistoryDTO.getResponseHistory().get(i));
            }

            if (gameResponseHistoryDTO.getResponseHistory().isEmpty()) {
                System.out.print("No guesses have been made yet");
            }

            System.out.println("\n--------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("The Game Server is currently offline, try again later...");
            System.out.println("--------------------------------------------------------------");
        }
    }

    /**
     * Method that displays the game objective
     */
    private static void displayObjective() {
        System.out.println("Mastermind is a guessing game! Let's go over the objective:");
        System.out.println("After creating a game, the application will generate a random number");
        System.out.println("Your goal is to figure out that random number within ten tries!");
        System.out.println("Good luck and have fun!");
        System.out.println("--------------------------------------------------------------");
    }
}
