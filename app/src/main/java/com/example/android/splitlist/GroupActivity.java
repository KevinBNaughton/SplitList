package com.example.android.splitlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = "GroupActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    //Create and Join Buttons
    private Button mCreateButton;
    private Button mJoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);



    }
}
