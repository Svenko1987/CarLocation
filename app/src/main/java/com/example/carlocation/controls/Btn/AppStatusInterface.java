package com.example.carlocation.controls.Btn;

import android.view.View;

public interface AppStatusInterface {

    boolean gotLocation(double longitude, double latitude);
    void hideItemDelay(View view, int time);
    void hideItemInstant(View view);
    void showItemDelay(View view, int time);
    void ShowItemInstant(View view);

}
