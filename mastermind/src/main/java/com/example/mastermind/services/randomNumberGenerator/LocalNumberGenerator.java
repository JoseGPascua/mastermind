package com.example.mastermind.services.randomNumberGenerator;

import org.springframework.stereotype.Service;

@Service
public class LocalNumberGenerator implements NumberGenerator {

    @Override
    public String generateRandomNumber() {
        StringBuilder randomNumber = new StringBuilder();
        String allowedCharacters = "01234567";

        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * allowedCharacters.length());
            randomNumber.append(allowedCharacters.charAt(index));
        }

        return randomNumber.toString();
    }
}
