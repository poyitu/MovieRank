package com.qxy.movierank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;
import com.google.android.material.navigation.NavigationView;
import com.qxy.movierank.ui.MovieFragment;
import com.qxy.movierank.ui.TVseriesFragment;
import com.qxy.movierank.ui.VarietyShowFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView mNavigationView;
    private MovieFragment mMovieFragment;
    private TVseriesFragment mTVseriesFragment;
    private VarietyShowFragment mVarietyShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.nav_view);

        mMovieFragment = new MovieFragment();
        mTVseriesFragment = new TVseriesFragment();
        mVarietyShowFragment = new VarietyShowFragment();

        switchFragment(mMovieFragment);
        mNavigationView.setOnItemSelectedListener(navView);
       
    }

    private NavigationBarView.OnItemSelectedListener navView = new OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d(TAG, "==navigation_home==");
                    //Toast.makeText(MainActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    switchFragment(mMovieFragment);
                    return true;
                case R.id.navigation_dashboard:
                    Log.d(TAG, "==navigation_dashboard==");
                    //Toast.makeText(MainActivity.this, R.string.title_dashboard, Toast.LENGTH_SHORT).show();
                    switchFragment(mTVseriesFragment);
                    return true;
                case R.id.navigation_notifications:
                    Log.d(TAG, "==navigation_notifications==");
                    //Toast.makeText(MainActivity.this, R.string.title_notifications, Toast.LENGTH_SHORT).show();
                    switchFragment(mVarietyShowFragment);
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).commitNow();
    }

}