package com.manum.android.moodtracker.Controllers.Activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manum.android.moodtracker.Adapters.HistoryAdapter;
import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Type listType = new TypeToken<ArrayList<Mood>>(){}.getType();
        List<Mood> mSavedMoods;

        Intent i = getIntent();
        String listStr = i.getStringExtra("list");

        if (listStr.isEmpty()) {mSavedMoods = new ArrayList<>(7);}
        else { mSavedMoods = new Gson().fromJson(listStr, listType); }

        // Get listView and set Adapter
        ListView mMoodList = findViewById(R.id.list_moods);
        mMoodList.setAdapter(new HistoryAdapter(this, mSavedMoods));
    }
}
