package com.example.mastermind.models;

import jakarta.persistence.*;
import org.springframework.http.HttpStatus;

@Entity
@Table(name = "game_response")
public class GameResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_input")
    private String userInput;

    @Column(name = "response")
    private String response;

    @Column(name = "attempts_left")
    private int attemptsLeft;

    @Column(name = "http_status")
    private HttpStatus httpStatus;

    @Column(name = "score_deduction")
    private int scoreDeduction;

    @Column(name = "total_score")
    private int totalScore;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserInput() {
        return userInput;
    }

    public GameResponse setUserInput(String userInput) {
        this.userInput = userInput;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public GameResponse setResponse(String response) {
        this.response = response;
        return this;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public GameResponse setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public GameResponse setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public int getScoreDeduction() {
        return scoreDeduction;
    }

    public GameResponse setScoreDeduction(int scoreDeduction) {
        this.scoreDeduction = scoreDeduction;
        return this;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public GameResponse setTotalScore(int totalScore) {
        this.totalScore = totalScore;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
