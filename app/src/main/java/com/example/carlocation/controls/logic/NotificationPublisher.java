package com.example.carlocation.controls.logic;


import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.carlocation.R;

public class NotificationPublisher extends BroadcastReceiver {
    public static final String NOTIFICATION_ID = "notification-id";
    public static final String NOTIFICATION_TITLE = "CarLocation";
    public static final String NOTIFICATION_CONTENT = "Your parking time is up";


    public NotificationPublisher() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_ID)
                .setSmallIcon(R.drawable.car)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_CONTENT)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "No Permission to set alarm!", Toast.LENGTH_SHORT).show();
            return;
        }
        notificationManager.notify(1, builder.build());


    }

    public void scheduleNotification(Context context, int delay) {
        // Create an Intent for the BroadcastReceiver
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_TITLE, "My Notification");
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_CONTENT, "This is a notification");

        // Create a PendingIntent for the BroadcastReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the current time and add the delay to set the alarm time
        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        // Get an instance of the AlarmManager and schedule the notification
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
}
