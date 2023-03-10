package com.example.carlocation.controls.inteface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.R;
import com.example.carlocation.model.ParkEventsList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ParkEventsList parkEvents;

    public recyclerAdapter(ParkEventsList parkEvents) {
        this.parkEvents = parkEvents;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView addressText;
        private TextView dateAndTime;
        private TextView note;

        public MyViewHolder(final View view) {
            super(view);
            this.addressText = view.findViewById(R.id.adressTV);
            this.dateAndTime = view.findViewById(R.id.dateTimeTV);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String date = parkEvents.read(position).getDate();
        String address= parkEvents.read(position).getStreet().concat(", ").concat(parkEvents.read(position).getHouseNumber()).concat(", ").concat(parkEvents.read(position).getCity());
        holder.addressText.setText(address);
        holder.dateAndTime.setText(date);
    }

    @Override
    public int getItemCount() {
        return parkEvents.getMyList().size();
    }
}
