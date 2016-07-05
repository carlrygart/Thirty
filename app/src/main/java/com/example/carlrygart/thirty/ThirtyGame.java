package com.example.carlrygart.thirty;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by carlrygart on 03/07/16.
 */
public class ThirtyGame {

    private Dice[] dices;
    private int nbrThrows;
    private Player player;

    public ThirtyGame() {
        resetDices();
        player = new Player("Carl");
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

    public void calculateScore(String spinnerValue) {
        LinkedList<Dice> dicesLeft = new LinkedList<Dice>(Arrays.asList(dices));
        int tempScore = 0;
        int chosenValue = 3;
        if (spinnerValue != "Low") chosenValue = Integer.parseInt(spinnerValue);
        //Log.d("value", String.valueOf(value));

        while (!dicesLeft.isEmpty()) {
            Dice dice = dicesLeft.poll();
            if (dice.getValue() == chosenValue) {
                tempScore += chosenValue;
                break;
            }

            for (Dice dice2: dicesLeft) {
                if (dice.getValue() + dice2.getValue() == chosenValue) {
                    tempScore += chosenValue;
                    dicesLeft.remove(dice2);
                    break;
                }
            }
        }
        //Log.d("tempScore", String.valueOf(tempScore));
        player.addToScore(spinnerValue, tempScore);
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
}