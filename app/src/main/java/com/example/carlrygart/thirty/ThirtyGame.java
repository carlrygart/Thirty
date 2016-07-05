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

            // Other solution... TBC
//        LinkedList<Dice> dicesLeft = new LinkedList<>(Arrays.asList(dices));
//        int chosenValue = 3;
//        if (spinnerValue.equals("Low")) chosenValue = Integer.parseInt(spinnerValue);
//        //Log.d("value", String.valueOf(value));
//
//        while (!dicesLeft.isEmpty()) {
//            Dice dice = dicesLeft.poll();
//            if (dice.getValue() == chosenValue) {
//                tempScore += chosenValue;
//                Log.d("First loop", String.valueOf(chosenValue));
//                // ev continue?
//            }
//
//            for (Dice dice2: dicesLeft) {
//                if (dice.getValue() + dice2.getValue() == chosenValue) {
//                    tempScore += chosenValue;
//                    dicesLeft.remove(dice2);
//                    Log.d("Second loop", String.valueOf(chosenValue));
//                    break;
//                }
//            }
//            // ev continue?
//        }
        //Log.d("tempScore", String.valueOf(tempScore));
        player.addToScore(spinnerValue, tempScore);
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
}