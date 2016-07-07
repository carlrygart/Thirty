package com.example.carlrygart.thirty;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ThirtyGame {

    private Dice[] dices;
    private int nbrThrows;
    private Player player;

    public ThirtyGame() {
        resetDices();
        player = new Player("DicePlayer1");
    }

    public int throwDices() {
        for (Dice dice : dices) {
            dice.throwDice();
        }
        nbrThrows++;
        return nbrThrows;
    }

    public void setSavedStatus(int dieNbr, boolean status) {
        dices[dieNbr].setSavedStatus(status);
    }

    public int getValueFromDie(int dieNbr) {
        return dices[dieNbr].getValue();
    }

    public boolean getSavedStatusFromDie(int dieNbr) {
        return dices[dieNbr].getSavedStatus();
    }

    public boolean calculateScore(String spinnerValue) {
        int chosenValue = 3;
        if (!spinnerValue.equals("Low")) chosenValue = Integer.parseInt(spinnerValue);
        int tempScore = 0;
        for (Dice d: dices) {
            if (!d.getSavedStatus()) continue;
            int dval = d.getValue();
            if (chosenValue == 3) {
                if (dval == 1 || dval == 2 || dval == 3) {
                    tempScore += dval;
                } else {
                    return false;
                }
            } else {
                tempScore += dval;
            }
        }
        if (chosenValue != 3 && tempScore%chosenValue != 0) return false;

        player.addToScore(chosenValue, tempScore);
        Log.d("ScoreAssigned:", String.valueOf(tempScore));
        return true;
    }

    public void resetDices() {
        dices = new Dice[] {new Dice(), new Dice(), new Dice(), new Dice(), new Dice(), new Dice()};
        nbrThrows = 0;
    }

    public int getPlayerScore() {
        return player.getScore();
    }

    public String getPlayerName() {
        return player.getName();
    }

    public Map<Integer, Integer> getPlayerResult() {
        return player.getResults();
    }
}