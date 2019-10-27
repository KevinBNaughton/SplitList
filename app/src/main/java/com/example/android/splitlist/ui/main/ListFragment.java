package com.example.android.splitlist.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.splitlist.R;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.example.android.splitlist.ui.main.groceryList.GroceryListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ListFragment extends Fragment {
     private static final String TAG = "ListFragment";
     private RecyclerView mRecyclerView;
     private ArrayList<String> mGroceryList = new ArrayList<>();
     private GroceryListAdapter mListAdapter;
     private SwipeRefreshLayout mSwipeRefreshLayout;
     private String token;
     private String baseUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Bundle b = this.getArguments();
        if (b != null) {
            token = b.getString("token");
            baseUrl = b.getString("baseUrl");
        }

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
        mListAdapter = new GroceryListAdapter(mGroceryList);
        mRecyclerView.setAdapter(mListAdapter);

        //TODO: set a swipe listener

    }

    private void addTestItem() {
        OkHttpClient client = new OkHttpClient();

        Request inventoryRequest = new Request.Builder()
                .url(baseUrl + "/v2/inventory/items")
                .header("Authorization","Bearer " + token)
                .build();

        client.newCall(inventoryRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    try {
                        JSONObject reader = new JSONObject(myResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mGroceryList.add("Milk!");
        mGroceryList.add("Eggs!");
    }

    public void addItem(String itemName) {
        mGroceryList.add(itemName);

        mListAdapter.notifyDataSetChanged();
    }

    private void newItemDialog() {
        NewItemDialog dialog = new NewItemDialog();
        dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_new_item));

        dialog.setDialogResult(new NewItemDialog.OnMyDialogResult(){
            public void finish(String result){
                addItem(result);
            }
        });
    }
}