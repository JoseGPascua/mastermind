package com.example.mastermind.services.randomNumberGenerator;

import com.example.mastermind.exceptions.InvalidDifficultyInputException;
import org.springframework.stereotype.Service;

/**
 * Service for generating a random number locally.
 */
@Service
public class LocalNumberGenerator implements NumberGenerator {

    private static final String EASY = "01234567";
    private static final String MEDIUM = "012345678";
    private static final String HARD = "0123456789";


    /**
     * This method generates a random number in the form of a String. The allowedCharacters variable changes
     * the value of the allowed characters that will be used to create the randomNumber. The randomNumber is built
     * by appending values from a for loop that multiplies a Math.random() value to the length of the allowedCharacters
     * variable.
     *
     * @param difficulty is the difficulty level that will determine which allowed characters will be used
     *
     * @return a String called randomNumber, which is the result of the built String
     */
    @Override
    public String generateRandomNumber(String difficulty) {

        String allowedCharacters = switch (difficulty) {
            case "1" -> EASY;
            case "2" -> MEDIUM;
            case "3" -> HARD;
            default -> throw new InvalidDifficultyInputException();
        };

        StringBuilder randomNumber = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * allowedCharacters.length());
            randomNumber.append(allowedCharacters.charAt(index));
        }

        return randomNumber.toString();
    }
}
