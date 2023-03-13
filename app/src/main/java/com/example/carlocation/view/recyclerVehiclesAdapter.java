package com.example.carlocation.view;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carlocation.R;
import com.example.carlocation.model.VehicleList;

public class recyclerVehiclesAdapter extends RecyclerView.Adapter<recyclerVehiclesAdapter.MyViewHolder> {
    private VehicleList vehicleList;

    public recyclerVehiclesAdapter(VehicleList vehicleList) {
        this.vehicleList = vehicleList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView licensePlate;
        private TextView manufacturer;
        private TextView type;
        private TextView firstRegistration;
        private TextView note;
        private Button color;
        private ImageView imageView;

        public MyViewHolder(final View view) {
            super(view);
            this.name=view.findViewById(R.id.vehilclenameLb);
            this.licensePlate=view.findViewById(R.id.licensePlateLb);
            this.manufacturer=view.findViewById(R.id.manufacturerLb);
            this.type=view.findViewById(R.id.typeLb);
            this.firstRegistration=view.findViewById(R.id.firstRegistrationLb);
            this.note=view.findViewById(R.id.vehlicleNoteLb);
            this.color= view.findViewById(R.id.colorPickerBtn);
            this.imageView = view.findViewById(R.id.vehicleIV);
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
        if(vehicleList.read(position)==null) return;
        holder.name.setText(vehicleList.read(position).getName());
        holder.licensePlate.setText(vehicleList.read(position).getLicencePlate());
        holder.manufacturer.setText(vehicleList.read(position).getManufacturer());
        holder.type.setText(vehicleList.read(position).getType());
        holder.note.setText(vehicleList.read(position).getNote());
        holder.color.setBackgroundColor(Color.BLACK);

    }

    @Override
    public int getItemCount() {
        return vehicleList.getMyList().size();
    }
}
