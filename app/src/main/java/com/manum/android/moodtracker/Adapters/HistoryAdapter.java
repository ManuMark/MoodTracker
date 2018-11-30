package com.manum.android.moodtracker.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

        // Get screen and bar status size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int statusBarHeight = getStatusBarHeight();
        int width = size.x;
        int height = size.y - statusBarHeight;

        // Get information about item
        Mood currentItem = getItem(i);
        String itemColor = currentItem.getColor();
        int itemWidth = currentItem.getWidth();
        final String itemComment = currentItem.getComment();

        // Set color and size of item
        LinearLayout itemLine = view.findViewById(R.id.item_line);
        int resId = context.getResources().getIdentifier(itemColor, "color", context.getPackageName());
        itemLine.setBackgroundColor(context.getResources().getColor(resId));
        itemLine.setLayoutParams(new LinearLayout.LayoutParams(((width/5)*itemWidth), height / moodList.size()));

        // Set date and comment of item
        setItemDate(i, view);
        setItemComment(itemComment, view);

        return view;
    }

    // Date of item
    private void setItemDate(int i, View view){
        TextView itemDate = view.findViewById(R.id.item_date);
        String resourceName = "str_date_"+i;
        int resId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());
        itemDate.setText(resId);
    }

    // Comment of item
    private void setItemComment(final String itemComment, View view){
        ImageButton showCommentBtn = view.findViewById(R.id.item_icon);
        if (itemComment.isEmpty()) {
            showCommentBtn.setEnabled(false);
            showCommentBtn.setImageAlpha(0);
        }
        else {
            showCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, itemComment, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Get height of status bar
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
