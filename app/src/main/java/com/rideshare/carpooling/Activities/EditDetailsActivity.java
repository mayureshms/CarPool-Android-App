package com.rideshare.carpooling.Activities;
/**
 * In this activity we will take user inputs and update the details in Database
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rideshare.carpooling.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class EditDetailsActivity extends AppCompatActivity {

    EditText updateAdharNumber;
    Button submitDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        //Assigning the address of android components to perform Action
        updateAdharNumber = (EditText) findViewById(R.id.updateAdharNumberEditTxt);
        submitDataBtn = (Button) findViewById(R.id.submitDetailsBtn);
        //User id to get User Details from database
        String userId = GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId();

        //implementation of onClickListener to store data in firebase on click button
        submitDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get Text from the EditText
                String adharNumber = updateAdharNumber.getText().toString();
                if (adharNumber.isEmpty() || (adharNumber.length()!=12)) {

                    //Shows this toast if EditText is Empty
                    Toast.makeText(getApplicationContext(), "Please,Enter your Aadhaar Number & should be 16 digit", Toast.LENGTH_SHORT).show();

                } else {

                    //Firebase Path to Update data in database
                    FirebaseDatabase.getInstance().getReference().child("users")
                            .child(userId)
                            .child("adharNumber")
                            .setValue(adharNumber).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Aadhaar Number Updated Successfully", Toast.LENGTH_SHORT).show();
                                //Sets the EditText as Empty after Updating the data in Database
                                updateAdharNumber.setText("");

                            }

                        }
                    });
                }


            }
        });


    }
}