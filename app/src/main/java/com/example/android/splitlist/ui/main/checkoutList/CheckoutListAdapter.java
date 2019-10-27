package com.example.android.splitlist.ui.main.checkoutList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;
import com.example.android.splitlist.ui.main.groceryList.GroceryViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CheckoutListAdapter extends RecyclerView.Adapter<CheckoutViewHolder> {

    private List<Item> mCheckoutList;

    public CheckoutListAdapter(ArrayList<Item> checkoutList) {
        mCheckoutList = checkoutList;
    }

    @Override
    public CheckoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkout_item_row, parent, false);

        CheckoutViewHolder viewHolder = new CheckoutViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CheckoutViewHolder holder, int position) {

        if (mCheckoutList != null && mCheckoutList.size() > position) {
            Item item = mCheckoutList.get(position);

            holder.setData(item);
            Log.d("Set the data", "yeah");
        }
    }

    @Override
    public int getItemCount() {
        return mCheckoutList.size();
    }

}
