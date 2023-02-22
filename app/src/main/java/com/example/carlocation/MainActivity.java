package com.example.carlocation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.carlocation.controls.Btn.AppStatus;
import com.example.carlocation.controls.GPS.GPSControls;
import com.example.carlocation.controls.SharedPreferencesManager;
import com.example.carlocation.model.Location;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {


    private Button parkCar;
    private Button navigate;
    private Button locate;
    private Button resetLocation;
    private TextView navigateL;
    private TextView locateL;
    private TextView loadingL, resetL;
    private TextView locationName;

    private ProgressBar progressBar;

    private double latitude;
    private double longitude;
    private Button test;
    private SharedPreferences sharedPreferences;
    private SharedPreferencesManager manager;

    private LocationRequest locationRequest;
    private Location location;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parkCar = findViewById(R.id.parkBtn);
        navigate = findViewById(R.id.navigateBtn);
        resetLocation = findViewById(R.id.resetLocationBtn);
        locate = findViewById(R.id.getLocationBtn);
        locationName = findViewById(R.id.locationET);

        locateL = findViewById(R.id.locateL);
        navigateL = findViewById(R.id.navigateL);
        test = findViewById(R.id.button4);
        loadingL = findViewById(R.id.loadingL);
        resetL = findViewById(R.id.resetL);
        progressBar = findViewById(R.id.progressBar);

        manager = new SharedPreferencesManager(sharedPreferences, location);
//        try {
//            if(!sharedPreferences.contains("CarLocation"))
//                sharedPreferences = getSharedPreferences("CarLocation", MODE_PRIVATE);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        GPSControls gpsControls = new GPSControls(locationRequest, MainActivity.this);
        AppStatus appStatus = new AppStatus();


        parkCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked on park");
                parkCar.setVisibility(View.GONE);
                appStatus.showItemDelay(progressBar, 0);
                appStatus.showItemDelay(loadingL, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        if (gpsControls.isGPSEnable()) {

                            LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);


                                    LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                                        int index = locationResult.getLocations().size() - 1;
                                        latitude = locationResult.getLocations().get(index).getLatitude();
                                        longitude = locationResult.getLocations().get(index).getLongitude();
                                        gpsControls.getAddressToText(latitude, longitude, locationName);
                                        location = new Location(latitude, longitude, MainActivity.this);
                                        appStatus.hideItemDelay(progressBar, 0);
                                        appStatus.hideItemDelay(loadingL, 0);
                                        appStatus.showItemDelay(resetL, 300);
                                        appStatus.showItemDelay(resetLocation, 300);
                                        resetLocation.setEnabled(true);
                                        appStatus.showItemDelay(locationName, 300);
                                        appStatus.showItemDelay(navigate, 300);
                                        navigate.setEnabled(true);
                                        appStatus.showItemDelay(navigateL, 300);
                                        appStatus.showItemDelay(locate, 300);
                                        locate.setEnabled(true);
                                        appStatus.showItemDelay(locateL, 300);


                                    }
                                }
                            }, Looper.getMainLooper());

                        } else {
                            gpsControls.turnOnGPS();
                        }
                    } else {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
            }
        });

        locate.setOnClickListener(view -> {
            if (appStatus.gotLocation(latitude, longitude)) {
                Log.d(TAG, "CLICKED " + longitude + " : " + latitude);
                String labelLocation = "Parked location";
                Uri gmmIntentUri = Uri.parse("geo:<" + latitude + ">,<" + longitude + ">?q=<" + latitude + ">,<" + longitude + ">(" + labelLocation + ")");
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        navigate.setOnClickListener(view -> {
            if (appStatus.gotLocation(latitude, longitude)) {
                Log.d(TAG, "CLICKED " + longitude + " : " + latitude);
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=w");
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        resetLocation.setOnClickListener(view -> {

            Log.d(TAG, "onClick: Clicked on repark");
            parkCar.setVisibility(View.GONE);
            appStatus.hideItemDelay(resetLocation, 0);
            appStatus.hideItemDelay(navigate, 0);
            appStatus.hideItemDelay(navigateL, 0);
            appStatus.hideItemDelay(resetL, 0);
            appStatus.hideItemDelay(locate, 0);
            appStatus.hideItemDelay(locateL, 0);
            appStatus.hideItemDelay(locationName, 0);


            appStatus.showItemDelay(progressBar, 0);
            appStatus.showItemDelay(loadingL, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    if (gpsControls.isGPSEnable()) {

                        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);


                                LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                                if (locationResult != null && locationResult.getLocations().size() > 0) {
                                    int index = locationResult.getLocations().size() - 1;
                                    latitude = locationResult.getLocations().get(index).getLatitude();
                                    longitude = locationResult.getLocations().get(index).getLongitude();
                                    gpsControls.getAddressToText(latitude, longitude, locationName);
                                    location = new Location(latitude, longitude, MainActivity.this);
                                    appStatus.hideItemDelay(progressBar, 0);
                                    appStatus.hideItemDelay(loadingL, 0);
                                    appStatus.showItemDelay(resetL, 300);
                                    appStatus.showItemDelay(resetLocation, 300);
                                    resetLocation.setEnabled(true);
                                    appStatus.showItemDelay(locationName, 300);
                                    appStatus.showItemDelay(navigate, 300);
                                    navigate.setEnabled(true);
                                    appStatus.showItemDelay(navigateL, 300);
                                    appStatus.showItemDelay(locate, 300);
                                    locate.setEnabled(true);
                                    appStatus.showItemDelay(locateL, 300);


                                }
                            }
                        }, Looper.getMainLooper());

                    } else {
                        gpsControls.turnOnGPS();
                    }
                } else {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }


        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (latitude != 0.0 && longitude != 0.0)
            manager.putToSharedPreferences();


    }

//    private void turnOnGPS() {
//
//
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
//                .checkLocationSettings(builder.build());
//
//        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
//            @Override
//            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
//
//                try {
//                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();
//
//                } catch (ApiException e) {
//
//                    switch (e.getStatusCode()) {
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//
//                            try {
//                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
//                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
//                            } catch (IntentSender.SendIntentException ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            //Device does not have location
//                            break;
//                    }
//                }
//            }
//        });
//    }

//    private boolean isGPSEnable() {
//        LocationManager locationManager = null;
//        boolean isEnabled = false;
//
//        if (locationManager == null) {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        }
//
//        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        return isEnabled;
//
//    }
}