package com.example.carlocation.view;

import static android.content.ContentValues.TAG;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.R;
import com.example.carlocation.model.ParkEventsList;

public class recyclerParkEventAdapter extends RecyclerView.Adapter<recyclerParkEventAdapter.MyViewHolder> {

    private ParkEventsList parkEvents;

    public recyclerParkEventAdapter(ParkEventsList parkEvents) {
        this.parkEvents = parkEvents;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView addressText;
        private TextView dateAndTime;
        private TextView note;
        private ImageView imageView;

        public MyViewHolder(final View view) {
            super(view);
            this.addressText = view.findViewById(R.id.adressTV);
            this.dateAndTime = view.findViewById(R.id.dateTimeTV);
            this.imageView = view.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public recyclerParkEventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerParkEventAdapter.MyViewHolder holder, int position) {
        if (parkEvents.read(position) == null) return;
        String date = parkEvents.read(position).getDate();


// Retrieve the street, house number, and city values from the event
        String street = parkEvents.read(position).getStreet();
        String houseNumber = parkEvents.read(position).getHouseNumber();
        String city = parkEvents.read(position).getCity();

// Check if any of the values are null, and replace them with "unknown" if necessary
        street = (street == null) ? "unknown" : street;
        houseNumber = (houseNumber == null) ? "unknown" : houseNumber;
        city = (city == null) ? "unknown" : city;

// Concatenate the street, house number, and city into a single string with a newline character
        String address = street + " " + houseNumber + "\n" + city;
        holder.addressText.setText(address);
        holder.dateAndTime.setText(date);
        holder.imageView.setBackgroundResource(R.drawable.pngegg);
        Log.d(TAG, "onBindViewHolder: Changed image");

        if (position == 0) {
            holder.imageView.setBackgroundResource(R.drawable.pngegg);
            Log.d(TAG, "onBindViewHolder: Changed image");
        }
    }

    @Override
    public int getItemCount() {
        return parkEvents.getMyList().size();
    }
}
