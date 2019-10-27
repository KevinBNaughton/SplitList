package com.example.android.splitlist.ui.main.checkoutList;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;

public class CheckoutViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private TextView mItemPrice;

    private Item mItem;

    public CheckoutViewHolder(View v) {
        super(v);

        mItemName = v.findViewById(R.id.checkout_item_name);
        mItemPrice = v.findViewById(R.id.checkout_item_price);

    }

    public void setData(Item item) {

        mItem = item;

        mItemName.setText(item.getItemName());
        mItemPrice.setText("$" + item.getPrice());
    }

}
