package com.example.android.splitlist.ui.main.favoritesList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private List<Item> mFavoriteList;

    public FavoriteListAdapter(ArrayList<Item> favoriteList) {
        mFavoriteList = favoriteList;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item_row, parent, false);

        FavoriteViewHolder viewHolder = new FavoriteViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

        if (mFavoriteList != null && mFavoriteList.size() > position) {
            Item item = mFavoriteList.get(position);

            holder.setData(item);
            Log.d("Set the data", "yeah");
        }
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }

}
