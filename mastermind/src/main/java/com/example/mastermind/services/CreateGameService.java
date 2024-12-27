package com.example.mastermind.services;

import com.example.mastermind.models.Game;
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

    public ResponseEntity<Game> createGame() {
        logger.info("Creating a new game");

        String numberCombination = randomNumberGeneratorService.provideRandomNumber();
        int maxAttempts = 10;

        Game game = new Game(numberCombination, maxAttempts);
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

}
