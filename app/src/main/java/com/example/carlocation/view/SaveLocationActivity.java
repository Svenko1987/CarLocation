package com.example.carlocation.view;

import static android.content.ContentValues.TAG;
import static com.example.carlocation.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.carlocation.MainActivity;
import com.example.carlocation.R;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.controls.logic.SharedPreferencesManagerParkEvent;
import com.example.carlocation.controls.logic.SharedPreferencesManagerVehicle;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.ParkEventsList;

public class SaveLocationActivity extends AppCompatActivity {

    private Button relocate;
    private Button save;
    private Button camera;
    private Button cancel;

    private TextView currentLocation;
    private TextView note;

    private SharedPreferences sharedPreferences;
    private SharedPreferencesManagerParkEvent manager;
    private SharedPreferencesManagerVehicle managerVehicle;
    private ParkEvent parkEvent;
    private ParkEventsList parkEventsList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        relocate= findViewById(R.id.resetLocationBtn2);
        save= findViewById(R.id.saveLocBtn);
        cancel= findViewById(R.id.cancelB);
        currentLocation=findViewById(R.id.locationET4);
        note=findViewById(R.id.noteT2);


        ListCRUD<ParkEvent> crud = new ListCRUD<>(SaveLocationActivity.this, "mySavedList.json");
        parkEventsList = new ParkEventsList(crud.loadList());

        manager = new SharedPreferencesManagerParkEvent(sharedPreferences, parkEvent);
        Log.d(TAG, "onCreate: sharedPreferences created");
        if (manager.IsEmpty()) {
            Log.d(TAG, "onCreate: Prazan");
            currentLocation.setText("No current location");
        } else {
            Log.d(TAG, "onCreate: Nije prazan");
            parkEvent = manager.getFromSharedPreferences();

        }

        relocate.setOnClickListener(view -> {


        });
        save.setOnClickListener(view -> {
            SaveDialogFragment dialog = new SaveDialogFragment();
            dialog.show(getSupportFragmentManager(), "SaveDialogFragment");
        });









    }
}