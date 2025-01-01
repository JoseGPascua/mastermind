package com.example.mastermind;

import com.example.mastermind.models.GameResponse;
import com.example.mastermind.services.utils.GuessCheckingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuessCheckingServiceTest {

    @InjectMocks
    private GuessCheckingService guessCheckingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void testCompareGuess(String numberCombination,
                                 String userInput,
                                 String expectedResponse,
                                 int expectedScoreDeduction) {
        GameResponse response = guessCheckingService.compareGuess(numberCombination, userInput);

        assertEquals(expectedResponse, response.getResponse());
        assertEquals(expectedScoreDeduction, response.getScoreDeduction());
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("1100", "1100", "Correctly placed numbers: 4 and correct numbers: 4", 0),
                Arguments.of("1100", "1103", "Correctly placed numbers: 3 and correct numbers: 3", 25),
                Arguments.of("1100", "0000", "Correctly placed numbers: 2 and correct numbers: 2", 50),
                Arguments.of("1100", "0001", "Correctly placed numbers: 1 and correct numbers: 3", 55),
                Arguments.of("1100", "0011", "Correctly placed numbers: 0 and correct numbers: 4", 60),
                Arguments.of("1100", "1022", "Correctly placed numbers: 1 and correct numbers: 2", 65),
                Arguments.of("1100", "1222", "Correctly placed numbers: 1 and correct numbers: 1", 75),
                Arguments.of("1100", "3333", "All incorrect!", 100)
        );
    }
}
