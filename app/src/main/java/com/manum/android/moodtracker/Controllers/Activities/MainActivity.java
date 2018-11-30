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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manum.android.moodtracker.Adapters.MyAdapter;
import com.manum.android.moodtracker.Controllers.Fragments.MainFragment;
import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;
import com.manum.android.moodtracker.Adapters.VerticalViewPager;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.MutableDateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonClickedListener {

    private Mood[] mMoods;
    private List<Mood> mSavedMoods;
    private int mCurrentPosition;
    private int mDaysSinceLastUse;
    private SharedPreferences mPreferences;
    private VerticalViewPager mPager;
    private Type mListType;
    private String mComment;

    public static final String PREF_KEY_POSITION = "PREF_KEY_POSITION";
    public static final String PREF_KEY_COMMENT = "PREF_KEY_COMMENT";
    public static final String PREF_KEY_DAYS = "PREF_KEY_DAYS";
    public static final String PREF_KEY_LIST = "PREF_KEY_LIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Mood[]
        mMoods = new Mood[5];
        mMoods[0] = new Mood("sad", "faded_red", "", 1);
        mMoods[1] = new Mood("disappointed", "warm_grey",  "", 2);
        mMoods[2] = new Mood("normal", "cornflower_blue_65","", 3);
        mMoods[3] = new Mood("happy", "light_sage","", 4);
        mMoods[4] = new Mood("super_happy","banana_yellow","", 5);

        // Create ListType to store saved moods
        mListType = new TypeToken<ArrayList<Mood>>(){}.getType();

        // Get preferences and stored data
        mPreferences = getPreferences(MODE_PRIVATE);
        mCurrentPosition = mPreferences.getInt(PREF_KEY_POSITION, 3);
        mComment = mPreferences.getString(PREF_KEY_COMMENT, "");

        // If the date has changed, add past mood to the saved moods
        mDaysSinceLastUse = daysCounter();
        if (mDaysSinceLastUse > 0) {
            addMoodToHistoryList();
            mCurrentPosition = 3;
            mComment = "";
        }

        // Configure pager and adapter
        mPager = findViewById(R.id.viewpager);
        this.configureViewPager();
    }

    // Save current mood and comment when activity is onStop()
    @Override
    protected void onStop() {
        super.onStop();

        int position = mPager.getCurrentItem();
        mPreferences.edit().putInt(PREF_KEY_POSITION, position).apply();
        mPreferences.edit().putString(PREF_KEY_COMMENT, mComment).apply();
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

        if (btnTag == 10) {
            // Display dialog popup
            dialogPopup();
        }
        else if (btnTag == 20) {
            // Start history activity
            getSavedMoods();
            String listStr = new Gson().toJson(mSavedMoods, mListType);
            Intent i = new Intent(this, HistoryActivity.class);
            i.putExtra("list", listStr);
            startActivity(i);
        } else {
            // Share mood with somebody
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String shareBody = "Today, I'm in a "+ mMoods[mCurrentPosition].getName() +" mood.";
            i.putExtra(Intent.EXTRA_SUBJECT, "Mood of the Day");
            i.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(i, "Share via"));
        }

    }

    // Create popup to add comment about the mood of the day
    private void dialogPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Commentaire");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(mComment);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                mComment = input.getText().toString();
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

    // If this is the first time app is running, return -1
    // Otherwise return the number of days since the last time app was launched
    private int daysCounter() {

        int prefDaysSinceEpoch = mPreferences.getInt(PREF_KEY_DAYS, 0);

        // Get days between epoch and now
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        int daysSinceEpoch = Days.daysBetween(epoch, now).getDays();

        mPreferences.edit().putInt(PREF_KEY_DAYS, daysSinceEpoch).apply();

        if (prefDaysSinceEpoch == 0) { return -1; }
        else { return (daysSinceEpoch - prefDaysSinceEpoch); }
    }

    // Add last mood to the history list, eventually add the absent moods
    private void addMoodToHistoryList() {

        Mood pastMood = new Mood(mMoods[mCurrentPosition].getName(), mMoods[mCurrentPosition].getColor(), mComment, mMoods[mCurrentPosition].getWidth());

        getSavedMoods();

        mSavedMoods.add(pastMood);
        if (mSavedMoods.size()>7){ mSavedMoods.remove(0); }

        if (mDaysSinceLastUse > 1) {
            for (int i = 0; i < mDaysSinceLastUse-1; i++) {
                mSavedMoods.add(new Mood("Absent", "history_background","Vous n'avez pas ouvert l'application ce jour", 5));
                if (mSavedMoods.size() > 7) { mSavedMoods.remove(0); }
            }
        }
        String listStr = new Gson().toJson(mSavedMoods, mListType);
        mPreferences.edit().putString(PREF_KEY_LIST, listStr).apply();
    }

    // Get list of saved moods, or create a new one if there is none
    private void getSavedMoods() {

        String strList = mPreferences.getString(PREF_KEY_LIST, "");

        if (strList.isEmpty()) {
            mSavedMoods = new ArrayList<>(7);
            for (int i=0; i<7; i++){
                mSavedMoods.add(new Mood("Empty", "history_background", "", 0));
            }
        }
        else { mSavedMoods = new Gson().fromJson(strList, mListType); }
    }

}
