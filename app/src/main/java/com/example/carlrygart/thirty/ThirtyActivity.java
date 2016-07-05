package com.example.carlrygart.thirty;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirtyActivity extends AppCompatActivity {

    private ThirtyGame game;
    private boolean gameOn;

    private Button selectButton;
    private Button throwButton;
    private int[] white, red, dices;
    private TextView player_str, score_str, nbrOfThrows_str;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty);

        // Spinner stuff
        Resources res = getResources();
        String[] numbered_list = res.getStringArray(R.array.number_list);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList(numbered_list)));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        game = new ThirtyGame();

        white = new int[] {R.drawable.white1, R.drawable.white2, R.drawable.white3, R.drawable.white4, R.drawable.white5, R.drawable.white6};
        red = new int[] {R.drawable.red1, R.drawable.red2, R.drawable.red3, R.drawable.red4, R.drawable.red5, R.drawable.red6};
        dices = new int[] {R.id.dice0, R.id.dice1, R.id.dice2, R.id.dice3, R.id.dice4, R.id.dice5};

        player_str = (TextView) findViewById(R.id.player_text);
        player_str.setText(game.getPlayerName());
        score_str = (TextView) findViewById(R.id.player_score);
        nbrOfThrows_str = (TextView) findViewById(R.id.player_throws);

        selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerValue = spinner.getSelectedItem().toString();
                //Toast.makeText(ThirtyActivity.this, spinnerValue, Toast.LENGTH_SHORT).show();
                if (!game.calculateScore(spinnerValue)) {
                    Toast.makeText(ThirtyActivity.this, R.string.wrong_choice, Toast.LENGTH_SHORT).show();
                    return;
                }
                score_str.setText(Integer.toString(game.getPlayerScore()));
                adapter.remove((String) spinner.getSelectedItem());
                adapter.notifyDataSetChanged();
                loadNewRound();
            }
        });

        throwButton = (Button) findViewById(R.id.throw_button);
        throwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOn = true;
                int nbrOfThrows = game.throwDices();
                updateDices();
                nbrOfThrows_str.setText(Integer.toString(nbrOfThrows));
                if (nbrOfThrows >= 3) throwButton.setEnabled(false);
                selectButton.setEnabled(true);
            }
        });

        for (int i = 0; i < 6; i++) {
            ImageView dieImg = (ImageView) findViewById(dices[i]);
            final int finalI = i;
            dieImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!gameOn) return;
                    boolean statusOfDie = game.getSavedStatusFromDie(finalI);
                    if (statusOfDie) {
                        game.setSavedStatus(finalI, false);
                    } else {
                        game.setSavedStatus(finalI, true);
                    }
                    updateDices();
                    Log.d("pressedDieValue", String.valueOf(game.getValueFromDie(finalI)));
                }
            });
        }
        loadNewRound();
    }

    protected void updateDices() {
        for (int i = 0; i < 6; i++) {
            int valueOfDie = game.getValueFromDie(i);
            boolean statusOfDie = game.getSavedStatusFromDie(i);
            ImageView dice = (ImageView) findViewById(dices[i]);
            //Log.d("valueOfDie", String.valueOf(valueOfDie));
            if (statusOfDie) {
                dice.setImageResource(red[valueOfDie-1]);
            } else {
                dice.setImageResource(white[valueOfDie-1]);
            }
        }
    }

    protected void loadNewRound() {
        game.resetDices();
        gameOn = false;
        updateDices();
        nbrOfThrows_str.setText("0");
        selectButton.setEnabled(false);
        throwButton.setEnabled(true);
    }
}
