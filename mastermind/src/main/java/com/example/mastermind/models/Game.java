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

    @Column(name = "attempts_left")
    private int attemptsLeft;

    @Column(name = "is_game_over")
    private boolean isGameOver;

    // Constructors
    public Game() {

    }

    public Game(String numberCombination, int attempts) {
        this.numberCombination = numberCombination;
        this.attemptsLeft = attempts;
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

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(int attempts) {
        this.attemptsLeft = attempts;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
