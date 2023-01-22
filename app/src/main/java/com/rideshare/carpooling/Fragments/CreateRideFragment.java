package com.rideshare.carpooling.Fragments;
/**
 * In this Fragment We will take Ride details as input
 * we will add Ride Details details to firebase
 */


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rideshare.carpooling.Model.Model;
import com.rideshare.carpooling.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class CreateRideFragment extends Fragment {

    FirebaseAuth mAuth;
    EditText  sourceAddressEditTxt, destinationAddressEditTxt, totalPassengersEditTxt,  rideTimeEditTxt, ridePriceEditTxt ;
    Button createRide,BtnPickDate,time1;
    String rideDate,t;
    TextView riderNameEditTxt,phoneNumberEditTxt;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static CreateRideFragment newInstance(String param1, String param2) {
        CreateRideFragment fragment = new CreateRideFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);

        //Assigning the address of android component to perform appropriate action
        riderNameEditTxt = (TextView) view.findViewById(R.id.RiderNameEdit);
        sourceAddressEditTxt = (EditText) view.findViewById(R.id.SourceAddressEdit);
        destinationAddressEditTxt = (EditText) view.findViewById(R.id.DestinationAddressEdit);
        totalPassengersEditTxt = (EditText) view.findViewById(R.id.TotalPassengersEdit);
        ridePriceEditTxt = (EditText) view.findViewById(R.id.RidePriceEdit);
        phoneNumberEditTxt = (TextView) view.findViewById(R.id.PhoneNumberEdit);
        BtnPickDate = (Button)view.findViewById(R.id.idBtnPickDate);
        //CreateRide Butotn to update the ride details in Fireabse database
        createRide = (Button) view.findViewById(R.id.CreateRideBtn);
        mAuth = FirebaseAuth.getInstance();


        time1=(Button) view.findViewById(R.id.time);
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time1.setText(selectedHour + ":" + selectedMinute);
                        t=(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        // on below line we are adding click listener for our pick date button
        BtnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                rideDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                BtnPickDate.setText(rideDate);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });


        //Firebase Database Path to access the user details and for showing user details
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId =user.getUid().toString();
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        //using the model class to get the data from the database
                        Model model = snapshot.getValue(Model.class);

                        //using  picasso repository to  load the image into image view from a url
                        riderNameEditTxt.setText(model.getUserName());
                        phoneNumberEditTxt.setText(model.getPhoneNumber());


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        //implementing the onclickListener to upload the Ride Details to fireabse on buttonClick
        createRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Gets the Test form the editTexts and assigning to Strings
                String riderName = riderNameEditTxt.getText().toString().trim().toLowerCase();
                String sourceAddress = sourceAddressEditTxt.getText().toString().trim().toLowerCase();
                String destinationAddress = destinationAddressEditTxt.getText().toString().trim().toLowerCase();
                String totalPassengers = totalPassengersEditTxt.getText().toString();
                String ridePrice = ridePriceEditTxt.getText().toString().trim().toLowerCase();
                String phoneNumber = phoneNumberEditTxt.getText().toString().trim();

                //Shows the below toast message if eny EditText field is empty
                if (riderName.isEmpty() || sourceAddress.isEmpty() || destinationAddress.isEmpty() || totalPassengers.isEmpty() || rideDate.isEmpty() || t.isEmpty() || ridePrice.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(view.getContext(), "Please, Fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    //Calls the below method to update the data in firebase
                    AddDataToFirebase(riderName, sourceAddress, destinationAddress, totalPassengers, rideDate, t, ridePrice, phoneNumber);
                }


            }
        });

        return view;
    }


    private void AddDataToFirebase(String riderName, String sourceAddress, String destinationAddress, String totalPassengers, String rideDate, String rideTime, String ridePrice, String phoneNumber) {

        //Accessing the google user id to create a unique ride  for unique users
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid().toString();

        //Fireabase dAtabase path to store the Ride dEtails
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Rides");

        //Creating the HashMap to store the Ride Details
        HashMap<String, String> user_details = new HashMap<>();


        //Assigining the Source Address and Destination Address to make Ride search Easy
        String sourceAddrAndDestinationAddr = sourceAddress + destinationAddress;

        //Storeing the ride Details in the HashMap
        user_details.put("riderName", riderName);
        user_details.put("sourceAddress", sourceAddress);
        user_details.put("destinationAddress", destinationAddress);
        user_details.put("sourceAddrAndDestinationAddr", sourceAddrAndDestinationAddr);
        user_details.put("rideDate", rideDate);
        user_details.put("rideTime", rideTime);
        user_details.put("ridePrice", ridePrice);
        user_details.put("totalPassengers", totalPassengers);

        user_details.put("userId", userId);
        user_details.put("phoneNumber", phoneNumber);

        //Setting the Ride details in the firebase Database
        myRef.child(userId).setValue(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    //Shows the bellow message when the Ride Details Successfully update to Database
                    Toast.makeText(getContext(), "Ride Created Successfully", Toast.LENGTH_SHORT).show();

                    //Setting the editText Fields to empty after updating the data in fireabse
                    riderNameEditTxt.setText("");
                    sourceAddressEditTxt.setText("");
                    destinationAddressEditTxt.setText("");
                    totalPassengersEditTxt.setText("");
                    ridePriceEditTxt.setText("");
                    phoneNumberEditTxt.setText("");

                }
            }
        });


    }
}



