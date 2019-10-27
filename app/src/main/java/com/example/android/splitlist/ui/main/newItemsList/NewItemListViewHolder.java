package com.example.android.splitlist.ui.main.newItemsList;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.ItemClickListener;
import com.example.android.splitlist.ui.main.data.model.Item;

public class NewItemListViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private Item mItem;
    private ItemClickListener mItemClickListener;

    public NewItemListViewHolder(View v, ItemClickListener clickListener) {
        super(v);

        mItemName = v.findViewById(R.id.new_item_name);
        mItemClickListener = clickListener;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View inputView) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemRowClick(mItem);
                }
            }
        });
    }

    public void setData(Item item) {

        mItem = item;

        mItemName.setText(item.getItemName() + ", $" + item.getPrice());
    }

}
