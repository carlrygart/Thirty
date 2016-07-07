package com.example.carlrygart.thirty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    TextView s3_val, s4_val, s5_val, s6_val, s7_val, s8_val, s9_val, s10_val, s11_val, s12_val, total_val;
    TextView[] vals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        vals = new TextView[] {
            s3_val = (TextView) findViewById(R.id.s3_val),
            s4_val = (TextView) findViewById(R.id.s4_val),
            s5_val = (TextView) findViewById(R.id.s5_val),
            s6_val = (TextView) findViewById(R.id.s6_val),
            s7_val = (TextView) findViewById(R.id.s7_val),
            s8_val = (TextView) findViewById(R.id.s8_val),
            s9_val = (TextView) findViewById(R.id.s9_val),
            s10_val = (TextView) findViewById(R.id.s10_val),
            s11_val = (TextView) findViewById(R.id.s11_val),
            s12_val = (TextView) findViewById(R.id.s12_val),
            total_val = (TextView) findViewById(R.id.total_val)};

        Intent intent = getIntent();
        int totalScore = 0;
        for (int i = 3; i <= 12; i++) {
            String key = String.valueOf(i);
            int value = intent.getIntExtra(key, -1);
            if (value >= 0) {
                vals[i-3].setText(String.valueOf(value));
                totalScore += value;
            } else {
                vals[i-3].setText("");
            }
        }
        total_val.setText(String.valueOf(totalScore));
    }
}
