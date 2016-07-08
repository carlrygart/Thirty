package com.example.carlrygart.thirty;

import java.util.HashMap;
import java.util.Map;

// Class for identify players for the game logic Thirty.
public class Player {

    String name;
    int score;
    Map<Integer, Integer> results;

    public Player(String name) {
        this.name = name;
        score = 0;
        results = new HashMap<>();
    }

    // Return player's score.
    public int getScore() {
        return score;
    }

    // Add score to the score board.
    public void addToScore(int choice, int score) {
        this.score += score;
        results.put(choice, score);
    }

    // Return player's name.
    public String getName() {
        return name;
    }

    // Return the player's score board.
    public Map<Integer, Integer> getResults() { return results; }
}
