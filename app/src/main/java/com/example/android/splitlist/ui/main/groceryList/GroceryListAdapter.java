package com.example.android.splitlist.ui.main.groceryList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;

import java.util.ArrayList;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryViewHolder> {

    private List<Item> mGroceryList;
    private CheckoutItemListener mDeleteListener;
    private LikeItemListener mLikeListener;
    private FavoriteItemListener mFavoriteListener;

    public GroceryListAdapter(ArrayList<Item> groceryList) {
        mGroceryList = groceryList;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        GroceryViewHolder viewHolder = new GroceryViewHolder(v, mDeleteListener, mLikeListener, mFavoriteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {

        if (mGroceryList != null && mGroceryList.size() > position) {
            Item item = mGroceryList.get(position);

            holder.setData(item);
            Log.d("Set the data", "yeah");
        }
    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }

    public void setListenerCallbacks(CheckoutItemListener deleteListener, LikeItemListener likeListener, FavoriteItemListener favoriteListener) {

        mDeleteListener = deleteListener;
        mLikeListener = likeListener;
        mFavoriteListener = favoriteListener;

    }
}
