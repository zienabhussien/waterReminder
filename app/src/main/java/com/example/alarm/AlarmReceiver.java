package com.example.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.reminderapp.R;

public class AlarmReceiver extends BroadcastReceiver {
 private MediaPlayer mediaPlayer ;
 private AlarmManager alarmManager;
 private PendingIntent alarmIntent;
 private String CHANNEL_ID = "channelId";
    NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        alarmIntent = PendingIntent.getBroadcast(context,0,i,0);

        // TYPE OF ALRAM
         alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                 SystemClock.elapsedRealtime()+ AlarmManager.INTERVAL_HOUR,
                 AlarmManager.INTERVAL_HOUR,alarmIntent);

        // setRepeating() lets you specify a precise custom interval--in this case,
       // 90 minutes.
       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 90, alarmIntent);

        // replace the  calendar.getTimeInMillis()  with ....


        // Create notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "water reminder channel";
            String description = "Channel for water reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

             notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Drink water reminder")
                .setContentText("It's time to drink ")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

             notificationManager.notify(1,builder.build());

        //NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
       // notificationManagerCompat.notify(1,builder.build());


    }
}
