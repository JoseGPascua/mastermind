package com.example.mastermind.services;

import com.example.mastermind.exceptions.GameIdNotProvidedException;
import com.example.mastermind.exceptions.GameNotFoundException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameResponse;
import com.example.mastermind.models.GameResponseDTO;
import com.example.mastermind.models.GameResponseHistoryDTO;
import com.example.mastermind.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for retrieving {@link Game} history
 */
@Service
public class GetGameHistoryService {
    
    private final GameRepository gameRepository;
    private final static Logger logger = LoggerFactory.getLogger(GetGameHistoryService.class);
    
    
    public GetGameHistoryService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Method to get the game response history of a specified game ID
     *
     * @param gameId is the given game id that will be used to find the game in the GameRepository
     *
     * @return A {@link ResponseEntity} containing a {@link GameResponseHistoryDTO} which contains the id of the game
     * and a list of {@link GameResponseDTO}
     */
    public ResponseEntity<GameResponseHistoryDTO> getGameHistory(Integer gameId) {
        logger.info("Retrieving feedback history for the Game ID: {}", gameId);
        if (gameId == null) {
            throw new GameIdNotProvidedException();
        }

        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if (gameOptional.isEmpty()) {
            throw new GameNotFoundException();
        }

        Game game = gameOptional.get();
        return populateGameResponseHistory(game);
    }

    /**
     * Method to populate the {@link GameResponseHistoryDTO} with a list of {@link GameResponseDTO} from a specific game
     * identified by the game ID.
     *
     * @param game is the {@link Game} found in the repository that matches the given game id, it contains a list of
     *             {@link GameResponse} that will be used to populate the GameResponseDTOList with corresponding
     *             GameResponseDTOs
     *
     * @return A {@link ResponseEntity} containing a {@link GameResponseHistoryDTO} which contains the id of the game
     * and a list of {@link GameResponseDTO}
     */
    private ResponseEntity<GameResponseHistoryDTO> populateGameResponseHistory(Game game) {
        GameResponseHistoryDTO gameResponseHistoryDTO = new GameResponseHistoryDTO(game);
        List<GameResponseDTO> gameResponseDTOList = new ArrayList<>();

        for (int i = 0; i < game.getResponseHistory().size(); i++) {
            gameResponseDTOList.add(new GameResponseDTO(game.getResponseHistory().get(i)));
        }

        gameResponseHistoryDTO.setResponseHistory(gameResponseDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(gameResponseHistoryDTO);
    }
}
