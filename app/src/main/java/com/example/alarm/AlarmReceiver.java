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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
 private AlarmManager alarmManager;
 private PendingIntent alarmIntent;
 private String CHANNEL_ID = "channelId";
    NotificationManager notificationManager;
    private String currTime = getCurrTimeIn_24Hour();
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
       bedTime = hashMap.get(UserData.WAKEUP_TIME);
       wakeupTime = hashMap.get(UserData.WAKEUP_TIME);


        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, HomeFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        alarmIntent = PendingIntent.getActivity(context,0,i,0);


       String [] wakeTime = wakeupTime.split(":");
       int wakeHour = Integer.parseInt(wakeTime[0]);
       int wakeMin = Integer.parseInt(wakeTime[1]);

        String [] sleepTime = bedTime.split(":");
        int sleepHour = Integer.parseInt(sleepTime[0]);
        int sleepMin = Integer.parseInt(sleepTime[1]);

        String [] currentTime = currTime.split(":");
        int currHour = Integer.parseInt(currentTime[0]);
        int currMin = Integer.parseInt(currentTime[1]);



        Calendar calendar = Calendar.getInstance();


        try {
            Date time1 = new SimpleDateFormat("HH:ss").parse(bedTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            Date time2 = new SimpleDateFormat("HH:ss").parse(wakeupTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);


            Date time3 = new SimpleDateFormat("HH:ss").parse(currTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(time3);
            calendar3.add(Calendar.DATE, 1);
            Date current = calendar3.getTime();

            if(current.before(calendar1.getTime()) && current.before(calendar2.getTime())){
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

             notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }



    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getCurrTimeIn_24Hour() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(calendar.getTime());
    }


}
