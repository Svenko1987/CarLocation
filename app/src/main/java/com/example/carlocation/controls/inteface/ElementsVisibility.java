package com.example.carlocation.controls.inteface;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.carlocation.controls.Btn.AppStatus;

public class ElementsVisibility {

    private Button parkCar, navigate, locate, resetLocation;
    private TextView navigateL, locateL, loadingL, resetL, locationName;
    private ProgressBar progressBar;
    private AppStatus appStatus;

    public ElementsVisibility(Button parkCar, Button navigate, Button locate, Button resetLocation, TextView navigateL, TextView locateL, TextView loadingL, TextView resetL, TextView locationName, ProgressBar progressBar) {
        this.parkCar = parkCar;
        this.navigate = navigate;
        this.locate = locate;
        this.resetLocation = resetLocation;
        this.navigateL = navigateL;
        this.locateL = locateL;
        this.loadingL = loadingL;
        this.resetL = resetL;
        this.locationName = locationName;
        this.progressBar = progressBar;
        this.appStatus = new AppStatus();
    }

    public void gotLocationMode() {

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

    public void dontHavaLocation() {

        appStatus.hideItemDelay(resetLocation, 0);
        appStatus.hideItemDelay(navigate, 0);
        appStatus.hideItemDelay(navigateL, 0);
        appStatus.hideItemDelay(resetL, 0);
        appStatus.hideItemDelay(locate, 0);
        appStatus.hideItemDelay(locateL, 0);
        appStatus.hideItemDelay(locationName, 0);



    }

    public void searchingForLocation() {
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

    }
    public void hidePark(){
        parkCar.setVisibility(View.GONE);
        appStatus.showItemDelay(progressBar, 0);
        appStatus.showItemDelay(loadingL, 0);
    }

}
