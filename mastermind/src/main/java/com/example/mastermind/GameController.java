package com.example.mastermind;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    // Testing if endpoint works
    @PostMapping("/create")
    public ResponseEntity<Game> createGame() {
        String numberCombination = "1234";
        int maxAttempts = 10;

        Game game = new Game(numberCombination, maxAttempts);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
}
