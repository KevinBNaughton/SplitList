package com.example.android.splitlist;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.splitlist.ui.main.fragments.FavoriteFragment;
import com.example.android.splitlist.ui.main.fragments.ListFragment;
import com.example.android.splitlist.ui.main.fragments.CheckoutFragment;
import com.example.android.splitlist.ui.main.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    //Navigation Draw
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private TextView mProfileName;
    private Switch mTutorialSwitch;

    //Tab Fragments
    private TabAdapter mTabAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int[] tabIcons = {
            R.drawable.ic_list,
            R.drawable.ic_checkout,
            R.drawable.ic_favorites
    };

    private Bundle b = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // *** This is for checking if user is logged in on app launch, enable later

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            // Load it up.
            init();
        }
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void init() {
        OkHttpClient client = new OkHttpClient();
        b.putString("baseUrl", "https://api-reg-apigee.ncrsilverlab.com");

        Request tokenRequest = new Request.Builder()
                .url(b.get("baseUrl") + "/v2/oauth2/token")
                .header("client_id", "gt_552465")
                .header("client_secret", "00340075-0043-0050-7600-260041005200")
                .build();

        client.newCall(tokenRequest).
                enqueue(new Callback() {
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
                                JSONObject result = reader.getJSONObject("Result");
                                b.putString("token", result.getString("AccessToken"));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragmentHelper();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void fragmentHelper() {
        // [START] Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        View navView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        mProfileName = (TextView) navView.findViewById(R.id.profile_name);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavDrawerMenuSelector(item);
                return false;
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("SplitList");

        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mActionBarDrawerToggle.syncState();
        // [END] Navigation Drawer

        // [START] Tab Layout
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mTabAdapter = new TabAdapter(getSupportFragmentManager(), this);

        ListFragment list = new ListFragment();
        list.setArguments(b);
        mTabAdapter.addFragment(list, "List", tabIcons[0]);
        CheckoutFragment checkout = new CheckoutFragment();
        checkout.setArguments(b);
        mTabAdapter.addFragment(checkout, "Checkout", tabIcons[1]);
        FavoriteFragment favorites =  new FavoriteFragment();
        favorites.setArguments(b);
        mTabAdapter.addFragment(favorites, "Favorites", tabIcons[2]);
        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        highLightCurrentTab(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        // [END] Tab Layout
        mProfileName.setText(mFirebaseUser.getDisplayName());

        //Checking that user is in a group
        String user_id = mFirebaseUser.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(user_id);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("group_id") == null) {
                            Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        //END Checking that user is in a group

    }

    private void NavDrawerMenuSelector(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home: {
                //Go HOME
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_groups: {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(mFirebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String group_id = document.getString("group_id");
                                db.collection("groups").document(group_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if (documentSnapshot.exists()) {
                                                String group_name = documentSnapshot.getString("group_name");
                                                Toast.makeText(MainActivity.this, "You are in group \"" + group_name +  "\"!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
                //Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                //startActivity(intent);
                break;
            }
            case R.id.nav_settings: {
                Toast.makeText(this, "Settings Selected!", Toast.LENGTH_SHORT).show();
                //SWITCH SETTINGS ACTIVITY
                break;
            }
            case R.id.nav_logout: {
                //Toast.makeText(this, "Logout Selected!", Toast.LENGTH_SHORT).show();
                //LOG OUT
                logOut();
                break;
            }
        }

    }

    private void logOut() {
        // Firebase sign out
        mFirebaseAuth.signOut();
        loadLogInView();
    }

    //Position of tab, 0 1 or 2.
    private void highLightCurrentTab(int position) {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(mTabAdapter.getTabView(i));
        }TabLayout.Tab tab = mTabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(mTabAdapter.getSelectedTabView(position));
    }

}