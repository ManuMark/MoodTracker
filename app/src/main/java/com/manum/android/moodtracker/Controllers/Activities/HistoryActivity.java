package com.manum.android.moodtracker.Controllers.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.manum.android.moodtracker.Adapters.HistoryAdapter;
import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {

    private LinearLayout mLine;
    private ImageButton mCommentButton;
    private Mood[] savedMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        // List of items
        List<Mood> mSavedMoods = new ArrayList<>();
        mSavedMoods.add(new Mood("Sad", "Le"));
        mSavedMoods.add(new Mood("Happy", ""));
        mSavedMoods.add(new Mood("Normal", "gras"));
        mSavedMoods.add(new Mood("Disappointed", "c'est"));
        mSavedMoods.add(new Mood("Happy", ""));
        mSavedMoods.add(new Mood("Very Happy", "la vie"));
        mSavedMoods.add(new Mood("Normal", ""));

        // Get listView and set Adapter
        ListView mMoodList = findViewById(R.id.list_moods);
        //mMoodList.
        mMoodList.setAdapter(new HistoryAdapter(this, mSavedMoods));


    }
}
