package com.manum.android.moodtracker.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.manum.android.moodtracker.Controllers.Fragments.MainFragment;
import com.manum.android.moodtracker.Models.Mood;

public class MyAdapter extends FragmentPagerAdapter {

    private Mood[] moods;

    public MyAdapter(FragmentManager mgr, Mood[] moods) {
        super(mgr);
        this.moods = moods;
    }

    @Override
    public int getCount() {
        return moods.length;
    }

    public Fragment getItem(int position) { return (MainFragment.newInstance(this.moods[position])); }

}