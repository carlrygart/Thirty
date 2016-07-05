package com.example.carlrygart.thirty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlrygart on 04/07/16.
 */
public class Player {

    String name;
    int score;
    Map<String, Integer> results;

    public Player(String name) {
        this.name = name;
        score = 0;
        results = new HashMap<String, Integer>();
    }

    public int getScore() {
        return score;
    }

    public void addToScore(String choice, int score) {
        this.score += score;
        results.put(choice, score);
    }

    public String getName() {
        return name;
    }
}
