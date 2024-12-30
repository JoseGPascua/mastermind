package com.example.mastermind.controller;

import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameDTO;
import com.example.mastermind.models.GameResponseDTO;
import com.example.mastermind.models.GameResponseHistoryDTO;
import com.example.mastermind.services.CreateGameService;
import com.example.mastermind.services.CreateUserGuessService;
import com.example.mastermind.services.GetGameHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final CreateGameService createGameService;
    private final CreateUserGuessService createUserGuessService;
    private final GetGameHistoryService getGameHistoryService;

    public GameController(CreateGameService createGameService,
                          CreateUserGuessService createUserGuessService,
                          GetGameHistoryService getGameHistoryService) {
        this.createGameService = createGameService;
        this.createUserGuessService = createUserGuessService;
        this.getGameHistoryService = getGameHistoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<GameDTO> createGame() {
        return createGameService.createGame();
    }

    @PostMapping("/createV2")
    public ResponseEntity<GameDTO> manualCreateGame(@RequestParam String numberCombination) {
        return createGameService.manualCreateGame(numberCombination);
    }

    @PostMapping("/guess")
    public ResponseEntity<GameResponseDTO> guess(@RequestParam Integer gameId, @RequestParam String userInput) {
        return createUserGuessService.handleUserInput(gameId, userInput);
    }

    @GetMapping("/history")
    public ResponseEntity<GameResponseHistoryDTO> getHistory(@RequestParam Integer gameId) {
        return getGameHistoryService.getGameHistory(gameId);
    }
}
