package com.example.mastermind.controller;

import com.example.mastermind.models.Game;
import com.example.mastermind.services.CreateGameService;
import com.example.mastermind.services.CreateUserGuessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final CreateGameService createGameService;
    private final CreateUserGuessService createUserGuessService;

    public GameController(CreateGameService createGameService,
                          CreateUserGuessService createUserGuessService) {
        this.createGameService = createGameService;
        this.createUserGuessService = createUserGuessService;
    }

    @PostMapping("/create")
    public ResponseEntity<Game> createGame() {
        return createGameService.createGame();
    }

    @PostMapping("/createV2")
    public ResponseEntity<Game> manualCreateGame(@RequestParam String numberCombination) {
        return createGameService.manualCreateGame(numberCombination);
    }

    @PostMapping("/guess")
    public ResponseEntity<String> guess(@RequestParam Integer gameId, @RequestParam String userInput) {
        return createUserGuessService.handleUserInput(gameId, userInput);
    }
}
