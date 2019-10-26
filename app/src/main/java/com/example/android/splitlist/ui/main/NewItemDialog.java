package com.example.android.splitlist.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.android.splitlist.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by User on 5/14/2018.
 */

public class NewItemDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "NewItemDialog";

    //widgets
    private EditText mTitle, mContent;
    private TextView mCreate, mCancel, mRadiusProgress;
    private SeekBar mRadiusBar;
    private View mRed, mAzure, mBlue, mCyan, mMagenta, mOrange, mViolet, mRose, mYellow;
    private Switch mRadiusSwitch;
    private RelativeLayout mRadiusWidget;
    private Spinner mPrivacy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_item, container, false);
        mTitle = view.findViewById(R.id.item_title);
        mContent = view.findViewById(R.id.item_content);
        mCreate = view.findViewById(R.id.create);
        mCancel = view.findViewById(R.id.cancel);

        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        //DocumentReference user = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getDialog().setTitle("New Item");

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.create:{

                // insert the new note

                String title = mTitle.getText().toString();
                String content = mContent.getText().toString();

                if(!title.equals("")) {

                    //THIS IS THE FUNCTION TO CREATE A NEW ITEM IN THE LIST
                    //mIMapsActivity.createNewLocationNote(title, content, latlng, color, locationCheck, radius, privacy);
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getActivity(), "Enter a title", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.cancel:{
                getDialog().dismiss();
                break;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Attach Interface to call functions in main
        //mIMapsActivity = (IMapsActivity) getActivity();
    }

}