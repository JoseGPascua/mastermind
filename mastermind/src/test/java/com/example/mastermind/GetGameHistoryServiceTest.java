package com.example.mastermind;

import com.example.mastermind.exceptions.GameIdNotProvidedException;
import com.example.mastermind.exceptions.GameNotFoundException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameResponse;
import com.example.mastermind.models.GameResponseDTO;
import com.example.mastermind.models.GameResponseHistoryDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.GetGameHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetGameHistoryServiceTest {
    private static final int VALID_GAME_ID = 1;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GetGameHistoryService getGameHistoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenGameIdNotProvidedThrowsException() {
        assertThrows(GameIdNotProvidedException.class, () ->
                getGameHistoryService.getGameHistory(null)
        );
    }

    @Test
    public void givenGameIdNotFoundProvidedThrowsException() {
        when(gameRepository.findById(VALID_GAME_ID)).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () ->
                getGameHistoryService.getGameHistory(VALID_GAME_ID)
        );
        verify(gameRepository).findById(VALID_GAME_ID);
    }

    @Test
    public void givenValidGameIdInputIsSuccessful() {
        GameResponse gameResponse = new GameResponse().setResponse("TEST1");

        Game game = new Game();
        game.setId(VALID_GAME_ID);
        game.addToResponseHistory(gameResponse);

        List<GameResponseDTO> gameResponseDTOList = new ArrayList<>();
        for (int i = 0; i < game.getResponseHistory().size(); i++) {
            gameResponseDTOList.add(new GameResponseDTO(game.getResponseHistory().get(i)));
        }

        GameResponseHistoryDTO expectedGameResponseHistoryDTO = new GameResponseHistoryDTO(game);
        expectedGameResponseHistoryDTO.setResponseHistory(gameResponseDTOList);

        when(gameRepository.findById(VALID_GAME_ID)).thenReturn(Optional.of(game));

        ResponseEntity<GameResponseHistoryDTO> response = getGameHistoryService.getGameHistory(VALID_GAME_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedGameResponseHistoryDTO.getGameId(), response.getBody().getGameId());
        assertEquals(expectedGameResponseHistoryDTO.getResponseHistory().size(), response.getBody().getResponseHistory().size());

        verify(gameRepository).findById(VALID_GAME_ID);
    }


}
