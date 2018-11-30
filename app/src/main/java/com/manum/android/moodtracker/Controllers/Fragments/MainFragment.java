package com.manum.android.moodtracker.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;

public class MainFragment extends Fragment implements View.OnClickListener{

    // Create keys for Bundle
    private static final String MOOD_COLOR_KEY = "mood_color";
    public static final String MOOD_IMAGE_KEY = "mood_image";

    private String mStringColor;
    private String mStringImage;
    private OnButtonClickedListener mCallback;

    public interface OnButtonClickedListener {
         void onButtonClicked(View v);
    }

    // Method that will create a new instance of MainFragment, and add data to its bundle
    public static MainFragment newInstance(Mood mood) {

        MainFragment frag = new MainFragment();
        Bundle args = new Bundle();
        args.putString(MOOD_COLOR_KEY, mood.getColor());
        args.putString(MOOD_IMAGE_KEY, mood.getName());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStringColor = getArguments() != null ? getArguments().getString(MOOD_COLOR_KEY) : "Null";
        mStringImage = getArguments() != null ? getArguments().getString(MOOD_IMAGE_KEY) : "Null";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;

        // Display layout considering bundle keys

        v = inflater.inflate(R.layout.fragment_main, container, false);
        RelativeLayout fragmentLayout = v.findViewById(R.id.main_fragment_layout);
        ImageView fragmentImage = v.findViewById(R.id.main_fragment_image);

        int resIdColor = getContext().getResources().getIdentifier(mStringColor, "color", getContext().getPackageName());
        fragmentLayout.setBackgroundColor(getContext().getResources().getColor(resIdColor));

        String resourceName = "smiley_"+mStringImage;
        int resIdImage = getContext().getResources().getIdentifier(resourceName, "drawable", getContext().getPackageName());
        fragmentImage.setImageResource(resIdImage);

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
