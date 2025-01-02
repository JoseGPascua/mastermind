package com.example.mastermind.models;

/**
 * Represents a pattern for the range that a given difficulty is allowed to use for the random number combination
 */
public class DifficultyPattern {
    private String difficultyRange;
    private String difficultyRegex;

    public DifficultyPattern(String difficultyRange, String difficultyRegex) {
        this.difficultyRange = difficultyRange;
        this.difficultyRegex = difficultyRegex;
    }

    public String getDifficultyRange() {
        return difficultyRange;
    }

    public String getDifficultyRegex() {
        return difficultyRegex;
    }

}
