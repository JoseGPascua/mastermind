package com.example.mastermind.services;

import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.randomNumberGenerator.RandomNumberGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateGameService {

    private final GameRepository gameRepository;
    private final RandomNumberGeneratorService randomNumberGeneratorService;
    private final static Logger logger = LoggerFactory.getLogger(CreateGameService.class);

    public CreateGameService(GameRepository gameRepository,
                             RandomNumberGeneratorService randomNumberGeneratorService) {
        this.gameRepository = gameRepository;
        this.randomNumberGeneratorService = randomNumberGeneratorService;
    }

    public ResponseEntity<GameDTO> createGame(String difficulty) {
        logger.info("Creating a new game");

        String numberCombination = randomNumberGeneratorService.provideRandomNumber(difficulty);
        int maxAttempts = 10;

        Game game = new Game(numberCombination, maxAttempts, difficulty);
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game));
    }

    public ResponseEntity<GameDTO> manualCreateGame(String numberCombination) {
        logger.info("Creating a new game using a manual user input");
        int maxAttempts = 10;

        if (!numberCombination.matches("[0-7]{4}")) {
            throw new RuntimeException("Invalid number combination");
        }

        Game game = new Game(numberCombination, maxAttempts, "1");
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game));
    }

}
