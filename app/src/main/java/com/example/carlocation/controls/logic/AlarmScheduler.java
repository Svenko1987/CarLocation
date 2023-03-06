package com.example.carlocation.controls.logic;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;


public class AlarmScheduler {

    Context context;

    public AlarmScheduler(Context context) {
        this.context = context;
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
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("notification-id", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel for notification");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
