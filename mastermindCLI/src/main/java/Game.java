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

    public void initializeGame() {
        displayObjective();
        displayGameOptions(scanner);
    }

    private static void displayGameOptions(Scanner scanner) {
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("1: Create New Game");
            System.out.println("2: Make a Guess");
            System.out.println("3: Check Previous Feedback");
            System.out.println("4: Provide Rules");
            System.out.println("5: Exit");
            System.out.println("--------------------------------------------------------------");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
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

    private static void createNewGame(Scanner scanner) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Please enter a difficulty: ");
        String difficulty = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        String url = BASE_URL + "new-game?difficulty=" + difficulty;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Content-Type", "application/json")
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                System.out.println("--------------------------------------------------------------");
                System.out.println("Something went wrong, status code: " + response.statusCode());
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            GameDTO gameDTO = objectMapper.readValue(response.body(), GameDTO.class);
            activeGameId = String.valueOf(gameDTO.getId());

            System.out.println("--------------------------------------------------------------");
            System.out.println("Game Created with the Game ID: " + activeGameId);
            System.out.println("Number Combination: " + gameDTO.getNumberCombination());
            System.out.println("Attempts Left: " + gameDTO.getAttemptsLeft());
            System.out.println("--------------------------------------------------------------");


        } catch (Exception e) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Something went wrong: " + e.getMessage());
            System.out.println("--------------------------------------------------------------");
        }
    }

    private static void createNewGuess(Scanner scanner) {
        if (activeGameId == null) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("There is currently no game, please create a new game.");
            System.out.println("\n--------------------------------------------------------------");
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

    private static void getFeedbackHistory() {
        System.out.println("--------------------------------------------------------------");
        System.out.print("Fetching Feedback History: ");

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
                System.out.println("\n--------------------------------------------------------------");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            GameResponseHistoryDTO gameResponseHistoryDTO =
                    mapper.readValue(response.body(), GameResponseHistoryDTO.class);


            for (int i = 0; i < gameResponseHistoryDTO.getResponseHistory().size(); i++) {
                System.out.print(gameResponseHistoryDTO.getResponseHistory().get(i));
            }

            if (gameResponseHistoryDTO.getResponseHistory().isEmpty()) {
                System.out.println("No guesses have been made yet");
            }

            System.out.println("\n--------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error retrieving feedback history: " + e.getMessage());
            System.out.println("--------------------------------------------------------------");
        }
    }

    private static void displayObjective() {
        System.out.println("Mastermind is a guessing game! Let's go over the objective:");
        System.out.println("After creating a game, the application will generate a random number");
        System.out.println("Your goal is to figure out that random number within ten tries!");
        System.out.println("Good luck and have fun!");
        System.out.println("--------------------------------------------------------------");
    }
}
