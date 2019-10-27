package com.example.android.splitlist.ui.main.groceryList;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;

public class GroceryViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private TextView mLikes;
    private DeleteItemListener mDeleteListener;
    private LikeItemListener mLikeListener;
    private SwipeItemListener mSwipeListener;

    private Item mItem;
    private Context mContext;

    public GroceryViewHolder(Context context, View v, DeleteItemListener deleteListener, LikeItemListener likeListener, SwipeItemListener swipeListener) {
        super(v);

        mContext = context;

        mItemName = v.findViewById(R.id.item_name);
        mLikes = v.findViewById(R.id.num_upvotes);

        mDeleteListener = deleteListener;
        mLikeListener = likeListener;
        mSwipeListener = swipeListener;

        ImageButton delete = v.findViewById(R.id.cancel_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteListener != null) {
                    mDeleteListener.onItemDelete(mItem);
                }
            }
        });

        ImageButton likeButton = v.findViewById(R.id.heart_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLikeListener != null) {
                    mLikeListener.onItemLiked(mItem); /
                }
            }
        });

        v.setOnTouchListener(new OnSwipeTouchListener(mContext) {
//            public void onSwipeTop() {
//                Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
//            }
            public void onSwipeRight() {

                if (mSwipeListener != null) {
                    mSwipeListener.moveToCheckout(mItem);
                }

            }
            public void onSwipeLeft() {
                if (mSwipeListener != null) {
                    mSwipeListener.moveToCheckout(mItem);
                }
            }
//            public void onSwipeBottom() {
//                Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//            }

        });

    }

    public void setData(Item item) {

        mItem = item;

        mItemName.setText(item.getItemName() + ", $" + item.getPrice());
        mLikes.setText(String.valueOf(item.getLikeNumber()));
    }

}
