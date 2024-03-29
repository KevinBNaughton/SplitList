package com.example.android.splitlist.ui.main.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.android.splitlist.R;
import com.example.android.splitlist.ui.main.data.model.Item;
import com.example.android.splitlist.ui.main.groceryList.CheckoutItemListener;
import com.example.android.splitlist.ui.main.groceryList.FavoriteItemListener;
import com.example.android.splitlist.ui.main.groceryList.GroceryListAdapter;
import com.example.android.splitlist.ui.main.groceryList.LikeItemListener;
import com.example.android.splitlist.ui.main.newItemsList.NewItemDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListFragment extends Fragment {
     private static final String TAG = "ListFragment";
     private RecyclerView mRecyclerView;
     private ArrayList<Item> mGroceryList = new ArrayList<>();
     private GroceryListAdapter mListAdapter;
     private SwipeRefreshLayout mSwipeRefreshLayout;
     private Bundle b;
     private FirebaseAuth mFirebaseAuth;
     private FirebaseUser mFirebaseUser;

     private String mGroup_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        b = this.getArguments();

        mRecyclerView = view.findViewById(R.id.list_recyclerview);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItemDialog();
            }
        });

        setUpList();

        initList();

        return view;
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
                        Query query = db.collection("groups").document(mGroup_id)
                                .collection("items").whereEqualTo("checkout", false);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "second task successful: " + task.getResult().toString());
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, "we adding the document successful");
                                        Item item = document.toObject(Item.class);
                                        mGroceryList.add(item);
                                        mListAdapter.notifyDataSetChanged();
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

        mGroceryList = new ArrayList<>();
        mListAdapter = new GroceryListAdapter(mGroceryList);
        mRecyclerView.setAdapter(mListAdapter);

        mListAdapter.setListenerCallbacks(new OnCheckoutListenerHandler(), new OnLikeListenerHandler(), new OnFavoriteListenerHandler());
    }

    public void addItem(Item item) {
        mGroceryList.add(item);
        mListAdapter.notifyDataSetChanged();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("groups").document(mGroup_id).collection("items").add(item);
    }

    public void removeItem(Item item) {
        mGroceryList.remove(item);

        mListAdapter.notifyDataSetChanged();
    }

    private void newItemDialog() {
        NewItemDialog dialog = new NewItemDialog();
        dialog.setArguments(b);
        dialog.show(getActivity().getSupportFragmentManager(), getString(R.string.dialog_new_item));

        dialog.setDialogResult(new NewItemDialog.OnMyDialogResult(){
            public void finish(Item item){
                addItem(item);
            }
        });
    }

    class OnCheckoutListenerHandler extends CheckoutItemListener {
        @Override
        public void onItemCheckout(Item item) {
            Log.d("ListFragment", "Is hitting the remove checkout method");

            //TODO: need to do database stuffs here
            item.setCheckout(true);

        }
    }

    class OnLikeListenerHandler extends LikeItemListener {
        @Override
        public void onItemLiked(Item item) {
            Log.d("ListFragment", "Is hitting the heart click method");
        }
    }

    class OnFavoriteListenerHandler extends FavoriteItemListener {
        @Override
        public void onFavorite(Item item) {
            Log.d("ListFragmnet", "Is hitting the favorite method");

            //TODO: need logic here to do database stuff
        }
    }


    //TODO: this is going to be moved to item liked method
    public void checkForDelete(final Item item){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setMessage("Are you sure that you want to remove this item from your group's shopping list?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        removeItem(item);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}