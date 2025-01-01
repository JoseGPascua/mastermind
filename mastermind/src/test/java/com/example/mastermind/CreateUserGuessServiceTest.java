package com.example.mastermind;

import com.example.mastermind.exceptions.GameIdNotProvidedException;
import com.example.mastermind.exceptions.GameNotFoundException;
import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameResponse;
import com.example.mastermind.models.GameResponseDTO;
import com.example.mastermind.repository.GameRepository;
import com.example.mastermind.services.CreateUserGuessService;
import com.example.mastermind.services.utils.GameValidationService;
import com.example.mastermind.services.utils.GuessCheckingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CreateUserGuessServiceTest {

    private static final int VALID_GAME_ID = 1;
    private static final String VALID_USER_INPUT = "1234";
    private static final String INVALID_USER_INPUT = "abcd";
    private static final String NUMBER_COMBINATION = "4321";


    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameValidationService gameValidationService;

    @Mock
    private GuessCheckingService guessCheckingService;

    @InjectMocks
    private CreateUserGuessService createUserGuessService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenGameIdNotProvidedThrowsException() {
        assertThrows(GameIdNotProvidedException.class, () ->
                createUserGuessService.handleUserInput(null, VALID_USER_INPUT)
        );
    }

    @Test
    public void givenGameIdNotFoundProvidedThrowsException() {
        when(gameRepository.findById(VALID_GAME_ID)).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () ->
                createUserGuessService.handleUserInput(VALID_GAME_ID, INVALID_USER_INPUT)
        );
        verify(gameRepository).findById(VALID_GAME_ID);
    }

    @Test
    public void givenValidGameAndValidUserInputIsSuccessful() {
        Game game = new Game();
        game.setNumberCombination(NUMBER_COMBINATION);

        GameResponse gameResponse = new GameResponse();
        gameResponse.setHttpStatus(HttpStatus.CREATED);

        GameResponse validGameResponse = new GameResponse();
        validGameResponse.setHttpStatus(HttpStatus.OK);

        GameResponse validUserInputResponse = new GameResponse();
        validUserInputResponse.setHttpStatus(HttpStatus.OK);

        when(gameRepository.findById(VALID_GAME_ID)).thenReturn(Optional.of(game));
        when(gameValidationService.validateGameStatus(eq(game), any(GameResponse.class))).thenReturn(validGameResponse);
        when(gameValidationService.validateUserInput(eq(game), eq(VALID_USER_INPUT), any(GameResponse.class)))
                .thenReturn(validUserInputResponse);
        when(guessCheckingService.compareGuess(NUMBER_COMBINATION, VALID_USER_INPUT)).thenReturn(gameResponse);

        ResponseEntity<GameResponseDTO> response =
                createUserGuessService.handleUserInput(VALID_GAME_ID, VALID_USER_INPUT);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(gameRepository).findById(VALID_GAME_ID);
        verify(gameValidationService).validateGameStatus(eq(game), any(GameResponse.class));
        verify(gameValidationService).validateUserInput(eq(game), eq(VALID_USER_INPUT), any(GameResponse.class));
        verify(guessCheckingService).compareGuess(NUMBER_COMBINATION, VALID_USER_INPUT);
    }

    @Test
    public void givenValidGameAndInvalidUserInputFails() {
        Game game = new Game();
        game.setNumberCombination(NUMBER_COMBINATION);

        GameResponse validGameResponse = new GameResponse();
        validGameResponse.setHttpStatus(HttpStatus.OK);

        GameResponse invalidUserInputResponse = new GameResponse();
        invalidUserInputResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        when(gameRepository.findById(VALID_GAME_ID)).thenReturn(Optional.of(game));
        when(gameValidationService.validateGameStatus(eq(game), any(GameResponse.class))).thenReturn(validGameResponse);
        when(gameValidationService.validateUserInput(eq(game), eq(INVALID_USER_INPUT), any(GameResponse.class)))
                .thenReturn(invalidUserInputResponse);

        ResponseEntity<GameResponseDTO> response =
                createUserGuessService.handleUserInput(VALID_GAME_ID, INVALID_USER_INPUT);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(gameRepository).findById(VALID_GAME_ID);
        verify(gameValidationService).validateGameStatus(eq(game), any(GameResponse.class));
        verify(gameValidationService).validateUserInput(eq(game), eq(INVALID_USER_INPUT), any(GameResponse.class));
    }
}
