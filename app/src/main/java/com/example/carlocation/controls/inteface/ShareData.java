package com.example.carlocation.controls.inteface;

import static android.content.ContentValues.TAG;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.carlocation.model.ParkEvent;

public class ShareData {

    Context context;
    ClipboardManager clipboardManager;
    ParkEvent parkEvent;

    public ShareData(Context context, ClipboardManager clipboardManager, ParkEvent parkEvent) {
        this.context = context;
        this.clipboardManager = clipboardManager;
        this.parkEvent = parkEvent;
    }

    public Intent shareData() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "https://maps.google.com/?q=" + parkEvent.getLatitude() + "," + parkEvent.getLongitude();
        Log.d(TAG, "shareData: "+shareBody);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        Log.d(TAG, "shareData: Shared");

        return intent;

    }

    public void copyDataToClipboard() {
        if (parkEvent.getLongitude() == 0.0 && parkEvent.getLatitude() == 0.0) {
            Toast.makeText(context, "No location", Toast.LENGTH_SHORT).show();
            return;
        }
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", "https://maps.google.com/?q=" + parkEvent.getLatitude() + "," + parkEvent.getLongitude());
        
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();


    }


}
