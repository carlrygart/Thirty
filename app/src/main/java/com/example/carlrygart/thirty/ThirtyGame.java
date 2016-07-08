package com.example.carlrygart.thirty;

import android.util.Log;
import java.util.Map;

// Game model and logic for the game Thirty. The class is half prepared to be used for multiple
// players, and can be done with some minor changes (i.e. change the player to a list of players
// and change the methods getPlayerScore() etc. to getPlayerScore(int id)).
public class ThirtyGame {

    private Dice[] dices;
    private int nbrThrows;
    private Player player;
    private boolean gameOn;

    public ThirtyGame() {
        resetDices();
        player = new Player("DicePlayer1");
    }

    // Iterates through all six dices and tell it to get a new value. Returns the number of throws
    // the current user has been using.
    public int throwDices() {
        for (Dice dice : dices) {
            dice.throwDice();
        }
        nbrThrows++;
        return nbrThrows;
    }

    // Changes the state of a specific die.
    public void setSavedStatus(int dieNbr, boolean status) {
        dices[dieNbr].setSavedStatus(status);
    }

    // Returns a specific value of a die.
    public int getValueFromDie(int dieNbr) {
        return dices[dieNbr].getValue();
    }

    // Returns the status of a chosen die.
    public boolean getSavedStatusFromDie(int dieNbr) {
        return dices[dieNbr].getSavedStatus();
    }

    // Method for calculating the score. The score is only calculated from the marked/saved dices.
    // For simplicity the value "Low", is mapped as three.
    public boolean calculateScore(String spinnerValue) {
        // Initial value for the spinner is Low (three), and if it's not change it to its right value.
        int chosenValue = 3;
        if (!spinnerValue.equals("Low")) chosenValue = Integer.parseInt(spinnerValue);
        // Variable for the return statement.
        int tempScore = 0;
        // Iterate through all saved/marked dices.
        for (Dice d: dices) {
            if (!d.getSavedStatus()) continue;
            int dVal = d.getValue();
            // Check if the spinner value is Low, then add all the marked values that are 1/2/3.
            if (chosenValue == 3) {
                if (dVal == 1 || dVal == 2 || dVal == 3) {
                    tempScore += dVal;
                } else {
                    return false;
                }
            } else {
                tempScore += dVal;
            }
        }
        // Finally, if the spinner value is not Low, check if the users choice is legit by
        // checking the remainder. Unfortunately the user can cheat by choosing for example dices
        // 2x6=12 and choose the spinner value 4. Hopefully the player is honest.
        if (chosenValue != 3 && tempScore%chosenValue != 0) return false;

        // Put the score in the players score board and return that the score counting was
        // successful.
        player.addToScore(chosenValue, tempScore);
        Log.d("ScoreAssigned:", String.valueOf(tempScore));
        return true;
    }

    // Recreates the dice objects.
    public void resetDices() {
        dices = new Dice[] {new Dice(), new Dice(), new Dice(), new Dice(), new Dice(), new Dice()};
        nbrThrows = 0;
    }

    // Returns player's score.
    public int getPlayerScore() {
        return player.getScore();
    }

    // Returns player's name.
    public String getPlayerName() {
        return player.getName();
    }

    // Returns players' score board.
    public Map<Integer, Integer> getPlayerResult() {
        return player.getResults();
    }

    // Check if game is on.
    public boolean isGameOn() {
        return gameOn;
    }

    // Sets game status.
    public void setGameState(boolean state) {
        this.gameOn = state;
    }
}