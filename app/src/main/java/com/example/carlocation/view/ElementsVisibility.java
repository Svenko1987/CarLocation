package com.example.carlocation.view;

import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.carlocation.controls.Btn.AppStatus;

/*
Class is setting element visibility depending the current state of application.
*/

public class ElementsVisibility {

    private Button parkCar, navigate, locate, resetLocation, share, copy,timer,save,history;
    private TextView navigateL, locateL, loadingL, resetL, locationName;
    private Chronometer chronometer;
    private ProgressBar progressBar;
    private AppStatus appStatus;

    public ElementsVisibility(Button parkCar, Button navigate, Button locate, Button resetLocation,Button timer, Button share, Button copy,Button save, Button history, Chronometer chronometer, TextView navigateL, TextView locateL, TextView loadingL, TextView resetL, TextView locationName, ProgressBar progressBar) {
        this.parkCar = parkCar;
        this.navigate = navigate;
        this.locate = locate;
        this.resetLocation = resetLocation;
        this.share = share;
        this.copy = copy;
        this.save=save;
        this.history=history;
        this.chronometer=chronometer;
        this.navigateL = navigateL;
        this.locateL = locateL;
        this.loadingL = loadingL;
        this.resetL = resetL;
        this.locationName = locationName;
        this.progressBar = progressBar;
        this.timer=timer;
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
        appStatus.showItemDelay(timer, 300);
        timer.setEnabled(true);
        appStatus.showItemDelay(chronometer, 300);
        appStatus.showItemDelay(navigateL, 300);
        appStatus.showItemDelay(locate, 300);
        locate.setEnabled(true);
        appStatus.showItemDelay(share, 300);
        locate.setEnabled(true);
        appStatus.showItemDelay(copy, 300);
        locate.setEnabled(true);
        appStatus.showItemDelay(locateL, 300);
        appStatus.showItemDelay(save,300);
        save.setEnabled(true);
        appStatus.showItemDelay(history,300);
        history.setEnabled(true);
    }

    public void dontHavaLocation() {

        appStatus.hideItemDelay(resetLocation, 0);
        appStatus.hideItemDelay(navigate, 0);
        appStatus.hideItemDelay(navigateL, 0);
        appStatus.hideItemDelay(timer, 0);
        appStatus.hideItemDelay(share, 0);
        appStatus.hideItemDelay(copy, 0);
        appStatus.hideItemDelay(chronometer, 0);
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
        appStatus.hideItemDelay(timer, 0);
        appStatus.hideItemDelay(chronometer, 0);
        appStatus.hideItemDelay(share, 0);
        appStatus.hideItemDelay(copy, 0);
        appStatus.hideItemDelay(save,0);
        appStatus.hideItemDelay(history,0);
        appStatus.hideItemDelay(locationName, 0);
        appStatus.showItemDelay(progressBar, 0);
        appStatus.showItemDelay(loadingL, 0);

    }

    public void hidePark() {
        parkCar.setVisibility(View.GONE);
        appStatus.showItemDelay(progressBar, 0);
        appStatus.showItemDelay(loadingL, 0);
    }

}
