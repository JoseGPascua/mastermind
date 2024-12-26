package com.example.mastermind;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "numberCombination")
    private String numberCombination;

    @Column(name = "maxAttempts")
    private int maxAttempts;

    @Column(name = "isGameOver")
    private boolean isGameOver;

    // Constructors
    public Game() {

    }

    public Game(String numberCombination, int maxAttempts) {
        this.numberCombination = numberCombination;
        this.maxAttempts = maxAttempts;
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

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
