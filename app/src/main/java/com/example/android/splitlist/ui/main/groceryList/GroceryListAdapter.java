package com.example.android.splitlist.ui.main.groceryList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;

import java.util.ArrayList;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryViewHolder> {

    private List<String> mGroceryList;

    public GroceryListAdapter(ArrayList<String> groceryList) {
        mGroceryList = groceryList;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        GroceryViewHolder viewHolder = new GroceryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {

        if (mGroceryList != null && mGroceryList.size() > position) {
            String grocery = mGroceryList.get(position);

            holder.setData(grocery);
            Log.d("Set the data", "yeah");
        }
    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }
}
