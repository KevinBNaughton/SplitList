package com.example.android.splitlist.ui.main.newItemsList;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.ItemClickListener;

public class NewItemListViewHolder extends RecyclerView.ViewHolder {

    private TextView mItemName;
    private String mName;
    private ItemClickListener mItemClickListener;

    public NewItemListViewHolder(View v, ItemClickListener clickListener) {
        super(v);

        mItemName = v.findViewById(R.id.new_item_name);
        mItemClickListener = clickListener;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View inputView) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemRowClick(mName);
                }
            }
        });
    }

    public void setData(String name) {

        mName = name;

        mItemName.setText(mName);
    }

}
