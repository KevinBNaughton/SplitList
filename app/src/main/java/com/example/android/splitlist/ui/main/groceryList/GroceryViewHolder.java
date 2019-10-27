package com.example.android.splitlist.ui.main.groceryList;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;

public class GroceryViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private TextView mLikes;
    private CheckoutItemListener mCheckoutListener;
    private LikeItemListener mLikeListener;
    private FavoriteItemListener mFavoriteListener;

    private Item mItem;

    public GroceryViewHolder(View v, CheckoutItemListener deleteListener, LikeItemListener likeListener, FavoriteItemListener favoriteListener) {
        super(v);


        mItemName = v.findViewById(R.id.item_name);
        mLikes = v.findViewById(R.id.num_upvotes);

        mCheckoutListener = deleteListener;
        mLikeListener = likeListener;
        mFavoriteListener = favoriteListener;

        ImageButton toCheckout = v.findViewById(R.id.move_to_checkout_button);

        final ImageButton likeButton = v.findViewById(R.id.heart_button);

        toCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckoutListener != null) {


                    mCheckoutListener.onItemCheckout(mItem);


                    //TODO: moving this to be incorrporated with the like button

//                        mItem.setLikeNumber(mItem.getLikeNumber() - 1);
//
//                        if (mItem.getLikeNumber() > 0) {
//                            likeButton.setImageResource(R.mipmap.heart_empty_icon);
//                            mLikes.setTextColor(Color.BLACK);
//                            mLikes.setText(String.valueOf(mItem.getLikeNumber()));
//                        }
//
//                        mDeleteListener.onItemDelete(mItem);
//                    }
                }
                ;
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLikeListener != null) {

                    //TODO: put in logic to make sure that users are not currently buying it before this logic runs

                        mItem.setLikeNumber(mItem.getLikeNumber() + 1);

                        likeButton.setImageResource(R.mipmap.heart_filled_icon);

                        mLikes.setTextColor(Color.WHITE);
                        mLikes.setText(String.valueOf(mItem.getLikeNumber()));

                        mLikeListener.onItemLiked(mItem);
                    }
                }
        });

        final ImageButton favorite = v.findViewById(R.id.favorite_button);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFavoriteListener != null) {

                    if (mItem.getFavorite()) {
                        favorite.setImageResource(R.mipmap.empty_star);
                        mItem.setFavorite(false);
                    } else {
                        favorite.setImageResource(R.mipmap.filled_star);
                        mItem.setFavorite(true);
                    }

                    mFavoriteListener.onFavorite(mItem);

                }
            }
        });

    }

    public void setData(Item item) {

        mItem = item;

        mItemName.setText(item.getItemName() + ", $" + item.getPrice());
        mLikes.setText(String.valueOf(item.getLikeNumber()));
    }

}

