package com.example.mastermind.services.randomNumberGenerator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ApiNumberGenerator implements NumberGenerator {

    private final RestTemplate restTemplate;

    public ApiNumberGenerator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String generateRandomNumber() {
        String apiUrl = "https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new";

        try {
            String response = restTemplate.getForObject(apiUrl, String.class);

            if (response == null || response.isEmpty()) {
                throw new RuntimeException("Response is empty");
            }

            String randomNumber = Arrays
                    .stream(response.trim().split("\\s"))
                    .collect(Collectors.joining());

            if (randomNumber.length() != 4) {
                throw new RuntimeException("Invalid number length");
            }
            return randomNumber;
        } catch (Exception e) {
            throw new RuntimeException("API fetch failed");
        }
    }
}
