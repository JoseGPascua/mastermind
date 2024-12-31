package com.example.mastermind.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "score")
    private int score;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<GameResponse> responseHistory = new ArrayList<>();

    // Constructors
    public Game() {

    }

    public Game(String numberCombination, int attempts, String difficulty) {
        this.numberCombination = numberCombination;
        this.attemptsLeft = attempts;
        this.isGameOver = false;
        this.difficulty = difficulty;
        this.score = 1000;
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

    public Game setGameOver(boolean gameOver) {
        isGameOver = gameOver;
        return this;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public Game updateScore(int deduction) {
        this.score -= deduction;
        return this;
    }

    public List<GameResponse> getResponseHistory() {
        return responseHistory;
    }

    public void addToResponseHistory(GameResponse response) {
        response.setGame(this);
        responseHistory.add(response);
    }

    public int useAttempt() {
        this.attemptsLeft--;
        return attemptsLeft;
    }
}
