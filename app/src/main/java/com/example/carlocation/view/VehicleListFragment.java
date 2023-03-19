package com.example.carlocation.view;

import static android.content.Context.MODE_PRIVATE;
import static com.example.carlocation.MainActivity.MyPREFERENCES;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.controls.logic.SharedPreferencesManagerParkEvent;
import com.example.carlocation.controls.logic.SharedPreferencesManagerVehicle;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;


public class VehicleListFragment extends Fragment implements SelectListener<Vehicle> {

    VehicleList vehicleList;
    RecyclerView recyclerView;
    ListCRUD<Vehicle> crud;

    private SharedPreferences sharedPreferences;
    private SharedPreferencesManagerVehicle manager;
    Vehicle vehicle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vehicle_list, container, false);
        recyclerView=view.findViewById(R.id.vehiclesRW);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        manager = new SharedPreferencesManagerVehicle(sharedPreferences,vehicle);



        crud= new ListCRUD<>(getActivity(),"myVehiclesList.json");
        vehicleList= new VehicleList(crud.loadList());

        setAdapterVehicles();


        return view;
    }

    private void setAdapterVehicles() {
        recyclerVehiclesAdapter adapter=new recyclerVehiclesAdapter(vehicleList,this);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Vehicle vehicle) {
        this.vehicle=vehicle;
        manager.setLocation(vehicle);
        manager.putToSharedPreferences();
        Toast.makeText(getContext(), "SELECTED : "+vehicle.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

}