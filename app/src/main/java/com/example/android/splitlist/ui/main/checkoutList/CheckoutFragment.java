package com.example.android.splitlist.ui.main.checkoutList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.CompleteActivity;
import com.example.android.splitlist.ui.main.data.model.Item;
import com.example.android.splitlist.ui.main.groceryList.GroceryListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Nullable;

public class CheckoutFragment extends Fragment {
    private static final String TAG = "CheckoutFragment";
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mCheckoutList = new ArrayList<>();
    private CheckoutListAdapter mCheckoutAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Button mCompleteButton;
    private String mGroup_id;
    private HashMap<String, Double> priceMap;
    private TextView mPersonalTotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        mRecyclerView = view.findViewById(R.id.checkout_recyclerview);

        mPersonalTotal = view.findViewById(R.id.personal_total);
        mCompleteButton = view.findViewById(R.id.complete_button);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        setUpList();

        initList();

        mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CompleteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    //Called within initList so group_id is already got.
    private void pricing() {
        Log.d(TAG, "pricing()");
        priceMap = new HashMap<>();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("groups").document(mGroup_id).collection("items")
            .whereEqualTo("checkout", true).get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "we adding the document successful");
                        String item_id = document.getId();
                        double price = (double) document.get("price") / (long) document.get("likeNumber");
                        ArrayList<String> list = (ArrayList<String>) document.get("users");
                        for (String userID : list) {
                            if (mFirebaseUser.getUid().equals(userID)) {
                                priceMap.put(item_id, price);
                            }
                        }
                    }
                    double personal_price = 0;
                    for (String key : priceMap.keySet()) {
                        personal_price += priceMap.get(key);
                    }
                    Log.d(TAG, "We pay: " + personal_price);
                    mPersonalTotal.setText("Your Total: " + personal_price);
                } else {

                }
            }
        });

    }

    private void initList() {
        Log.d(TAG, "initList()");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(mFirebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "first task successful: " + task.getResult().toString());
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "first document exists");
                        mGroup_id = document.getString("group_id");
                        pricing();
                        Query query = db.collection("groups").document(mGroup_id)
                                .collection("items").whereEqualTo("checkout", true);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "second task successful: " + task.getResult().toString());
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, "we adding the document successful");
                                        Item item = document.toObject(Item.class);
                                        mCheckoutList.add(item);
                                        mCheckoutAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    } else {
                        Log.d(TAG, "NOT first document exists");
                    }
                } else {
                    Log.d(TAG, "NOT first task successful");
                }
            }
        });
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
