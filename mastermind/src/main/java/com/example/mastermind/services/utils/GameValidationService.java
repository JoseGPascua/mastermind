package com.example.mastermind.services.utils;

import com.example.mastermind.exceptions.InvalidInputException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GameValidationService {

    private final static Logger logger = LoggerFactory.getLogger(GameValidationService.class);

    /**
     * Method to validate the current game's status. If the game is over, or currently out of attempts, we return
     * a bad request, otherwise we return null
     *
     * @param currentGame is the Game object that is being validated
     * @param response is the response that will be populated with information about the game, if the game gets past
     *                 the validations, the response will only be returned as an HttpStatus
     *
     * @return a GameResponse, if it fails the validation it will return the correct number combination and attempts
     * remaining. If the validation is passed it will return a GameResponse with an HttpStatus of OK.
     */
    public GameResponse validateGameStatus(Game currentGame, GameResponse response) {
        logger.info("Validating game status");
        if (currentGame.isGameOver()) {
            return gameIsOverResponse(currentGame, response);
        }

        if (currentGame.getAttemptsLeft() <= 0) {
            currentGame.setGameOver(true);
            return gameIsOverResponse(currentGame, response);
        }

        return response.setHttpStatus(HttpStatus.OK);
    }

    /**
     * Helper method that updates the data of a GameResponse object to display a Game Over type of message
     *
     * @param currentGame is the Game object that has failed the validation checks, its numberCombination and attempts
     *                    remaining are used to populate the response
     *
     * @return a GameResponse with a Game Over style message
     */
    private GameResponse gameIsOverResponse(Game currentGame, GameResponse response) {
        return response.setResponse("Game is over, the correct answer: " + currentGame.getNumberCombination())
                .setAttemptsLeft(currentGame.getAttemptsLeft())
                .setHttpStatus(HttpStatus.FORBIDDEN);
    }

    /**
     * Method to validate if the user's input (guess for the number combination) only contains characters from
     * 0 to 7 and is only 4-digits long
     *
     * @param currentGame is the game that is being played, its data will be used to get the attempts remaining
     * @param userInput is the user's guess to be validated
     * @param response is used to provide the user with information if their guess is invalid, if the validation is
     *                 passed, the response will just be sest as an HttpStatus of OK
     *
     * @return a GameResponse that tells the user if their input is invalid, otherwise just an HttpStatus of OK
     */
    public GameResponse validateUserInput(Game currentGame, String userInput, GameResponse response) {
        logger.info("Validating user input");

        try {
            isValidCode(userInput);
            response.setHttpStatus((HttpStatus.OK));
        } catch (InvalidInputException exception) {
            response.setResponse(exception.getMessage())
                    .setUserInput(userInput)
                    .setAttemptsLeft(currentGame.getAttemptsLeft())
                    .setHttpStatus(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Helper method for validateUserInput to check if the user's input (guess for the number combination) only contains
     * characters from 0 to 7 and is only 4-digits long
     *
     * @param userInput is the user's guess to be validated
     */
    private void isValidCode(String userInput) {
        if (!userInput.matches("[0-7]{4}")) {
            throw new InvalidInputException();
        }
    }
}
