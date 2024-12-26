package com.example.mastermind.controller;

import com.example.mastermind.models.Game;
import com.example.mastermind.services.CreateGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final CreateGameService createGameService;

    public GameController(CreateGameService createGameService) {
        this.createGameService = createGameService;
    }

    // Testing if endpoint works
    @PostMapping("/create")
    public ResponseEntity<Game> createGame() {
        return createGameService.createGame();
    }
}
