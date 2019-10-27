package com.example.android.splitlist.ui.main.groceryList;

import android.content.Context;
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
    private DeleteItemListener mDeleteListener;
    private LikeItemListener mLikeListener;
    private SwipeItemListener mSwipeListener;
    private Context mContext;

    public GroceryListAdapter(Context context, ArrayList<Item> groceryList) {
        mGroceryList = groceryList;
        mContext = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        GroceryViewHolder viewHolder = new GroceryViewHolder(mContext, v, mDeleteListener, mLikeListener, mSwipeListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {

        if (mGroceryList != null && mGroceryList.size() > position) {
            Item item = mGroceryList.get(position);

            ArrayList<String> list = item.getUsers();

            holder.setData(item);
            Log.d("Set the data", "yeah");
        }
    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }

    public void setListenerCallbacks(DeleteItemListener deleteListener, LikeItemListener likeListener, SwipeItemListener swipeListener) {

        mDeleteListener = deleteListener;
        mLikeListener = likeListener;
        mSwipeListener = swipeListener;

    }
}
