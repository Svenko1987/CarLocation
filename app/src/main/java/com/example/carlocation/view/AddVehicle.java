package com.example.carlocation.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;


public class AddVehicle extends Fragment {

    private ImageView vehicleImage;
    private Button save;
    private VehicleList vehicleList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_vehilcle, container, false);
        vehicleImage = view.findViewById(R.id.imageView2);
        save = view.findViewById(R.id.saveVehicleBtn);
        vehicleImage.setBackgroundResource(R.drawable.pngegg);
        ListCRUD<Vehicle> crud= new ListCRUD<>(getActivity(),"myVehiclesList.json");
        vehicleList= new VehicleList(crud.loadList());


        save.setOnClickListener(view1 -> {
            Vehicle  vehicle= new Vehicle("320","t71j208","BMW","2001","","","");
            VehicleList vehicleList=new VehicleList(crud.loadList());
            vehicleList.create(vehicle);
            crud.updateList(vehicleList.getMyList());
            Log.d(TAG, "onCreateView: SAVED IN LIST");
        });


        return view;

    }

}