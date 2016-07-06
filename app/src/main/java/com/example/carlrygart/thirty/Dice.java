package com.example.carlrygart.thirty;

import java.util.Random;

public class Dice {
    private int value;
    private Random randomGenerator;
    private boolean saved;

    public Dice() {
        randomGenerator = new Random();
        saved = false;
        value = 1;
    }

    public int throwDice() {
        if (saved) return value;
        value = 1 + randomGenerator.nextInt(6);
        return value;
    }

    public int getValue() {
        return value;
    }

    public boolean getSavedStatus() {
        return saved;
    }

    public void setSavedStatus(boolean status) {
        saved = status;
    }
}
