package com.example.carlocation.controls.Btn;

import android.view.View;

public class AppStatus implements AppStatusInterface {

    public AppStatus() {
    }

    @Override
    public boolean gotLocation(double longitude, double latitude) {
        if (latitude != 0 && longitude != 0) {
            return true;
        } else return false;
    }

    @Override
    public void hideItemDelay(View view, int time) {
        view.animate().alpha(0.0f).setDuration(time);
        view.setClickable(false);

    }

    @Override
    public void hideItemInstant(View view) {

    }

    @Override
    public void showItemDelay(View view, int time) {

        view.animate().alpha(1.0f).setDuration(time);
        view.setClickable(true);

    }

    @Override
    public void ShowItemInstant(View view) {

    }
}
