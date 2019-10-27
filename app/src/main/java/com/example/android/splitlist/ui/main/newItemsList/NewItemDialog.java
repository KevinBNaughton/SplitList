package com.example.android.splitlist.ui.main.newItemsList;

import android.os.Bundle;
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

import java.util.ArrayList;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by User on 5/14/2018.
 */

public class NewItemDialog extends DialogFragment {

    private RecyclerView mRecyclerView;

    private ArrayList<Item> mNewItems;

    private NewItemAdapter mNewItemsAdapter;

    private OnMyDialogResult mDialogResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mRecyclerView = view.findViewById(R.id.new_item_list);

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

        mNewItems.add(new Item("Milk", 6.00, 102578, 1920845));
        mNewItems.add(new Item("Water", 2.75, 102578, 1920845));
        mNewItems.add(new Item("Eggs", 97.56, 102578, 1920845));
        mNewItems.add(new Item("Coffee", 5.30, 102578, 1920845));
        mNewItems.add(new Item("School Textbook", 134.89, 102578, 1920845));
        mNewItems.add(new Item("Computer", 2.10, 102578, 1920845));

        mNewItemsAdapter.notifyDataSetChanged();
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