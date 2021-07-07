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

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.reminderapp.R;
import com.example.reminderapp.tabFragments.HomeFragment;
import com.example.userSession.UserData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
 private MediaPlayer mediaPlayer ;
 private AlarmManager alarmManager;
 private PendingIntent alarmIntent;
 private String CHANNEL_ID = "channelId";
    NotificationManager notificationManager;
    private String currTime = getCurrentTime();
    private String bedTime, wakeupTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        UserData userData = new UserData(context);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = userData.getUserData();
       bedTime = hashMap.get(UserData.WAKEUP_TIME);
       wakeupTime = hashMap.get(UserData.WAKEUP_TIME);


        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        alarmIntent = PendingIntent.getActivity(context,0,i,0);

        // TYPE OF ALARM
       String [] wakeTime = wakeupTime.split(":");
       int wakeHour = Integer.parseInt(wakeTime[0]);
       int wakeMin = Integer.parseInt(wakeTime[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,wakeHour);
        calendar.set(Calendar.MINUTE,wakeMin);
        // setRepeating() lets you specify a precise custom interval--in this case,
       // 90 minutes.
        // condition
        // here it will work when he wake up and every 90 min
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 90, alarmIntent);



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
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(alarmIntent);

             notificationManager.notify(1,builder.build());

        //NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
       // notificationManagerCompat.notify(1,builder.build());

    }

    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}
