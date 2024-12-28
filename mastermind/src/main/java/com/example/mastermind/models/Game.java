package com.example.mastermind.models;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number_combination")
    private String numberCombination;

    @Column(name = "max_attempts")
    private int maxAttempts;

    @Column(name = "attempts_left")
    private int attemptsLeft;

    @Column(name = "is_game_over")
    private boolean isGameOver;

    // Constructors
    public Game() {

    }

    public Game(String numberCombination, int maxAttempts) {
        this.numberCombination = numberCombination;
        this.maxAttempts = maxAttempts;
        this.attemptsLeft = maxAttempts;
        this.isGameOver = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberCombination() {
        return numberCombination;
    }

    public void setNumberCombination(String numberCombination) {
        this.numberCombination = numberCombination;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
