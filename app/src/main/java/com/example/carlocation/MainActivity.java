package com.example.carlocation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.carlocation.controls.Btn.AppStatus;
import com.example.carlocation.inteface.ChronometerControls;
import com.example.carlocation.inteface.HistoryActivity;
import com.example.carlocation.inteface.SavedActivity;
import com.example.carlocation.controls.logic.GPSControls;
import com.example.carlocation.controls.logic.ListCRUD;
import com.example.carlocation.controls.logic.SharedPreferencesManager;
import com.example.carlocation.inteface.ElementsVisibility;
import com.example.carlocation.inteface.ShareData;
import com.example.carlocation.controls.logic.NotificationPublisher;
import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.ParkEventsList;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {


    private Button parkCar, navigate, locate, resetLocation, timer, share, copy, save, history;
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
    private ParkEvent parkEvent;
    private ParkEventsList parkEventsList;


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
        timer = findViewById(R.id.startTimeBtn);
        share = findViewById(R.id.shareBtn);
        copy = findViewById(R.id.copyBtn);
        chronometer = findViewById(R.id.parkTimer);
        locationName = findViewById(R.id.locationET);
        locateL = findViewById(R.id.locateL);
        navigateL = findViewById(R.id.navigateL);
        test = findViewById(R.id.button4);
        loadingL = findViewById(R.id.loadingL);
        save = findViewById(R.id.saveLocationBtn);
        history = findViewById(R.id.historyBtn);
        resetL = findViewById(R.id.resetL);
        progressBar = findViewById(R.id.progressBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("notification-id", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        ElementsVisibility elementsVisibility = new ElementsVisibility(parkCar, navigate, locate, resetLocation, timer, share, copy,save, history, chronometer, navigateL, locateL, loadingL, resetL, locationName, progressBar);
        GPSControls gpsControls = new GPSControls(locationRequest, MainActivity.this);
        ChronometerControls chronometerControls = new ChronometerControls(chronometer);
        AppStatus appStatus = new AppStatus();
        ListCRUD crud = new ListCRUD(MainActivity.this,"myList.json");
        parkEventsList = new ParkEventsList(crud.loadList());
//        Log.d(TAG, "LISTA: " + parkEventsList.toString());
//        ParkEvent testpark = parkEventsList.read(1);
//        Log.d(TAG, "Povuceno iz liste: " + testpark.getDate());

        manager = new SharedPreferencesManager(sharedPreferences, parkEvent);
        Log.d(TAG, "onCreate: sharedPreferences created");
        if (manager.IsEmpty()) {
            Log.d(TAG, "onCreate: Prazan");
        } else {
            Log.d(TAG, "onCreate: Nije prazan");
            parkEvent = manager.getFromSharedPreferences();
            longitude = parkEvent.getLongitude();
            latitude = parkEvent.getLatitude();

            gpsControls.getAddressToText(latitude, longitude, locationName);
            elementsVisibility.hidePark();
            chronometerControls.startChronometerWithTime(parkEvent.getTime());
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

                if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                    Log.d(TAG, "onClick: Wrong version");
                    return;
                }
                if (!(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    Log.d(TAG, "onClick: No permission");
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    elementsVisibility.dontHavaLocation();
                    return;
                }
                if (!gpsControls.isGPSEnable()) {
                    Log.d(TAG, "onClick: GPS turned off");
                    gpsControls.turnOnGPS();
                    return;
                }
                elementsVisibility.hidePark();
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
                            parkEvent = new ParkEvent(latitude, longitude, MainActivity.this);
                            parkEventsList.create(parkEvent);
                            crud.updateList(parkEventsList.getMyList());
                            manager.setLocation(parkEvent);
                            manager.putToSharedPreferences();
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

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }

            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }

            if (!gpsControls.isGPSEnable()) {
                gpsControls.turnOnGPS();
                return;
            }

            elementsVisibility.searchingForLocation();
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
                        parkEvent = new ParkEvent(latitude, longitude, MainActivity.this);
                        parkEventsList.create(parkEvent);
                        crud.updateList(parkEventsList.getMyList());
                        manager.setLocation(parkEvent);
                        manager.putToSharedPreferences();
                        elementsVisibility.gotLocationMode();
                        chronometerControls.startChronometer();
                    }
                }
            }, Looper.getMainLooper());
        });
        history.setOnClickListener(view -> {
            Intent intent= new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
        save.setOnClickListener(view -> {
            Intent intent=new Intent(this, SavedActivity.class);

            startActivity(intent);
        });
        share.setOnClickListener(view -> {
            ShareData shareData = new ShareData(MainActivity.this, clipboardManager, parkEvent);
            Log.d(TAG, "onCreate: Clicked on share");

            Log.d(TAG, "onCreate: " + parkEvent.getLongitude() + " " + parkEvent.getLatitude());
            startActivity(shareData.shareData());
        });
        copy.setOnClickListener(view -> {
            ShareData shareData = new ShareData(MainActivity.this, clipboardManager, parkEvent);
            Log.d(TAG, "onCreate: Clicked on copy");
            shareData.copyDataToClipboard();
            Log.d(TAG, "onCreate: " + parkEvent.getLongitude() + " " + parkEvent.getLatitude());

        });
        timer.setOnClickListener(view -> {
            int style = AlertDialog.THEME_HOLO_DARK;
            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, style, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    NotificationPublisher publisher = new NotificationPublisher();
                    long milliseconds = (hourOfDay * 60 * 60 * 1000) + (minute * 60 * 1000);
                    publisher.scheduleNotification(MainActivity.this, milliseconds);

                    Log.d(TAG, "onTimeSet: " + milliseconds);
                }
            }, 0, 0, true);
            timePickerDialog.setTitle("Select park time");
            timePickerDialog.show();

        });
        test.setOnClickListener(view -> {
            NotificationPublisher publisher = new NotificationPublisher();
            publisher.scheduleNotification(this, 5000);
            Toast.makeText(this, "Notification Scheduled", Toast.LENGTH_SHORT).show();
        });

    }

    private void scheduleNotification(int delay) {
        // Create an Intent for the BroadcastReceiver
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_TITLE, "My Notification");
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_CONTENT, "This is a notification");

        // Create a PendingIntent for the BroadcastReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the current time and add the delay to set the alarm time
        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        // Get an instance of the AlarmManager and schedule the notification
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (latitude != 0.0 && longitude != 0.0) manager.putToSharedPreferences();


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

}