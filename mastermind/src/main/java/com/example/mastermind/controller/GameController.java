package com.example.mastermind.controller;

import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameDTO;
import com.example.mastermind.models.GameResponse;
import com.example.mastermind.models.GameResponseDTO;
import com.example.mastermind.models.GameResponseHistoryDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.CreateGameService;
import com.example.mastermind.services.CreateUserGuessService;
import com.example.mastermind.services.GetGameHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing Game related features.
 */
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

    /**
     * Creates a {@link Game} entity with the given difficulty level
     *
     * @param difficulty A String representation of the difficulty modes, the value of difficulty can only be
     *                   1, 2, or 3 and the range for which the number combinations will change depending on the
     *                   chosen difficulty. For example:
     *                   - "1" will allow the number combination to be 0-7
     *                   - "2" will allow the number combination to be 0-8
     *                   - "3" will allow the number combination to be 0-9
     * @return A {@link ResponseEntity} containing a {@link GameDTO} that represents the game that has been created
     */
    @PostMapping("/create")
    public ResponseEntity<GameDTO> createGame(@RequestParam String difficulty) {
        return createGameService.createGame(difficulty);
    }

    /**
     * Creates a {@link Game} entity with the given number combination
     *
     * @param numberCombination A String representation of the random number that the user of the application must
     *                          guess in order to "win" the game
     * @return A {@link ResponseEntity} containing a {@link GameDTO} that represents the game that has been created
     */
    @PostMapping("/createV2")
    public ResponseEntity<GameDTO> manualCreateGame(@RequestParam String numberCombination) {
        return createGameService.manualCreateGame(numberCombination);
    }

    /**
     * Creates a {@link GameResponse} entity that will be used to represent the feedback the user will receive after
     * sending a "guess".
     *
     * @param gameId An Integer that will be used to help find the {@link Game} within the {@link GameRepository}
     * @param userInput A String representation of the user's guess for the random number combination. The userInput
     *                  is validated and then processed through an algorithm to produce hints based on the correct
     *                  placements and correct numbers the userInput has when compared to the Game's numberCombination
     * @return A {@link ResponseEntity} containing a {@link GameResponseDTO} that represents the feedback that is
     * sent back to the user.
     */
    @PostMapping("/guess")
    public ResponseEntity<GameResponseDTO> guess(@RequestParam Integer gameId, @RequestParam String userInput) {
        return createUserGuessService.handleUserInput(gameId, userInput);
    }

    /**
     * Creates a {@link GameResponseHistoryDTO}, which contains a list of Game's GameResponses.
     *
     * @param gameId An Integer that will be used to help find the {@link Game} within the {@link GameRepository}
     * @return A {@link ResponseEntity} containing a {@link GameResponseHistoryDTO} that represents all the feedback
     * that is has been sent to the user.
     */
    @GetMapping("/history")
    public ResponseEntity<GameResponseHistoryDTO> getHistory(@RequestParam Integer gameId) {
        return getGameHistoryService.getGameHistory(gameId);
    }
}