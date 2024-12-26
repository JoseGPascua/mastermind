package com.example.mastermind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameRepository gameRepository;
    private final static Logger logger = LoggerFactory.getLogger(GameController.class);

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Testing if endpoint works
    @PostMapping("/create")
    public ResponseEntity<Game> createGame() {
        logger.info("Creating a new game");

        String numberCombination = "1234";
        int maxAttempts = 10;

        Game game = new Game(numberCombination, maxAttempts);
        gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
}
