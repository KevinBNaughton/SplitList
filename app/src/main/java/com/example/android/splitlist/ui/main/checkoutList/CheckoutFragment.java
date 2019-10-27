package com.example.android.splitlist.ui.main.checkoutList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;
import com.example.android.splitlist.ui.main.groceryList.GroceryListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CheckoutFragment extends Fragment {
    private static final String TAG = "CheckoutFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mCheckoutList = new ArrayList<>();
    private CheckoutListAdapter mCheckoutAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        mRecyclerView = view.findViewById(R.id.checkout_recyclerview);

        setUpList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpList();

    }

    private void setUpList() {

        mRecyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);

        mCheckoutList = new ArrayList<>();
        mCheckoutAdapter = new CheckoutListAdapter(mCheckoutList);
        mRecyclerView.setAdapter(mCheckoutAdapter);

    }

    public void addItem(Item item) {
        mCheckoutList.add(item);

        mCheckoutAdapter.notifyDataSetChanged();
    }
}
