package com.example.android.splitlist.ui.main;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;

public class GroceryViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;

    public GroceryViewHolder(View v) {
        super(v);

        mItemName = v.findViewById(R.id.name);
    }

    public void setData(String name) {
        mItemName.setText(name);

        Log.d("please set data", name);
    }

}
