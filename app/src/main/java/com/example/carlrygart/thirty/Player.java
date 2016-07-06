package com.example.carlrygart.thirty;

import java.util.HashMap;
import java.util.Map;

public class Player {

    String name;
    int score;
    Map<Integer, Integer> results;

    public Player(String name) {
        this.name = name;
        score = 0;
        results = new HashMap<>();
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int choice, int score) {
        this.score += score;
        results.put(choice, score);
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Integer> getResults() { return results; }
}
