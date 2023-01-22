package com.rideshare.carpooling.Fragments;

/**
 * We will show the user details in this fragment and signOut Option
 */

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.rideshare.carpooling.Activities.EditDetailsActivity;
import com.rideshare.carpooling.Activities.StartingActivity;
import com.rideshare.carpooling.Model.Model;
import com.rideshare.carpooling.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView circleImageView;
    TextView userName, adharNumber;
    Button signOutBtn, editDetailsBtn;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Assigning the Addresses of the android components to show the data and perform appropriate action
        userName = (TextView) view.findViewById(R.id.UserNameTxt);
        adharNumber = (TextView) view.findViewById(R.id.adharNumberTxt);

        //Signout Button to make the user signout from the current account
        signOutBtn = (Button) view.findViewById(R.id.SignOutBtn);

        //edit details button to make the user change his Details
        editDetailsBtn = (Button) view.findViewById(R.id.EditDetailsBtn);

        //implementing the OnclickListener to change the Activity
        editDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //When we click on the editDetails Button. We will redirect to EditDetails Activity
                Intent intent = new Intent(getContext(), EditDetailsActivity.class);
                startActivity(intent);

            }
        });


        //Firebase Database Path to access the user details and for showing user details
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid().toString();
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        //using the model class to get the data from the database
                        Model model = snapshot.getValue(Model.class);

                        //using  picasso repository to  load the image into image view from a url

                        userName.setText(model.getUserName());
                        adharNumber.setText(model.getMobile());


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        //implementing the signOut
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                //Accesses the current users
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //makes the user Signout From the App
                            FirebaseAuth.getInstance().signOut();

                            //Changes the intent after user Signout
                            Intent intent = new Intent(getContext(), StartingActivity.class);

                            //Clears all the previous intents
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }

                    }
                });

            }
        });


        return view;
    }
}