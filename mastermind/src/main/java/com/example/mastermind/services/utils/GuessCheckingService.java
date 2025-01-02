package com.example.mastermind.services.utils;

import com.example.mastermind.models.Game;
import com.example.mastermind.models.GameResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Service for comparing the {@link Game} numberCombination field to the user's guess. This service generates
 * the feedback for hints.
 */
@Service
public class GuessCheckingService {

    /**
     * Logic for comparing the game's random number combination to the user's input. This method counts the number
     * of correctly placed numbers and the amount of correct numbers. Using the two counts, they are used to create
     * a GameResponse that will provide the user with feedback.
     *
     * The logic for this method first creates the necessary variables then proceeds to the first for loop. The first
     * for loop checks the amount of correctlyPlaceNumbers, and if there are none it adds the current iteration's
     * character to a HashMap (for the numberCombination) and an ArrayList (for the userInput). The second for loop
     * checks if the HashMap has the current iteration's character, and if that character has a value greater than 0,
     * and if that is the case the correctNumbers value is incremented and the value of the current key is decremented.
     * At the end of the second for loop, the value of correctlyPlacedNumbers is added to correctNumbers to account
     * for the characters that weren't iterated over in the second for loop.
     *
     * @param numberCombination is the random number that is provided when a Game object is instantiated
     * @param userInput is the user's guess that will be compared to the numberCombination. This method is only
     *                  accessed when the numberCombination does not equal the userInput
     *
     * @return A {@link GameResponse} that contains feedback for the user
     */
    public GameResponse compareGuess(String numberCombination, String userInput) {
        GameResponse response = new GameResponse();
        int correctlyPlacedNumbers = 0;
        int correctNumbers = 0;

        HashMap<Character, Integer> numberCombinationsMap = new HashMap<>();
        ArrayList<Character> userInputsList = new ArrayList<>();

        for (int i = 0; i < numberCombination.length(); i++) {
            if (numberCombination.charAt(i) == userInput.charAt(i)) {
                correctlyPlacedNumbers++;
            } else {
                if (numberCombinationsMap.containsKey(numberCombination.charAt(i))) {
                    numberCombinationsMap.put(numberCombination.charAt(i), numberCombinationsMap.get(numberCombination.charAt(i)) + 1);
                } else {
                    numberCombinationsMap.put(numberCombination.charAt(i), 1);
                }
                userInputsList.add(userInput.charAt(i));
            }
        }

        for (int i = 0; i < userInputsList.size(); i++) {
            char guessChar = userInputsList.get(i);
            if (numberCombinationsMap.containsKey(guessChar) && numberCombinationsMap.get(guessChar) > 0) {
                correctNumbers++;
                numberCombinationsMap.put(guessChar, numberCombinationsMap.get(guessChar) - 1);
            }
        }

        correctNumbers += correctlyPlacedNumbers;

        int scoreDeduction = calculateScoreDeduction(correctlyPlacedNumbers, correctNumbers);
        response.setScoreDeduction(scoreDeduction);

        boolean allIncorrect = correctlyPlacedNumbers == 0 && correctNumbers == 0;
        return allIncorrect
                ? response.setResponse("All incorrect!")
                : response.setResponse("Correctly placed numbers: " + correctlyPlacedNumbers + " and correct numbers: " + correctNumbers);
    }

    /**
     * Method to add a score deduction value to the GameResponse produced after comparing the user's guess to the
     * random number combination. This method takes the values of correctlyPlacedNumbers and correctNumbers from
     * compareGuess method to calculate a deduction. The amount of correctlyPlacedNumbers will be used to select an
     * index from an array to reduce the score deduction by. The amount of correctNumbers will reduce the scoreDeduction
     * if there are more correctNumbers than there are correctlyPlacedNumbers.
     *
     * @param correctlyPlaceNumbers is the number of correctly guessed numbers in ths correct place that matches the
     *                              random number combination. This value also represents a correctNumber, for example
     *                              if the value of correctlyPlacedNumbers is 1, then correctNumber cannot be a value of
     *                              0.
     * @param correctNumbers is the number of correct numbers in the user's guess but is not in the correct placement
     *                       as the random number combination.
     * @return An Integer representing how much to reduce the total score by
     */
    private Integer calculateScoreDeduction(int correctlyPlaceNumbers, int correctNumbers) {
        int[] scoreDeductionArray = {0, 25, 50, 75, 100};
        int scoreDeduction = 100 - scoreDeductionArray[correctlyPlaceNumbers];

        if (correctNumbers > correctlyPlaceNumbers) {
            int difference = correctNumbers - correctlyPlaceNumbers;
            scoreDeduction -= difference * 10;
        }

        return scoreDeduction;
    }
}
