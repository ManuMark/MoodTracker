package com.manum.android.moodtracker.Controllers.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;


public class HistoryActivity extends AppCompatActivity {

    private LinearLayout mLine;
    private ImageButton mCommentButton;
    private Mood[] savedMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        savedMoods = new Mood[7];

        savedMoods[0] = new Mood("Sad", "");
        savedMoods[1] = new Mood("Disappointed", "J'apprécie");
        savedMoods[2] = new Mood("Normal", "");
        savedMoods[3] = new Mood("Happy", "les fruits");
        savedMoods[4] = new Mood("Sad", "");
        savedMoods[5] = new Mood("Happy", "au");
        savedMoods[6] = new Mood("Very Happy", "sirop");


        for (int i=0; i<savedMoods.length; i++) {

            defineLine(i);
            Mood mood = savedMoods[i];

            switch (mood.getName()) {
                case "Sad":
                    mLine.setBackgroundColor(getResources().getColor(R.color.faded_red));
                    break;
                case "Disappointed":
                    mLine.setBackgroundColor(getResources().getColor(R.color.warm_grey));
                    break;
                case "Normal":
                    mLine.setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
                    break;
                case "Happy":
                    mLine.setBackgroundColor(getResources().getColor(R.color.light_sage));
                    //mLine.setLayoutParams(new TableLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f));
                    break;
                case "Very Happy":
                    mLine.setBackgroundColor(getResources().getColor(R.color.banana_yellow));
                    break;
                default:
                    break;
            }
            if (mood.getComment().isEmpty()) { mCommentButton.setVisibility(View.INVISIBLE); }
            else {

                final String comment = mood.getComment();
                mCommentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), comment, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    void defineLine(int i) {

        switch (i){
            case 0:
                mLine = findViewById(R.id.line1);
                mCommentButton = findViewById(R.id.btn_1);
                break;
            case 1:
                mLine = findViewById(R.id.line2);
                mCommentButton = findViewById(R.id.btn_2);
                break;
            case 2:
                mLine = findViewById(R.id.line3);
                mCommentButton = findViewById(R.id.btn_3);
                break;
            case 3:
                mLine = findViewById(R.id.line4);
                mCommentButton = findViewById(R.id.btn_4);
                break;
            case 4:
                mLine = findViewById(R.id.line5);
                mCommentButton = findViewById(R.id.btn_5);
                break;
            case 5:
                mLine = findViewById(R.id.line6);
                mCommentButton = findViewById(R.id.btn_6);
                break;
            case 6:
                mLine = findViewById(R.id.line7);
                mCommentButton = findViewById(R.id.btn_7);
                break;
            default:
                break;
        }
    }
}
