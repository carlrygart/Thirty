package com.example.carlrygart.thirty;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ThirtyActivity extends AppCompatActivity {

    private ThirtyGame game;
    private Button selectButton, throwButton, resultsButton, newGameButton;
    private int[] white, red, dices;
    private TextView playerStr, scoreStr, nbrOfThrowsString, statusText;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty);

        // Spinner stuff. Fetching the resources and find the array with choices. Creates the
        // spinner object and the adapter. Finally putting the adapter in the spinner object.
        Resources res = getResources();
        String[] numberedList = res.getStringArray(R.array.number_list);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList(numberedList)));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Initiates the game model.
        game = new ThirtyGame();

        // Fetching the needed the dice images and getting IDs for the ImageViews.
        white = new int[] {R.drawable.white1, R.drawable.white2, R.drawable.white3, R.drawable.white4, R.drawable.white5, R.drawable.white6};
        red = new int[] {R.drawable.red1, R.drawable.red2, R.drawable.red3, R.drawable.red4, R.drawable.red5, R.drawable.red6};
        dices = new int[] {R.id.dice0, R.id.dice1, R.id.dice2, R.id.dice3, R.id.dice4, R.id.dice5};

        // Fetching all the Textviews.
        playerStr = (TextView) findViewById(R.id.player_text);
        playerStr.setText(game.getPlayerName());
        scoreStr = (TextView) findViewById(R.id.player_score);
        nbrOfThrowsString = (TextView) findViewById(R.id.player_throws);
        statusText = (TextView) findViewById(R.id.status_text);

        // Finds all the dices and creates the click listeners for them.
        for (int i = 0; i < 6; i++) {
            ImageView dieImg = (ImageView) findViewById(dices[i]);
            final int finalI = i;
            dieImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // A dice should not be clickable if game is not on.
                    if (!game.isGameOn()) return;

                    // Change status of dice in the game object.
                    boolean statusOfDie = game.getSavedStatusFromDie(finalI);
                    if (statusOfDie) {
                        game.setSavedStatus(finalI, false);
                    } else {
                        game.setSavedStatus(finalI, true);
                    }

                    updateDices();
                    Log.d("PressedDieValue", String.valueOf(game.getValueFromDie(finalI)));
                }
            });
        }

        // Finds the select button and creates the click listener.
        selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerValue = spinner.getSelectedItem().toString();
                // If the player have chosen dices that are invalid for the chosen value
                // it will show the message.
                if (!game.calculateScore(spinnerValue)) {
                    Toast.makeText(ThirtyActivity.this, R.string.wrong_choice, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Print out score and remove the chosen value from the adapter (and therefore
                // also the spinner).
                scoreStr.setText(Integer.toString(game.getPlayerScore()));
                adapter.remove((String) spinner.getSelectedItem());
                adapter.notifyDataSetChanged();
                loadNewRound();
            }
        });

        // Finds the results button and creates the click listener.
        resultsButton = (Button) findViewById(R.id.results_button);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResults();
            }
        });

        // Finds the new game button and creates the click listener. For simplicity the activity is
        // just recreated.
        newGameButton = (Button) findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThirtyActivity.this.recreate();
            }
        });

        // Finds the new throw button and creates the click listener.
        throwButton = (Button) findViewById(R.id.throw_button);
        throwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change the status for the game.
                game.setGameState(true);
                statusText.setText("");
                // Tell the game to throw the dices. and then update the images.
                int nbrOfThrows = game.throwDices();
                updateDices();
                // Print the number of used throws for this round and disable the throw button
                // if the player already has used his throws.
                nbrOfThrowsString.setText(Integer.toString(nbrOfThrows));
                if (nbrOfThrows >= 3) throwButton.setEnabled(false);
                selectButton.setEnabled(true);
            }
        });

        loadNewRound();
    }

    // Updates all the dice images according to their status.
    protected void updateDices() {
        for (int i = 0; i < 6; i++) {
            int valueOfDie = game.getValueFromDie(i);
            boolean statusOfDie = game.getSavedStatusFromDie(i);
            ImageView dice = (ImageView) findViewById(dices[i]);
            if (statusOfDie) {
                dice.setImageResource(red[valueOfDie-1]);
            } else {
                dice.setImageResource(white[valueOfDie-1]);
            }
        }
    }

    // Resetting all the dices values to one and updates the images, buttons and textviews.
    protected void loadNewRound() {
        game.resetDices();
        game.setGameState(false);
        updateDices();
        nbrOfThrowsString.setText("0");
        selectButton.setEnabled(false);
        throwButton.setEnabled(true);
        if (adapter.isEmpty()) {
            throwButton.setEnabled(false);
            statusText.setText(R.string.game_over);
            openResults();

        }
    }

    // Fetching all the score results from the game object, putting the information in the intent
    // for the activity and finally starting the activity.
    protected void openResults() {
        Map<Integer, Integer> playerScores = game.getPlayerResult();
        Intent intent = new Intent(ThirtyActivity.this, Results.class);
        for (int key_int = 3; key_int <= 12; key_int++) {
            String key_string = String.valueOf(key_int);
            intent.putExtra(key_string, playerScores.get(key_int));
            //Log.d("Int", key_string + " and Val: " + playerScores.get(key_int));
        }
        startActivity(intent);
    }
}
