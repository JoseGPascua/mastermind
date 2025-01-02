package com.example.mastermind.services;

import com.example.mastermind.exceptions.InvalidInputException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.randomNumberGenerator.RandomNumberGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service for creating a {@link Game}
 */
@Service
public class CreateGameService {

    private final GameRepository gameRepository;
    private final RandomNumberGeneratorService randomNumberGeneratorService;
    private final static Logger logger = LoggerFactory.getLogger(CreateGameService.class);
    private static final int MAX_ATTEMPTS = 10;
    private static final String DEFAULT_DIFFICULTY = "1";

    public CreateGameService(GameRepository gameRepository,
                             RandomNumberGeneratorService randomNumberGeneratorService) {
        this.gameRepository = gameRepository;
        this.randomNumberGeneratorService = randomNumberGeneratorService;
    }

    /**
     * Method to create a new {@link Game}. The new Game is constructed using a numberCombination that is provided by
     * the {@link RandomNumberGeneratorService} class. The new Game is then constructed with the numberCombination,
     * the number of attempts, and the difficulty level. After the Game is instantiated it is saved to the
     * gameRepository.
     *
     * @param difficulty A String representation of the difficulty level. This argument assists in changing which
     *                   numbers can be populated into the numberCombination variable.
     * @return A {@link ResponseEntity} containing a {@link GameDTO} to represent the new {@link Game}
     */
    public ResponseEntity<GameDTO> createGame(String difficulty) {
        logger.info("Attempting to create a new game with the difficulty of: {}", difficulty);

        String numberCombination = randomNumberGeneratorService.provideRandomNumber(difficulty);

        Game game = new Game(numberCombination, MAX_ATTEMPTS, difficulty);
        gameRepository.save(game);
        logger.info("Created a new game with the ID of: {}", game.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game));
    }

    /**
     * Method to create a new {@link Game}. The new Game is constructed using a numberCombination that is provided as an
     * argument. The new Game is then constructed with the numberCombination, the number of attempts, and a default
     * difficulty level of "1". After the Game is instantiated it is saved to the gameRepository. This endpoint is
     * typically used for testing, however it can also be applied for a 2-player style version of Mastermind where
     * one person creates a numberCombination and another person guesses.
     *
     * @param numberCombination A String representation of a user's input so that another user can guess this
     *                          numberCombination
     * @return A {@link ResponseEntity} containing a {@link GameDTO} to represent the new {@link Game}
     */
    public ResponseEntity<GameDTO> manualCreateGame(String numberCombination) {
        logger.info("Creating a new game using a manual user input");

        if (!numberCombination.matches("[0-7]{4}")) {
            logger.error("Failed to create a game using number combination: {}", numberCombination);
            throw new InvalidInputException("Please enter a number that only contains numbers from 0-7");
        }

        Game game = new Game(numberCombination, MAX_ATTEMPTS, DEFAULT_DIFFICULTY);
        gameRepository.save(game);
        logger.info("Manually created a new game with the ID of: {}", game.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game));
    }
}
