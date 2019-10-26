package com.example.android.splitlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.splitlist.ui.main.JoinGroupDialog;
import com.example.android.splitlist.ui.main.NewGroupDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class GroupActivity extends AppCompatActivity {

    private static final String TAG = "GroupActivity";
    private FirebaseAuth mFirebaseAuth;


    //Create and Join Buttons
    private Button mCreateButton;
    private Button mJoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        mCreateButton = findViewById(R.id.create_group);
        mJoinButton = findViewById(R.id.join_group);
        mFirebaseAuth = FirebaseAuth.getInstance();

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewGroupDialog dialog = new NewGroupDialog();
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_new_group));

                dialog.setDialogResult(new NewGroupDialog.OnMyDialogResult(){
                    public void finish(String result) {
                        Log.d(TAG, "result: " + result);
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();

                        final DocumentReference newGroupRef = db
                                .collection("groups")
                                .document();
                        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Map<String, Object> data = new HashMap<>();
                        data.put("group_name", result);
                        newGroupRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    String group_id = newGroupRef.getId();
                                    Map<String, Object> dat = new HashMap<>();
                                    dat.put("user_id", userId);
                                    db.collection("groups").document(group_id)
                                            .collection("users").document(userId).set(dat).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "Somehow, it worked");
                                        }
                                    });
                                } else{
                                    Toast.makeText(GroupActivity.this, "Group not created, notify support.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinGroupDialog dialog = new JoinGroupDialog();
                dialog.show(getSupportFragmentManager(), getString(R.string.dialog_new_group));

                dialog.setDialogResult(new JoinGroupDialog.OnMyDialogResult(){
                    public void finish(String result) {
                        Log.d(TAG, "result: " + result);
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final CollectionReference joinGroupReg = db.collection("groups");
                        Query groups = db.collection("groups").whereEqualTo("group_name", result);
                        groups.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, "data: " + document.getData());
                                        Log.d(TAG, "id: " + document.getId());
                                        Map<String, Object> dat = new HashMap<>();
                                        dat.put("user_id", userId);
                                        db.collection("groups").document(document.getId())
                                                .collection("users").document(userId).set(dat).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.d(TAG, "Somehow, it worked");
                                            }
                                        });
                                        break;
                                    }
                                } else {
                                    Log.e(TAG, "failed");
                                }
                            }
                        });
                    }
                });
            }
        });

    }
}
