package com.example.carlocation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.model.Vehicle;
import com.example.carlocation.model.VehicleList;

public class VehicleActivity extends AppCompatActivity {
    private Button addVehicle;
    private Button vehicleListBtn;
    private Button backToMain;

    private VehicleList vehicleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        addVehicle = findViewById(R.id.addVehilicleBtn);
        vehicleListBtn = findViewById(R.id.viewVehicleBtn);
        backToMain = findViewById(R.id.backBT);

        ListCRUD<Vehicle> crud= new ListCRUD<>(VehicleActivity.this,"myVehiclesList.json");
        vehicleList= new VehicleList(crud.loadList());




        addVehicle.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, AddVehicle.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();

        });
        vehicleListBtn.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, VehicleListFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();
        });
        backToMain.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}