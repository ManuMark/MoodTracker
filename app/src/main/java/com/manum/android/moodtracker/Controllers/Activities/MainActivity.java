package com.manum.android.moodtracker.Controllers.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.manum.android.moodtracker.Adapters.MyAdapter;
import com.manum.android.moodtracker.Controllers.Fragments.MainFragment;
import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;
import com.manum.android.moodtracker.Adapters.VerticalViewPager;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonClickedListener {

    private Mood[] mMoods;
    private int mCurrentPosition;
    private SharedPreferences mPreferences;
    private VerticalViewPager mPager;

    public static final String PREF_KEY_POSITION = "PREF_KEY_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getPreferences(MODE_PRIVATE);

        mPager = findViewById(R.id.viewpager);

        // Create Mood[]
        mMoods = new Mood[5];
        mMoods[0] = new Mood("Sad");
        mMoods[1] = new Mood("Disappointed");
        mMoods[2] = new Mood("Normal");
        mMoods[3] = new Mood("Happy");
        mMoods[4] = new Mood("Very Happy");

        // Get last mood displayed
        mCurrentPosition = mPreferences.getInt(PREF_KEY_POSITION, 3);

        this.configureViewPager();
    }

    // Save current mood when activity is onStop()
    @Override
    protected void onStop() {
        super.onStop();
        int mSavedPosition = mPager.getCurrentItem();
        mPreferences.edit().putInt(PREF_KEY_POSITION, mSavedPosition).apply();
    }

    // Configure VerticalViewPager to display main app screen
    private void configureViewPager(){
        mPager.setAdapter(new MyAdapter(getSupportFragmentManager(), mMoods));
        mPager.setCurrentItem(mCurrentPosition);
    }

    // Set up buttons
    @Override
    public void onButtonClicked(View v) {

        int btnTag = Integer.parseInt(v.getTag().toString());
        // Open history activity
        if (btnTag == 20) {
            Intent i = new Intent(this, HistoryActivity.class);
            startActivity(i);
        // Display Toast
        } else {
            Toast.makeText(this, "Bouton commentary cliqué", Toast.LENGTH_SHORT).show();
        }

    }
}
