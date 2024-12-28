package com.example.mastermind.services;

import com.example.mastermind.models.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameValidationService {

    /**
     * Method to validate the current game's status. If the game is over, or currently out of attempts, we return
     * a bad request, otherwise we return null
     *
     * @param currentGame is the Game object that is being validated
     *
     * @return null if game status is not over or out of attempts
     */
    public ResponseEntity<String> validateGameStatus(Game currentGame) {
        if (currentGame.isGameOver()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game is already over");
        }

        if (currentGame.getAttemptsLeft() <= 0) {
            currentGame.setGameOver(true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Out of attempts");
        }
        return null;
    }

    /**
     * Method to validate if the user's input (guess for the number combination) only contains characters from
     * 0 to 7 and is only 4-digits long
     *
     * @param userInput is the user's guess to be validated
     *
     * @return null if the user's guess is 4-digits long and only contains characters 0 to 7
     */
    public ResponseEntity<String> validateUserInput(String userInput) {
        if (!userInput.matches("[0-7]{4}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user input");
        }
        return null;
    }
}
