package com.example.mastermind.services;

import com.example.mastermind.models.Game;
import com.example.mastermind.controller.GameController;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.randomNumberGenerator.LocalNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateGameService {

    private final GameRepository gameRepository;
    private final LocalNumberGenerator localNumberGenerator;

    private final static Logger logger = LoggerFactory.getLogger(GameController.class);

    public CreateGameService(GameRepository gameRepository, LocalNumberGenerator localNumberGenerator) {
        this.gameRepository = gameRepository;
        this.localNumberGenerator = localNumberGenerator;
    }

    public ResponseEntity<Game> createGame() {
        logger.info("Creating a new game");

        String numberCombination = localNumberGenerator.generateRandomNumber();
        int maxAttempts = 10;

        Game game = new Game(numberCombination, maxAttempts);
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

}
