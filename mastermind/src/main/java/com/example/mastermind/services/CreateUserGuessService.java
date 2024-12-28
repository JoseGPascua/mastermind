package com.example.mastermind.services;

import com.example.mastermind.models.Game;
import com.example.mastermind.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CreateUserGuessService {

    private final GameRepository gameRepository;
    private final GameValidationService gameValidationService;

    private final static Logger logger = LoggerFactory.getLogger(CreateUserGuessService.class);


    public CreateUserGuessService(GameRepository gameRepository,
                                  GameValidationService gameValidationService) {
        this.gameRepository = gameRepository;
        this.gameValidationService = gameValidationService;
    }

    public ResponseEntity<String> handleUserInput(Integer gameId, String userInput) {

        logger.info("Creating guess");

        if (gameId == null) {
            throw new RuntimeException("Game ID not provided");
        }

        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            throw new RuntimeException("Game not found");
        }

        Game currentGame = optionalGame.get();

        return processUserInput(currentGame, userInput);
    }

    private ResponseEntity<String> processUserInput(Game currentGame, String userInput) {

        ResponseEntity<String> inValidGameResponse = gameValidationService.validateGameStatus(currentGame);
        if (inValidGameResponse != null) {
            return inValidGameResponse;
        }

        ResponseEntity<String> invalidUserInputResponse = gameValidationService.validateUserInput(userInput);
        if (invalidUserInputResponse != null) {
            return invalidUserInputResponse;
        }

        return userInput.equals(currentGame.getNumberCombination())
                ? handleCorrectGuess(currentGame, userInput)
                : handleIncorrectGuess(currentGame, userInput);
    }

    private ResponseEntity<String> handleCorrectGuess(Game currentGame, String userInput) {
        currentGame.setGameOver(true);
        gameRepository.save(currentGame);
        return ResponseEntity.status(HttpStatus.CREATED).body("Congratulations, you won with the guess: " + userInput);
    }

    private ResponseEntity<String> handleIncorrectGuess(Game currentGame, String userInput) {
        String hint = compareGuess(currentGame.getNumberCombination(), userInput);
        int updatedAttemptsCount = currentGame.getAttemptsLeft() - 1;
        currentGame.setAttemptsLeft(updatedAttemptsCount);
        gameRepository.save(currentGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(hint);
    }

    private String compareGuess(String numberCombination, String userInput) {
        int correctlyPlacedNumbers = 0;
        int correctNumbers = 0;

        HashMap<Character, Integer> numberCombinationsMap = new HashMap<>();
        ArrayList<Character> userInputsList = new ArrayList<>();

        for (int i = 0; i < numberCombination.length(); i++) {
            if (numberCombination.charAt(i) == userInput.charAt(i)) {
                correctlyPlacedNumbers++;
            } else {
                if (numberCombinationsMap.containsKey(numberCombination.charAt(i))) {
                    numberCombinationsMap.put(numberCombination.charAt(i), numberCombinationsMap.get(numberCombination.charAt(i)) + 1);
                } else {
                    numberCombinationsMap.put(numberCombination.charAt(i), 1);
                }
                userInputsList.add(userInput.charAt(i));
            }
        }

        for (int i = 0; i < userInputsList.size(); i++) {
            char guessChar = userInputsList.get(i);
            if (numberCombinationsMap.containsKey(guessChar) && numberCombinationsMap.get(guessChar) > 0) {
                correctNumbers++;
                numberCombinationsMap.put(guessChar, numberCombinationsMap.get(guessChar) - 1);
            }
        }

        correctNumbers += correctlyPlacedNumbers;

        boolean allIncorrect = correctlyPlacedNumbers == 0 && correctNumbers == 0;
        return allIncorrect
                ? "All incorrect!"
                : "Correctly placed numbers: " + correctlyPlacedNumbers + " and correct numbers: " + correctNumbers;
    }
}
