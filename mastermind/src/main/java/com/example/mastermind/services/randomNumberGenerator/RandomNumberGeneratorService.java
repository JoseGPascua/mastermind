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

    public String provideRandomNumber() {
        try {
            logger.info("Providing random number from external API...");
            return apiNumberGenerator.generateRandomNumber();
        } catch (Exception e) {
            logger.warn("{}... generating local random number", e.getMessage());
            return localNumberGenerator.generateRandomNumber();
        }
    }
}
