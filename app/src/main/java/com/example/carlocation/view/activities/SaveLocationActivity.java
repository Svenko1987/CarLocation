package com.example.carlocation.view.activities;

import static android.content.ContentValues.TAG;
import static com.example.carlocation.MainActivity.MyPREFERENCES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlocation.R;
import com.example.carlocation.controls.logic.GPSControls;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.controls.logic.SharedPreferencesManagerParkEvent;
import com.example.carlocation.controls.logic.SharedPreferencesManagerVehicle;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.ParkEventsList;
import com.example.carlocation.view.fragments.LoadingDialogFragment;
import com.example.carlocation.view.fragments.SaveDialogFragment;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

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
    private double latitude;
    private double longitude;
    private LocationRequest locationRequest;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_location);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        relocate= findViewById(R.id.resetLocationBtn2);
        save= findViewById(R.id.saveLocBtn);
        cancel= findViewById(R.id.cancelB);
        currentLocation=findViewById(R.id.locationET4);
        currentLocation.setFocusable(false);
        note=findViewById(R.id.noteT2);


        ListCRUD<ParkEvent> crud = new ListCRUD<>(SaveLocationActivity.this, "mySavedList.json");
        parkEventsList = new ParkEventsList(crud.loadList());
        manager = new SharedPreferencesManagerParkEvent(sharedPreferences, parkEvent);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        GPSControls gpsControls = new GPSControls(locationRequest, SaveLocationActivity.this);

        Log.d(TAG, "onCreate: sharedPreferences created");
        if (manager.IsEmpty()) {
            Log.d(TAG, "onCreate: Prazan");
            currentLocation.setText("No current location");
        } else {
            Log.d(TAG, "onCreate: Nije prazan");
            parkEvent = manager.getFromSharedPreferences();
            longitude = parkEvent.getLongitude();
            latitude = parkEvent.getLatitude();
            currentLocation.setText(parkEvent.getStreet()+" "+parkEvent.getHouseNumber()+", "+parkEvent.getCity());

        }
        clearHintOnFocus(note,"Add note..");

        relocate.setOnClickListener(view -> {
            Log.d(TAG, "onClick: Clicked on repark");

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }

            if (ActivityCompat.checkSelfPermission(SaveLocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            if (!gpsControls.isGPSEnable()) {
                gpsControls.turnOnGPS();
                return;
            }
            LoadingDialogFragment dialog = new LoadingDialogFragment();
            dialog.show(getSupportFragmentManager(), "LoadingDialogFragment");
            try {
                LocationServices.getFusedLocationProviderClient(SaveLocationActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(SaveLocationActivity.this).removeLocationUpdates(this);
                        dialog.dismiss();
                        if (locationResult.getLocations().size() > 0) {
                            int index = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(index).getLatitude();
                            longitude = locationResult.getLocations().get(index).getLongitude();
                            gpsControls.getAddressToText(latitude, longitude, currentLocation);
                            parkEvent = new ParkEvent(latitude, longitude, SaveLocationActivity.this);
                            parkEventsList.create(parkEvent);
                            crud.updateList(parkEventsList.getMyList());
                            manager.setLocation(parkEvent);
                            manager.putToSharedPreferences();
                        }
                    }
                }, Looper.getMainLooper());

            } catch (Exception e) {
                Log.d(TAG, "Searching location: Cant get location");;
            }


        });
        save.setOnClickListener(view -> {
            if(!isTextViewNotEmpty(note)){
                Toast.makeText(SaveLocationActivity.this, "Please Enter note", Toast.LENGTH_SHORT).show();
                return;
            }
            parkEvent = new ParkEvent(latitude, longitude, SaveLocationActivity.this);
            String te=note.toString();
            note.setFocusable(false);
            parkEvent.setNote(te);
            parkEventsList.create(parkEvent);
            crud.updateList(parkEventsList.getMyList());
            SaveDialogFragment dialog = new SaveDialogFragment();
            dialog.show(getSupportFragmentManager(), "SaveDialogFragment");
            save.setClickable(false);
        });









    }

    public void clearHintOnFocus(final TextView editText, final String hint) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setHint("");
                } else {
                    editText.setHint(hint);
                }
            }
        });
    }
    private boolean isTextViewNotEmpty(TextView textView) {
        return textView != null && textView.getText() != null &&
                !textView.getText().toString().trim().isEmpty();
    }
}