package com.rideshare.carpooling;
/**
 * We Performs the Bottom Navigation in this activity
 * we will check whether user logged in or not and redirect to Starting activity if not login
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rideshare.carpooling.Activities.StartingActivity;
import com.rideshare.carpooling.Fragments.CreateRideFragment;
import com.rideshare.carpooling.Fragments.HomeFragment;
import com.rideshare.carpooling.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mbottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning the bottom navigation to navigate between the fragments
        mbottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationView);
        Menu menuNav = mbottomNavigationView.getMenu();

        //setting the homeFragment as default Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new HomeFragment()).commit();

        mbottomNavigationView.setOnNavigationItemSelectedListener(bottomnavMethod);
    }


    @Override
    protected void onStart() {
        super.onStart();

        //checks the user is loggedIn or not
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) {
            //Redirect to the starting Activity if the user is not logged
            Intent intent = new Intent(MainActivity.this, StartingActivity.class);
            startActivity(intent);
            finish();
        }
    }


    //Performs the Bottom Navigation aciton
    private BottomNavigationView.OnNavigationItemSelectedListener bottomnavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    //Setting the fragment to null
                    Fragment fragment = null;
                    switch (item.getItemId()) {

                        //Navigates between the activities by using the id of the menu
                        case R.id.homeMenu:
                            fragment = new HomeFragment();
                            break;
                        case R.id.createRideMenu:
                            fragment = new CreateRideFragment();
                            break;
                        case R.id.profileMenu:
                            fragment = new ProfileFragment();
                            break;

                    }
                    //Replaces the fragment in the FrameLayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, fragment).commit();
                    return true;
                }
            };
}
