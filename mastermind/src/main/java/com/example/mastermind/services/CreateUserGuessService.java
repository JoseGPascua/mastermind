package com.example.mastermind.services;

import com.example.mastermind.exceptions.GameIdNotProvidedException;
import com.example.mastermind.exceptions.GameNotFoundException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameResponse;
import com.example.mastermind.models.GameResponseDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.utils.GameValidationService;
import com.example.mastermind.services.utils.GuessCheckingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for creating the user's guess and returning feedback in the form of a {@link GameResponseDTO}
 */
@Service
public class CreateUserGuessService {

    private final GameRepository gameRepository;
    private final GameValidationService gameValidationService;
    private final GuessCheckingService guessCheckingService;
    private final static Logger logger = LoggerFactory.getLogger(CreateUserGuessService.class);

    public CreateUserGuessService(GameRepository gameRepository,
                                  GameValidationService gameValidationService, GuessCheckingService guessCheckingService) {
        this.gameRepository = gameRepository;
        this.gameValidationService = gameValidationService;
        this.guessCheckingService = guessCheckingService;
    }

    /**
     * Method that first handles the user's input by first making sure there is an existing game before processing
     * the user's input to a Game's numberCombination. If a Game does exist, the user's input will then be sent to be
     * processed with the given Game.
     *
     * @param gameId An Integer representing the ID of a Game, it will be used to search the gameRepository.
     * @param userInput A String representing the user's guess.
     * @return A {@link GameResponseDTO} that provides a response to the client.
     */
    public ResponseEntity<GameResponseDTO> handleUserInput(Integer gameId, String userInput) {

        logger.info("Attempting to create a guess using the input: {}", userInput);

        if (gameId == null) {
            logger.warn("Game ID cannot be null...");
            throw new GameIdNotProvidedException();
        }

        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            logger.warn("Game could not be found with the provided Game ID...");
            throw new GameNotFoundException();
        }

        Game currentGame = optionalGame.get();

        return processUserInput(currentGame, userInput);
    }

    /**
     * Method that processes the user input. However, it must first validate that the Game is ongoing and playable, if
     * the Game is playable, it will then validate that the userInput can be used as a guess for the game. If all
     * validations pass, userInput is compared to the Game's numberCombination, if userInput is correct it will be sent
     * to a method that handle's a correct guess, otherwise it will send it to a method that handle's incorrect guesses.
     *
     * @param currentGame A {@link Game} that contains a numberCombination that the user must guess correctly
     * @param userInput A String representing the user's guess.
     * @return A {@link GameResponseDTO} that provides a response to the client.
     */
    private ResponseEntity<GameResponseDTO> processUserInput(Game currentGame, String userInput) {
        GameResponse response = new GameResponse();

        GameResponse validGameResponse = gameValidationService.validateGameStatus(currentGame, response);
        if (!validGameResponse.getHttpStatus().equals(HttpStatus.OK)) {
            return ResponseEntity.status(validGameResponse.getHttpStatus())
                    .body(new GameResponseDTO(validGameResponse));
        }
        logger.info("Game Status validated successfully...");

        GameResponse validUserInputResponse = gameValidationService.validateUserInput(currentGame, userInput, response);
        if (!validUserInputResponse.getHttpStatus().equals(HttpStatus.OK)) {
            return ResponseEntity.status(validUserInputResponse.getHttpStatus())
                    .body(new GameResponseDTO(validUserInputResponse));
        }
        logger.info("User Input validated successfully...");

        return userInput.equals(currentGame.getNumberCombination())
                ? handleCorrectGuess(currentGame, userInput, response)
                : handleIncorrectGuess(currentGame, userInput, response);
    }

    /**
     * Method that handles a correct guess. It provides a game-winning response and updates the game's status to over.
     *
     * @param currentGame A {@link Game} that contains a numberCombination that the user must guess correctly.
     * @param userInput A String representing the user's guess.
     * @param response A {@link GameResponse} that will be populated to create a readable game-winning response.
     * @return A {@link GameResponseDTO} that provides a response to the client.
     */
    private ResponseEntity<GameResponseDTO> handleCorrectGuess(Game currentGame, String userInput, GameResponse response) {
        logger.info("User has won the game");
        currentGame.setGameOver(true).setAttemptsLeft(currentGame.useAttempt());
        response.setUserInput(userInput)
                .setResponse("Congratulations, you won with the guess: " + userInput)
                .setTotalScore(currentGame.getScore())
                .setAttemptsLeft(currentGame.getAttemptsLeft())
                .setHttpStatus(HttpStatus.CREATED);
        currentGame.addToResponseHistory(response);
        gameRepository.save(currentGame);
        return ResponseEntity.status(response.getHttpStatus()).body(new GameResponseDTO(response));
    }

    /**
     * Method that handles an incorrect guess. The numberCombination and the userInput are sent to the
     * {@link GuessCheckingService} to provide a hint and use it to populate the response that will be sent back to the
     * client.
     *
     * @param currentGame A {@link Game} that contains a numberCombination that the user must guess correctly.
     * @param userInput A String representing the user's guess.
     * @param response A {@link GameResponse} that will be populated to create a readable hint response.
     * @return A {@link GameResponseDTO} that provides a response to the client.
     */
    private ResponseEntity<GameResponseDTO> handleIncorrectGuess(Game currentGame, String userInput, GameResponse response) {
        logger.info("User input is incorrect, proceeding to generate a hint...");
        GameResponse hint = guessCheckingService.compareGuess(currentGame.getNumberCombination(), userInput);

        currentGame.updateScore(hint.getScoreDeduction()).setAttemptsLeft(currentGame.useAttempt());

        response.setUserInput(userInput)
                .setResponse(hint.getResponse())
                .setTotalScore(currentGame.getScore())
                .setAttemptsLeft(currentGame.getAttemptsLeft())
                .setHttpStatus(HttpStatus.CREATED);
        currentGame.addToResponseHistory(response);
        gameRepository.save(currentGame);
        return ResponseEntity.status(response.getHttpStatus()).body(new GameResponseDTO(response));
    }
}
