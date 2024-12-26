package com.example.mastermind.services;

import com.example.mastermind.models.Game;
import com.example.mastermind.controller.GameController;
import com.example.mastermind.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateGameService {

    private final GameRepository gameRepository;
    private final static Logger logger = LoggerFactory.getLogger(GameController.class);

    public CreateGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public ResponseEntity<Game> createGame() {
        logger.info("Creating a new game");

        String numberCombination = generateRandomNumber();
        int maxAttempts = 10;

        Game game = new Game(numberCombination, maxAttempts);
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    private String generateRandomNumber() {
        StringBuilder randomNumber = new StringBuilder();
        String allowedCharacters = "01234567";

        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * allowedCharacters.length());
            randomNumber.append(allowedCharacters.charAt(index));
        }

        return randomNumber.toString();
    }

}
