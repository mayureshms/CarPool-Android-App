package com.rideshare.carpooling.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rideshare.carpooling.MainActivity;
import com.rideshare.carpooling.Model.Model;
import com.rideshare.carpooling.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Adapter extends FirebaseRecyclerAdapter<Model, Adapter.Viewholder> {

    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull Viewholder holder, int position, @NonNull Model model) {


        //Gets The Data from Firebase Database using model class and sets the individual data in Holder
        holder.riderName.setText("Rider Name: " + model.getRiderName());
        holder.sourceAddress.setText("Source Address: " + model.getSourceAddress());
        holder.destinationAddress.setText("Destination Address: " + model.getDestinationAddress());
        holder.totalPassengers.setText("Total Passengers: " + model.getTotalPassengers());
        holder.rideDate.setText("Ride date: " + model.getRideDate());
        holder.rideTime.setText("Ride Time: " + model.getRideTime());
        holder.ridePrice.setText("Ride Pirce: " + model.getRidePrice());
        holder.riderPhoneNumber.setText("Rider Mobile: " + model.getPhoneNumber());


        //Implementing the onClickListner to Book Ticket For User
        holder.bookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                //Accessing No of Seats available in the Vehicle and decreases the value when user books one seat
                int totalPassengersInt = Integer.parseInt(model.getTotalPassengers()) - 1;

                //Books seats for the user if total available seats are greater than 0
                if (totalPassengersInt > 0) {

                    //Decreases the Total available seats in database
                    FirebaseDatabase.getInstance().getReference().child("Rides").child(model.getUserId()).child("totalPassengers").setValue(String.valueOf(totalPassengersInt));
                    Toast.makeText(view.getContext(), "Successfully Registered for Ride", Toast.LENGTH_SHORT).show();

                    //Restarts the intent to clear all the values after booking seat
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else {

                    //Shows this toast if total seats are booked in vehicle
                    Toast.makeText(context, "Please,Choose another Ride", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ride_detilas, parent, false);
        return new Viewholder(view);
    }

    //ViewHolder to hold each individual data object and to show it in Recyclerview
    class Viewholder extends RecyclerView.ViewHolder {

        TextView riderName, sourceAddress, destinationAddress, totalPassengers, rideDate, rideTime, ridePrice, riderPhoneNumber;
        Button bookRide;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


            //Assigning the Address of Android components to show data in the appropriate component
            riderName = (TextView) itemView.findViewById(R.id.RiderNameTxt);
            sourceAddress = (TextView) itemView.findViewById(R.id.SourceAddressTxt);
            destinationAddress = (TextView) itemView.findViewById(R.id.DestinationAddressTxt);
            totalPassengers = (TextView) itemView.findViewById(R.id.TotalPassengersTxt);
            rideDate = (TextView) itemView.findViewById(R.id.RideDateTxt);
            rideTime = (TextView) itemView.findViewById(R.id.RideTimeTxt);
            ridePrice = (TextView) itemView.findViewById(R.id.RidePriceTxt);
            riderPhoneNumber = (TextView) itemView.findViewById(R.id.RiderPhoneNumber);

            //Button To book a seat for the user
            bookRide = (Button) itemView.findViewById(R.id.BookRideBtn);


        }
    }

}