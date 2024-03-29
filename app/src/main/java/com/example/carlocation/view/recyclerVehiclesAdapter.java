package com.example.carlocation.view;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.R;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;

public class recyclerVehiclesAdapter extends RecyclerView.Adapter<recyclerVehiclesAdapter.MyViewHolder> {
    private VehicleList vehicleList;
    private SelectListener<Vehicle> listener;

    public recyclerVehiclesAdapter(VehicleList vehicleList,SelectListener<Vehicle> listener) {
        this.vehicleList = vehicleList;
        this.listener=listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView licensePlate;
        private TextView firstRegistration;
        private TextView note;
        private Button color;
         public CardView cardView;

        public MyViewHolder(final View view) {
            super(view);
            this.name = view.findViewById(R.id.vehicleNameLb);
            this.licensePlate = view.findViewById(R.id.licensePlateLb);
            this.cardView= view.findViewById(R.id.vehicleCV);

            this.firstRegistration = view.findViewById(R.id.firstRegistrationLb);
            this.note = view.findViewById(R.id.vehlicleNoteLb);
            this.color = view.findViewById(R.id.colorPickerBtn);

        }
    }

    @NonNull
    @Override
    public recyclerVehiclesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerVehiclesAdapter.MyViewHolder holder, int position) {
        if (vehicleList.read(position) == null) return;
        holder.name.setText(vehicleList.read(position).getName());
        holder.licensePlate.setText(vehicleList.read(position).getLicencePlate());
        holder.note.setText(vehicleList.read(position).getNote());
        holder.firstRegistration.setText(vehicleList.read(position).getFirstRegistration());
        if (TextUtils.isEmpty(vehicleList.read(position).getColor())) {
            holder.color.setBackgroundColor(Color.BLACK);
            return;
        }
        holder.color.setBackgroundColor(Integer.parseInt(vehicleList.read(position).getColor()));
        holder.cardView.setOnClickListener(view -> {
            listener.onItemClicked(vehicleList.read(position));
        });

    }

    @Override
    public int getItemCount() {
        return vehicleList.getMyList().size();
    }
}
