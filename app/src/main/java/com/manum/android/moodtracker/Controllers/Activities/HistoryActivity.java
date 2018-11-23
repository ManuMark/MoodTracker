package com.manum.android.moodtracker.Controllers.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manum.android.moodtracker.Adapters.HistoryAdapter;
import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {

    private List<Mood> mSavedMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Type listType = new TypeToken<ArrayList<Mood>>(){}.getType();

        Intent i = getIntent();
        String listStr = i.getStringExtra("list");

        if (listStr.isEmpty()) {mSavedMoods = new ArrayList<>(7);}
        else { mSavedMoods = new Gson().fromJson(listStr, listType); }


        /*// List of items
        mSavedMoods = new ArrayList<>();
        mSavedMoods.add(new Mood("Sad", "Le"));
        mSavedMoods.add(new Mood("Happy", ""));
        mSavedMoods.add(new Mood("Normal", "gras"));
        mSavedMoods.add(new Mood("Disappointed", "c'est"));
        mSavedMoods.add(new Mood("Happy", ""));
        mSavedMoods.add(new Mood("Very Happy", "la vie"));
        mSavedMoods.add(new Mood("Normal", ""));*/

        // Get listView and set Adapter
        ListView mMoodList = findViewById(R.id.list_moods);
        //mMoodList.
        mMoodList.setAdapter(new HistoryAdapter(this, mSavedMoods));


    }
}
