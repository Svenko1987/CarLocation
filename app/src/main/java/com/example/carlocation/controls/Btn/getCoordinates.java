package com.example.carlocation.controls.Btn;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.carlocation.MainActivity;
import com.example.carlocation.controls.GPS.GPSControls;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class getCoordinates implements View.OnClickListener {

    private TextView textView;
    private GPSControls gpsControls;
    private Context context;
    private LocationRequest locationRequest;



    @Override
    public void onClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (gpsControls.isGPSEnable()) {

                    LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);

                            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(this);
                            if(locationResult!= null && locationResult.getLocations().size() >0 ){
                                int index = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                double longitude = locationResult.getLocations().get(index).getLongitude();

                                textView.setText(latitude + ":"+ longitude);
                            }
                        }
                    }, Looper.getMainLooper());

                } else {
                    gpsControls.turnOnGPS();
                }
            } else {

                //requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
}
