package com.rideshare.carpooling.Activities;
/**
 * In this activity we show the intro of this app
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rideshare.carpooling.R;

public class StartingActivity extends AppCompatActivity {

    Button getStartedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        //Assigning the Button to perform  Appropriate Action
        getStartedBtn = (Button) findViewById(R.id.GetStartedBtn);

        //implementing the onClickListener to Change the intent
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}