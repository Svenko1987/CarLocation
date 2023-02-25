package com.example.carlocation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.SharedElementCallback;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.carlocation.controls.Btn.AppStatus;
import com.example.carlocation.controls.GPS.GPSControls;
import com.example.carlocation.controls.SharedPreferencesManager;
import com.example.carlocation.controls.inteface.ElementsVisibility;
import com.example.carlocation.controls.inteface.ShareData;
import com.example.carlocation.model.Location;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {


    private Button parkCar, navigate, locate, resetLocation,timer, share, copy;
    private TextView navigateL, locateL, loadingL, resetL, locationName;
    private Chronometer chronometer;

    private ProgressBar progressBar;

    private double latitude;
    private double longitude;
    private Button test;
    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "CarLocation";
    public static final String value = "key";
    ClipboardManager clipboardManager;
    private SharedPreferencesManager manager;

    private LocationRequest locationRequest;
    private Location location;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: sharedPreferences created");

        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        parkCar = findViewById(R.id.parkBtn);
        navigate = findViewById(R.id.navigateBtn);
        resetLocation = findViewById(R.id.resetLocationBtn);
        locate = findViewById(R.id.getLocationBtn);
        timer=findViewById(R.id.startTimeBtn);
        share = findViewById(R.id.shareBtn);
        copy = findViewById(R.id.copyBtn);
        chronometer=findViewById(R.id.parkTimer);
        locationName = findViewById(R.id.locationET);
        locateL = findViewById(R.id.locateL);
        navigateL = findViewById(R.id.navigateL);
        test = findViewById(R.id.button4);
        loadingL = findViewById(R.id.loadingL);
        resetL = findViewById(R.id.resetL);
        progressBar = findViewById(R.id.progressBar);

        ElementsVisibility elementsVisibility = new ElementsVisibility(parkCar, navigate, locate, resetLocation,timer, share, copy,chronometer, navigateL, locateL, loadingL, resetL, locationName, progressBar);
        GPSControls gpsControls = new GPSControls(locationRequest, MainActivity.this);
        AppStatus appStatus = new AppStatus();

        manager = new SharedPreferencesManager(sharedPreferences, location);
        Log.d(TAG, "onCreate: sharedPreferences created");
        if (manager.IsEmpty()) {
            Log.d(TAG, "onCreate: Prazan");
        } else {
            Log.d(TAG, "onCreate: Nije prazan");
            location = manager.getFromSharedPreferences();
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            gpsControls.getAddressToText(latitude, longitude, locationName);
            elementsVisibility.hidePark();
            elementsVisibility.gotLocationMode();
        }
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);


        parkCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Clicked on park");
                elementsVisibility.hidePark();
                if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                    Log.d(TAG, "onClick: Wrong version");
                    return;
                }
                if (!(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    Log.d(TAG, "onClick: No permission");
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }
                if (!gpsControls.isGPSEnable()) {
                    Log.d(TAG, "onClick: GPS turned off");
                    gpsControls.turnOnGPS();
                    return;
                }
                LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                        if (locationResult.getLocations().size() > 0) {
                            int index = locationResult.getLocations().size() - 1;
                            latitude = locationResult.getLocations().get(index).getLatitude();
                            longitude = locationResult.getLocations().get(index).getLongitude();
                            gpsControls.getAddressToText(latitude, longitude, locationName);
                            location = new Location(latitude, longitude, MainActivity.this);
                            manager.setLocation(location);
                            elementsVisibility.gotLocationMode();
                        }
                    }
                }, Looper.getMainLooper());
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


            elementsVisibility.searchingForLocation();
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
                                    manager.setLocation(location);
                                    elementsVisibility.gotLocationMode();


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
        share.setOnClickListener(view -> {
            ShareData shareData = new ShareData(MainActivity.this, clipboardManager, location);
            Log.d(TAG, "onCreate: Clicked on share");

            Log.d(TAG, "onCreate: " + location.getLongitude() + " " + location.getLatitude());
            startActivity(shareData.shareData());
        });
        copy.setOnClickListener(view -> {
            ShareData shareData = new ShareData(MainActivity.this, clipboardManager, location);
            Log.d(TAG, "onCreate: Clicked on copy");
            shareData.copyDataToClipboard();
            Log.d(TAG, "onCreate: " + location.getLongitude() + " " + location.getLatitude());

        });
        timer.setOnClickListener(view -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
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