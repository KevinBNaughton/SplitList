package com.example.android.splitlist;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.splitlist.ui.main.ListFragment;
import com.example.android.splitlist.ui.main.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.splitlist.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        mTabAdapter.addFragment(new ListFragment(), "List", tabIcons[0]);
        mTabAdapter.addFragment(new ListFragment(), "Checkout", tabIcons[1]);
        mTabAdapter.addFragment(new ListFragment(), "Favorites", tabIcons[2]);

        mViewPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        highLightCurrentTab(0);
        // [END] Tab Layout






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
                Toast.makeText(this, "Groups Selected!", Toast.LENGTH_SHORT).show();
                //VIEW FRIENDS
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