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

    public ResponseEntity<GameResponseDTO> handleUserInput(Integer gameId, String userInput) {

        logger.info("Creating guess");

        if (gameId == null) {
            throw new GameIdNotProvidedException();
        }

        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalGame.isEmpty()) {
            throw new GameNotFoundException();
        }

        Game currentGame = optionalGame.get();

        return processUserInput(currentGame, userInput);
    }

    private ResponseEntity<GameResponseDTO> processUserInput(Game currentGame, String userInput) {
        GameResponse response = new GameResponse();

        GameResponse validGameResponse = gameValidationService.validateGameStatus(currentGame, response);
        if (!validGameResponse.getHttpStatus().equals(HttpStatus.OK)) {
            return ResponseEntity.status(validGameResponse.getHttpStatus())
                    .body(new GameResponseDTO(validGameResponse));
        }

        GameResponse validUserInputResponse = gameValidationService.validateUserInput(currentGame, userInput, response);
        if (!validUserInputResponse.getHttpStatus().equals(HttpStatus.OK)) {
            return ResponseEntity.status(validGameResponse.getHttpStatus())
                    .body(new GameResponseDTO(validUserInputResponse));
        }

        return userInput.equals(currentGame.getNumberCombination())
                ? handleCorrectGuess(currentGame, userInput, response)
                : handleIncorrectGuess(currentGame, userInput, response);
    }

    private ResponseEntity<GameResponseDTO> handleCorrectGuess(Game currentGame, String userInput, GameResponse response) {
        currentGame.setGameOver(true);
        response.setUserInput(userInput)
                .setResponse("Congratulations, you won with the guess: " + userInput)
                .setAttemptsLeft(currentGame.getAttemptsLeft())
                .setHttpStatus(HttpStatus.CREATED);
        currentGame.addToResponseHistory(response);
        gameRepository.save(currentGame);

        return ResponseEntity.status(response.getHttpStatus()).body(new GameResponseDTO(response));
    }

    private ResponseEntity<GameResponseDTO> handleIncorrectGuess(Game currentGame, String userInput, GameResponse response) {
        GameResponse hint = guessCheckingService.compareGuess(currentGame.getNumberCombination(), userInput);
        int updatedAttemptsCount = currentGame.getAttemptsLeft() - 1;
        currentGame.setAttemptsLeft(updatedAttemptsCount);

        response.setUserInput(userInput)
                .setResponse(hint.getResponse())
                .setAttemptsLeft(updatedAttemptsCount)
                .setHttpStatus(HttpStatus.CREATED);
        currentGame.addToResponseHistory(response);
        gameRepository.save(currentGame);

        return ResponseEntity.status(response.getHttpStatus()).body(new GameResponseDTO(response));
    }
}
