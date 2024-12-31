package com.example.mastermind.services.randomNumberGenerator;

/**
 * Interface for generating random numbers based on the difficulty level. Implementations of this interface
 * can vary on its usage.
 */
public interface NumberGenerator {

    /**
     * Generates a random number as a String based on the difficulty provided as an argument.
     *
     * @param difficulty is the difficulty level that will determine the allowed characters in the random number
     *                   that will be generated. The difficulty levels allow for three different inputs: 1, 2, or 3.
     *                   If the input is 1, the allowed characters are from the range 0-7.
     *                   If the input is 2, the allowed characters are from the range 0-8.
     *                   If the input is 3, the allowed characters are from the range 0-9.
     * @return a String representing the random number
     */
    String generateRandomNumber(String difficulty);
}
