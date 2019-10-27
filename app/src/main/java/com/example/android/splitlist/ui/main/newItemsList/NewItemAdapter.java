package com.example.android.splitlist.ui.main.newItemsList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewItemAdapter extends RecyclerView.Adapter<NewItemListViewHolder> {

    private List<String> mNewItemList;

    private ItemClickListener mItemClickListener;

    public NewItemAdapter(ArrayList<String> groceryList) {
        mNewItemList = groceryList;
    }

    @Override
    public NewItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_item_row, parent, false);

        NewItemListViewHolder viewHolder = new NewItemListViewHolder(v, mItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewItemListViewHolder holder, int position) {

        if (mNewItemList != null && mNewItemList.size() > position) {
            String grocery = mNewItemList.get(position);

            holder.setData(grocery);
            Log.d("Set the data", "yeah");
        }
    }

    @Override
    public int getItemCount() {
        return mNewItemList.size();
    }

    public void setRowClickListnerCallback(ItemClickListener rowCallback) {
        mItemClickListener = rowCallback;
    }
}
