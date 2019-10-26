package com.example.android.splitlist.ui.main;

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
import com.example.android.splitlist.ui.main.newItemsList.NewItemAdapter;

import java.util.ArrayList;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by User on 5/14/2018.
 */

public class JoinGroupDialog extends DialogFragment {

    private RecyclerView mRecyclerView;

    private ArrayList<String> mNewItems;

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
        mNewItems.add("PLEASE");
        mNewItems.add("WORK");
        mNewItems.add("BUYFOODS");
        mNewItems.add("PLEASE");
        mNewItems.add("WORK");
        mNewItems.add("BUYFOODS");
        mNewItems.add("PLEASE");
        mNewItems.add("WORK");
        mNewItems.add("BUYFOODS");
        mNewItems.add("PLEASE");
        mNewItems.add("WORK");
        mNewItems.add("BUYFOODS");
        mNewItems.add("PLEASE");
        mNewItems.add("WORK");
        mNewItems.add("BUYFOODS");

        mNewItemsAdapter.notifyDataSetChanged();
    }

    class OnRowClickHandler extends ItemClickListener {
        @Override
        public void onItemRowClick(String name) {
            mDialogResult.finish(name);

            dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }

}