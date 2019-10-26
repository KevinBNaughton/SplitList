package com.example.android.splitlist.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.splitlist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListFragment extends Fragment {
     private static final String TAG = "ListFragment";
     private RecyclerView mRecyclerView;
     private ArrayList<String> mGroceryList = new ArrayList<>();
     private GroceryListAdapter mListAdapter;
     private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = view.findViewById(R.id.list_recyclerview);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemDialog();
            }
        });

        setUpList();

        addTestItem();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpList();

        addTestItem();
    }

    private void setUpList() {

        mRecyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);

        mGroceryList = new ArrayList<>();
        mListAdapter = new GroceryListAdapter(mGroceryList);
        mRecyclerView.setAdapter(mListAdapter);

        //TODO: set a swipe listener

    }

    private void addTestItem() {
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");

        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");

        mListAdapter.notifyDataSetChanged();
    }

    private void newItemDialog() {
        NewItemDialog dialog = new NewItemDialog();
        dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_new_item));
    }
}