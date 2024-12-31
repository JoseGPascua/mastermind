package com.example.mastermind.models;

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
