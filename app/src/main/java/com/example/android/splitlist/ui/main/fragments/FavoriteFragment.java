package com.example.android.splitlist.ui.main.fragments;

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
import com.example.android.splitlist.ui.main.favoritesList.FavoriteListAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private static final String TAG = "FavoriteFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mFavoriteList = new ArrayList<>();
    private FavoriteListAdapter mFavoriteAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        mRecyclerView = view.findViewById(R.id.favorite_recyclerview);

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

        mFavoriteList = new ArrayList<>();
        mFavoriteAdapter = new FavoriteListAdapter(mFavoriteList);
        mRecyclerView.setAdapter(mFavoriteAdapter);

    }

    public void addItem(Item item) {
        mFavoriteList.add(item);

        mFavoriteAdapter.notifyDataSetChanged();
    }
}
