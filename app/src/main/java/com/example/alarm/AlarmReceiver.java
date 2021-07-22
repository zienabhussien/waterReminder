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
import com.example.reminderapp.room.Util;
import com.example.reminderapp.tabFragments.HomeFragment;
import com.example.userSession.UserData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
 private PendingIntent alarmIntent;
 private String CHANNEL_ID = "channelId";
    NotificationManager notificationManager;
    private String currTime = Util.getCurrTimeIn_24Hour();
    private String bedTime, wakeupTime;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Drink water reminder")
                .setContentText("It's time to drink ")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(alarmIntent);



        UserData userData = new UserData(context);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = userData.getUserData();
       bedTime = hashMap.get(UserData.BED_TIME);
       wakeupTime = hashMap.get(UserData.WAKEUP_TIME);


        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(context, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        alarmIntent = PendingIntent.getActivity(context,0,i,0);




        try {
            Date time1 = new SimpleDateFormat("HH:mm",Locale.getDefault()).parse(bedTime);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);


            Date time2 = new SimpleDateFormat("HH:mm",Locale.getDefault()).parse(wakeupTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);


            Date time3 = new SimpleDateFormat("HH:mm",Locale.getDefault()).parse(currTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(time3);

            Date current = calendar3.getTime();

            // to be in the same day
            if(calendar1.before(calendar2)) calendar1.add(Calendar.DATE,1);


            if(current.before(calendar1.getTime()) && current.after(calendar2.getTime())){
                notificationManager.notify(1,builder.build());

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }




        // Create notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "water reminder channel";
            String description = "Channel for water reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }


}
