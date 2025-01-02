package com.example.mastermind.services.randomNumberGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RandomNumberGeneratorService {

    private final ApiNumberGenerator apiNumberGenerator;
    private final LocalNumberGenerator localNumberGenerator;
    private final Logger logger = LoggerFactory.getLogger(RandomNumberGeneratorService.class);

    public RandomNumberGeneratorService(ApiNumberGenerator apiNumberGenerator,
                                        LocalNumberGenerator localNumberGenerator) {
        this.apiNumberGenerator = apiNumberGenerator;
        this.localNumberGenerator = localNumberGenerator;
    }

    /**
     * Method to provide a random number. This method will first attempt to use teh ApiNumberGenerator class, and if
     * the API is down or somehow manages to return a value the program sees as invalid, the method will use the
     * localNumberGenerator as a fallback to generate the random number.
     *
     * @param difficulty is the difficulty level used to determine what allowed characters will be used for the random
     *                   number
     * @return a String representing the random number that the user must guess during the game
     */
    public String provideRandomNumber(String difficulty) {
        try {
            logger.info("Providing random number from external API...");
            return apiNumberGenerator.generateRandomNumber(difficulty);
        } catch (Exception e) {
            logger.info("Using local number generator: {}", e.getMessage());
            return localNumberGenerator.generateRandomNumber(difficulty);
        }
    }
}
