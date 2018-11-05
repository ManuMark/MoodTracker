package com.manum.android.moodtracker.Controllers.Activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

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
    private String mComment;

    public static final String PREF_KEY_POSITION = "PREF_KEY_POSITION";
    public static final String PREF_KEY_COMMENT = "PREF_KEY_COMMENT";

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

        // Get the preferences
        mCurrentPosition = mPreferences.getInt(PREF_KEY_POSITION, 3);
        mComment = mPreferences.getString(PREF_KEY_COMMENT, "No comment");

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
        // Start history activity
        if (btnTag == 20) {
            Intent i = new Intent(this, HistoryActivity.class);
            startActivity(i);
        // Display dialog popup
        } else {
            dialogPopup();
        }

    }

    // Create popup to add comment about the mood of the day
    private void dialogPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Commentaire");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                mComment = input.getText().toString();
                mPreferences.edit().putString(PREF_KEY_COMMENT, mComment).apply();
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
