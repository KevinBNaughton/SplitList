package com.example.android.splitlist.ui.main.groceryList;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;

public class GroceryViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private TextView mLikes;

    public GroceryViewHolder(View v) {
        super(v);

        mItemName = v.findViewById(R.id.item_name);
        mLikes = v.findViewById(R.id.num_upvotes);
    }

    public void setData(String name) {
        mItemName.setText(name);
        mLikes.setText("1");
    }

}
