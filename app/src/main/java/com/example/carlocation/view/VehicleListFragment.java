package com.example.carlocation.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;


public class VehicleListFragment extends Fragment {

    VehicleList vehicleList;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vehicle_list, container, false);
        recyclerView=view.findViewById(R.id.vehiclesRW);

        ListCRUD<Vehicle> crud= new ListCRUD<>(getActivity(),"myVehiclesList.json");
        vehicleList= new VehicleList(crud.loadList());
        
        setAdapterVehicles();


        return view;
    }

    private void setAdapterVehicles() {
        recyclerVehiclesAdapter adapter=new recyclerVehiclesAdapter(vehicleList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}