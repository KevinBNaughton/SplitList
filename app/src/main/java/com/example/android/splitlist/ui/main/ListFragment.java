package com.example.android.splitlist.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.splitlist.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.example.android.splitlist.ui.main.groceryList.DeleteItemListener;
import com.example.android.splitlist.ui.main.groceryList.GroceryListAdapter;
import com.example.android.splitlist.ui.main.groceryList.LikeItemListener;
import com.example.android.splitlist.ui.main.groceryList.SwipeItemListener;
import com.example.android.splitlist.ui.main.newItemsList.NewItemDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ListFragment extends Fragment {
     private static final String TAG = "ListFragment";
     private RecyclerView mRecyclerView;
     private ArrayList<Items> mGroceryList = new ArrayList<>();
     private GroceryListAdapter mListAdapter;
     private SwipeRefreshLayout mSwipeRefreshLayout;
     private Bundle b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        b = this.getArguments();

        mRecyclerView = view.findViewById(R.id.list_recyclerview);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemDialog();
            }
        });

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

        mGroceryList = new ArrayList<>();
        mListAdapter = new GroceryListAdapter(getContext(), mGroceryList);
        mRecyclerView.setAdapter(mListAdapter);

        mListAdapter.setListenerCallbacks(new OnDeleteListenerHandler(), new OnLikeListenerHandler(), new OnSwipeListenerHandler());
    }

    private void addTestItem() {
        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
    }

    public void addItem(String itemName) {
        mGroceryList.add(itemName);

        mListAdapter.notifyDataSetChanged();
    }

    //TODO: refactor to remove an object
    public void removeItem(String name) {
        mGroceryList.remove(name);

        mListAdapter.notifyDataSetChanged();
    }

    private void newItemDialog() {
        NewItemDialog dialog = new NewItemDialog();
        dialog.setArguments(b);
        dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_new_item));

        dialog.setDialogResult(new NewItemDialog.OnMyDialogResult(){
            public void finish(String result){
                addItem(result);
            }
        });
    }

    class OnDeleteListenerHandler extends DeleteItemListener {
        @Override
        public void onItemDelete(String name) {
            Log.d("ListFragment", "Is hitting the remove click method");
            //TODO: popup check
            removeItem(name);
        }
    }

    class OnLikeListenerHandler extends LikeItemListener {
        @Override
        public void onItemLiked(String name) {
            Log.d("ListFragment", "Is hitting the heart click method");

            addItem("THE LIKE BUTTON WORKS");
            //TODO: method to update something
        }
    }

    class OnSwipeListenerHandler extends SwipeItemListener {
        @Override
        public void moveToCheckout(String name) {

            Log.d("ListFragment", "Is hitting the swipe listener method");

            //TODO: move to checkout list
            addItem("RIGHT SWIPE DO WORKKKK");

            removeItem(name);
        }
    }
}