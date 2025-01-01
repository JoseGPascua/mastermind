package com.example.mastermind;

import com.example.mastermind.exceptions.InvalidDifficultyInputException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.CreateGameService;
import com.example.mastermind.services.randomNumberGenerator.RandomNumberGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateGameServiceTest {

    private static final int MAX_ATTEMPTS = 10;
    private static final String NUMBER_COMBINATION = "4321";
    private static final String INVALID_DIFFICULTY = "9";
    private static final String VALID_DIFFICULTY = "1";

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RandomNumberGeneratorService randomNumberGeneratorService;

    @InjectMocks
    private CreateGameService createGameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidDifficultyIsInputForCreatingGameReturnsGameDTOWith201StatusCode() {
        GameDTO expectedGameDTO = new GameDTO(new Game(NUMBER_COMBINATION, MAX_ATTEMPTS, VALID_DIFFICULTY));

        when(randomNumberGeneratorService.provideRandomNumber(VALID_DIFFICULTY)).thenReturn(NUMBER_COMBINATION);
        when(gameRepository.save(any(Game.class)))
                .thenReturn(new Game(NUMBER_COMBINATION, MAX_ATTEMPTS, VALID_DIFFICULTY));

        ResponseEntity<GameDTO> response = createGameService.createGame(VALID_DIFFICULTY);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedGameDTO.getNumberCombination(), response.getBody().getNumberCombination());
        assertEquals(expectedGameDTO.getAttemptsLeft(), response.getBody().getAttemptsLeft());

        verify(randomNumberGeneratorService).provideRandomNumber(VALID_DIFFICULTY);
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    public void givenInvalidDifficultyIsInputForCreatingGameReturnsInvalidDifficultyInputException() {
        when(randomNumberGeneratorService.provideRandomNumber(INVALID_DIFFICULTY))
                .thenThrow(new InvalidDifficultyInputException());

        assertThrows(InvalidDifficultyInputException.class, () ->
                createGameService.createGame(INVALID_DIFFICULTY)
        );

        verify(randomNumberGeneratorService).provideRandomNumber(INVALID_DIFFICULTY);
    }
}
