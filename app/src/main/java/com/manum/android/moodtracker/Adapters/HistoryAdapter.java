package com.manum.android.moodtracker.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manum.android.moodtracker.Models.Mood;
import com.manum.android.moodtracker.R;

import java.util.List;

/**
 * Created by Emmanuel G. on 21/11/2018.
 */
public class HistoryAdapter extends BaseAdapter {

    // Fields
    private Context context;
    private List<Mood> moodList;
    private LayoutInflater inflater;

    private String itemName;
    private LinearLayout itemLine;
    private TextView itemDate;

    private int width;
    private int height;

     // Constructor
    public HistoryAdapter(Context context, List<Mood> moodList) {
        this.context = context;
        this.moodList = moodList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return moodList.size();
    }

    @Override
    public Mood getItem(int position) {
        return moodList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.history_adapter_item, null);

        // Get screen size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


        // get informations about item
        Mood currentItem = getItem(i);
        itemName = currentItem.getName();
        final String itemComment = currentItem.getComment();

        itemLine = view.findViewById(R.id.item_line);
        ImageButton showCommentbtn = view.findViewById(R.id.item_icon);

        if (itemComment.isEmpty()) { showCommentbtn.setVisibility(View.INVISIBLE); }
        else {
            showCommentbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, itemComment, Toast.LENGTH_SHORT).show();
                }
            });
        }

        displayWidthAndColor();

        itemDate = view.findViewById(R.id.item_date);
        switch (i) {
            case 0:
                itemDate.setText("Il y a une semaine");
                break;
            case 1:
                itemDate.setText("Il y a six jours");
                break;
            case 2:
                itemDate.setText("Il y a cinq jours");
                break;
            case 3:
                itemDate.setText("Il y a quatre jours");
                break;
            case 4:
                itemDate.setText("Il y a trois jours");
                break;
            case 5:
                itemDate.setText("Avant-hier");
                break;
            case 6:
                itemDate.setText("Hier");
                break;
            default:
                break;
        }

        return view;
    }

    private void displayWidthAndColor() {
        switch (itemName) {
            case "Sad":
                itemLine.setBackgroundColor(context.getResources().getColor(R.color.faded_red));
                itemLine.setLayoutParams(new LinearLayout.LayoutParams((width/5), height / moodList.size()));
                break;
            case "Disappointed":
                itemLine.setBackgroundColor(context.getResources().getColor(R.color.warm_grey));
                itemLine.setLayoutParams(new LinearLayout.LayoutParams((width/5)*2, height / moodList.size()));
                break;
            case "Normal":
                itemLine.setBackgroundColor(context.getResources().getColor(R.color.cornflower_blue_65));
                itemLine.setLayoutParams(new LinearLayout.LayoutParams((width/5)*3, height / moodList.size()));
                break;
            case "Happy":
                itemLine.setBackgroundColor(context.getResources().getColor(R.color.light_sage));
                itemLine.setLayoutParams(new LinearLayout.LayoutParams((width/5)*4, height / moodList.size()));
                break;
            case "Very Happy":
                itemLine.setBackgroundColor(context.getResources().getColor(R.color.banana_yellow));
                itemLine.setLayoutParams(new LinearLayout.LayoutParams(width, height / moodList.size()));
                break;
            default:
                break;
        }
    }
}
