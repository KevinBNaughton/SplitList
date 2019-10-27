package com.example.android.splitlist.ui.main.newItemsList;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.ItemClickListener;
import com.example.android.splitlist.ui.main.data.model.Item;
import com.example.android.splitlist.ui.main.groceryList.GroceryListAdapter;
import com.example.android.splitlist.ui.main.newItemsList.NewItemAdapter;
import com.google.android.material.tabs.TabLayout;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by User on 5/14/2018.
 */

public class NewItemDialog extends DialogFragment {
    private static final String TAG = "DialogFragment";

    private RecyclerView mRecyclerView;

    private ArrayList<Item> mNewItems;

    private NewItemAdapter mNewItemsAdapter;

    private OnMyDialogResult mDialogResult;

    private String token;
    private String baseUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getArguments();
        if (b != null) {
            token = b.getString("token");
            baseUrl = b.getString("baseUrl");
        }

        //TODO: fix the theme bc its ugly
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_item, container, false);

        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference user = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getDialog().setTitle("Select a new item!");

        setUpRecyclerView(view);

        popuplateList();

        return view;
    }

    private void setUpRecyclerView(View view) {

        mRecyclerView = view.findViewById(R.id.new_item_row);

        mRecyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);

        mNewItems = new ArrayList<>();
        mNewItemsAdapter = new NewItemAdapter(mNewItems);
        mRecyclerView.setAdapter(mNewItemsAdapter);

        mNewItemsAdapter.setRowClickListnerCallback (new OnRowClickHandler());

    }

    private void popuplateList() {
        OkHttpClient client = new OkHttpClient();

        Request inventoryRequest = new Request.Builder()
                .url(baseUrl + "/v2/inventory/items?limit=138")
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
                        JSONArray result = reader.getJSONArray("Result");
                        Log.d(TAG, "Length: " + result.length());
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject newItem = result.getJSONObject(i);
                            mNewItems.add(new Item(newItem.getString("Name"),
                                    newItem.getDouble("RetailPrice"),
                                    newItem.getInt("ItemMasterId")));
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mNewItemsAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    class OnRowClickHandler extends ItemClickListener {
        @Override
        public void onItemRowClick(Item item) {
            mDialogResult.finish(item);

            dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(Item item);
    }

}