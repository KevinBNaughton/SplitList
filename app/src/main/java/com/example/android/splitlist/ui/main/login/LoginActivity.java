package com.example.android.splitlist.ui.main.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.splitlist.MainActivity;
import com.example.android.splitlist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";


    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private Button registerButton;
    private EditText verifypasswordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        registerButton = findViewById(R.id.register);
        verifypasswordEditText = findViewById(R.id.password_verify);

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "LOGIN PRESSED", Toast.LENGTH_SHORT).show();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                email = email.trim();
                password = password.trim();
                loadingProgressBar.setVisibility(View.VISIBLE);

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage(task.getException().getMessage())
                                            .setTitle(R.string.login_error_title)
                                            .setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                    loadingProgressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifypasswordEditText.getVisibility() == View.INVISIBLE) {
                    verifypasswordEditText.setVisibility(View.VISIBLE);
                } else {
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String verifyPassword = verifypasswordEditText.getText().toString();

                    email = email.trim();
                    password = password.trim();
                    verifyPassword = verifyPassword.trim();

                    if (email.isEmpty() || password.isEmpty() || verifyPassword.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.login_error_message)
                                .setTitle(R.string.login_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                    } else if (!password.equals(verifyPassword)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.login_error_message2)
                                .setTitle(R.string.login_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        loadingProgressBar.setVisibility(View.INVISIBLE);
                    } else {
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "onCreate: success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        // Got below code from https://stackoverflow.com/questions/38114358/firebase-setdisplayname-of-user-while-creating-user-android
//                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                                .setDisplayName(mName).build();

//                                        user.updateProfile(profileUpdates)
//                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<Void> task) {
//                                                        if (task.isSuccessful()) {
//                                                            Log.d(TAG, "onCreate: Name updated to profile.");
//                                                        } else {
//                                                            Log.d(TAG, "onCreate: **FAILURE** Name updated to profile.");
//                                                        }
//                                                    }
//                                                });
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        DocumentReference newUser = db
                                                .collection("users")
                                                .document(user.getUid());
                                        HashMap<String, String> userMap = new HashMap<String, String>();
                                        userMap.put("user_id", newUser.getId());
                                        newUser.set(userMap);
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d(TAG, "onCreate: failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Sign up failed. Contact support if persists.", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            });
                    }
                }
            }
        });

    }

    // [START updateUI]
    // When a user is made in log in from instance, go to main activity logged in.
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    // [END updateUI]


}
