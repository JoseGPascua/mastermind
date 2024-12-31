package com.example.mastermind.services.randomNumberGenerator;

import com.example.mastermind.exceptions.InvalidDifficultyInputException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ApiNumberGenerator implements NumberGenerator {

    private final RestTemplate restTemplate;
    private static final String EASY = "https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new";
    private static final String MEDIUM = "https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new";
    private static final String HARD = "https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new";

    public ApiNumberGenerator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method generates a random number using the random number api. The api url is determined through a switch
     * statement that depends on the difficulty argument. The chosen api url will then try to get a response from the
     * api url, and if it is successful, it will reformat the response that will be returned as the random number.
     * Otherwise, if the try block fails, it will throw a RuntimeException.
     *
     * @param difficulty is the difficulty level that will determine which api url to use
     *
     * @return a String called randomNumber, which is the result of the response from the api after it is reformatted
     */
    @Override
    public String generateRandomNumber(String difficulty) {
        String apiUrl = switch (difficulty) {
            case "1" -> EASY;
            case "2" -> MEDIUM;
            case "3" -> HARD;
            default -> throw new InvalidDifficultyInputException();
        };

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
