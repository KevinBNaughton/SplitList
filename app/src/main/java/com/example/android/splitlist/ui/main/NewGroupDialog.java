package com.example.android.splitlist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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


public class NewGroupDialog extends DialogFragment {

    private OnMyDialogResult mDialogResult;

    private TextView mCreate;
    private EditText mName;

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
        View view = inflater.inflate(R.layout.dialog_new_group, container, false);

        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference user = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getDialog().setTitle("Enter New Group Name!");

        mName = view.findViewById(R.id.group_name);
        mCreate = view.findViewById(R.id.create);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                mDialogResult.finish(name);
                dismiss();
            }
        });
        return view;
    }


    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }

}