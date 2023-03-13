package com.example.carlocation.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.carlocation.R;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;


public class AddVehicle extends Fragment {

    private ImageView vehicleImage;
    private Button save;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vehilcle, container, false);
        vehicleImage = view.findViewById(R.id.imageView2);
        save = view.findViewById(R.id.saveVehicleBtn);
        vehicleImage.setBackgroundResource(R.drawable.pngegg);

        save.setOnClickListener(view1 -> {
           // Vehicle vehicle=new Vehicle()
        });


        return view;

    }

}