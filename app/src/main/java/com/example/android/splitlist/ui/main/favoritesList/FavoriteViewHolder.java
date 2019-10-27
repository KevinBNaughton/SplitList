package com.example.android.splitlist.ui.main.favoritesList;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private TextView mItemPrice;

    private Item mItem;

    public FavoriteViewHolder(View v) {
        super(v);

        //TODO: these might need to change for favorite list
        mItemName = v.findViewById(R.id.favorite_item_name);
        mItemPrice = v.findViewById(R.id.favorite_item_price);

    }

    public void setData(Item item) {

        mItem = item;

        //TODO: these might need to change for favorite list
        mItemName.setText(item.getItemName());
        mItemPrice.setText("$" + item.getPrice());
    }

}
