package com.example.plantor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReciever1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
           NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
           int notificationId = 1;
           String channelId = "channel-01";
           String channelName = "Channel Name";
           int importance = NotificationManager.IMPORTANCE_HIGH;

           Intent repeating_intent = new Intent(context, MainActivity.class);
           repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
               NotificationChannel mChannel = new NotificationChannel(
                       channelId, channelName, importance);
               notificationManager.createNotificationChannel(mChannel);
           }

           NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                   .setSmallIcon(android.R.drawable.arrow_up_float)
                   .setContentTitle("Your Plant")
                   .setContentText("Check fertilizer!")
                   .setAutoCancel(true);

           TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
           stackBuilder.addNextIntent(repeating_intent);
           PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
           mBuilder.setContentIntent(resultPendingIntent);

           notificationManager.notify(notificationId, mBuilder.build());
       }else{
           NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
           int notificationId = 1;
           String channelId = "channel-01";
           String channelName = "Channel Name";
           int importance = NotificationManager.IMPORTANCE_HIGH;

           Intent repeating_intent = new Intent(context, MainActivity.class);
           repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
               NotificationChannel mChannel = new NotificationChannel(
                       channelId, channelName, importance);
               notificationManager.createNotificationChannel(mChannel);
           }

           NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                   .setSmallIcon(android.R.drawable.arrow_up_float)
                   .setContentTitle("Your Plant")
                   .setContentText("Check fertilizer!")
                   .setAutoCancel(true);

           TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
           stackBuilder.addNextIntent(repeating_intent);
           PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
           mBuilder.setContentIntent(resultPendingIntent);

           notificationManager.notify(notificationId, mBuilder.build());
       }

    }
}
