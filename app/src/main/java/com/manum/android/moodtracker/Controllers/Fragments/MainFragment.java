package com.manum.android.moodtracker.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;

public class MainFragment extends Fragment implements View.OnClickListener{

    // Create key for Bundle
    private static final String MY_MOOD_KEY = "mood";

    private String mString;
    private OnButtonClickedListener mCallback;

    public interface OnButtonClickedListener {
         void onButtonClicked(View v);
    }

    // Method that will create a new instance of MainFragment, and add data to its bundle
    public static MainFragment newInstance(Mood mood) {

        MainFragment frag = new MainFragment();
        Bundle args = new Bundle();
        args.putString(MY_MOOD_KEY, mood.getName());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments() != null ? getArguments().getString(MY_MOOD_KEY) : "Null";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        // Display layout considering MY_MOOD_KEY
        switch (mString) {
            case "Sad":
                v = inflater.inflate(R.layout.fragment_sad, container, false);
                break;
            case "Disappointed":
                v = inflater.inflate(R.layout.fragment_disappointed, container, false);
                break;
            case "Normal":
                v = inflater.inflate(R.layout.fragment_normal, container, false);
                break;
            case "Happy":
                v = inflater.inflate(R.layout.fragment_happy, container, false);
                break;
            case "Very Happy":
                v = inflater.inflate(R.layout.fragment_very_happy, container, false);
                break;
            default:
                return null;
        }
        v.findViewById(R.id.note_add_btn).setOnClickListener(this);
        v.findViewById(R.id.history_btn).setOnClickListener(this);
        v.findViewById(R.id.share_btn).setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    @Override
    public void onClick(View v) {
        mCallback.onButtonClicked(v);
    }

    private void createCallbackToParentActivity() {
        try {
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }
}
