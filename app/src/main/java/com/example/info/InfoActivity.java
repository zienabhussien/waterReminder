package com.example.info;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.text.format.DateFormat;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarm.AlarmReceiver;
import com.example.reminderapp.MainActivity;
import com.example.reminderapp.R;
import com.example.reminderapp.databinding.ActivityInfoBinding;
import com.example.reminderapp.tabFragments.HomeFragment;
import com.example.userSession.UserData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.example.reminderapp.R.color.purple_200;
import static com.example.reminderapp.R.color.purple_700;

public class InfoActivity extends AppCompatActivity {
 ActivityInfoBinding binding;
 UserData mUserData;
 AlarmManager alarmManager;
 Intent myIntent;

 int weight = 0;
 String bedTime;
 String wakeupTime;
 String gender;
 int bedTimeHour, bedTimeMin;
 int wakeupTimeHour, wakeupTimeMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mUserData = new UserData(getApplicationContext());

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);


          getGender();
          getWeight();
            cancelAlarm();


        binding.wakeupTimeTv.setOnClickListener(v ->{
                  setWakeupTime();
          });
          //we can get wakeup time from text
          wakeupTime = binding.wakeupTimeTv.getText().toString();

        binding.bedTimeTv.setOnClickListener(v ->{
            setBedTime();
        });
        //we can get bed time from text
        bedTime = binding.bedTimeTv.getText().toString();

        binding.startBtn.setOnClickListener(v ->{
            // save data to shared preferences
           mUserData.saveData( gender,weight+"", bedTime, wakeupTime,true);
            startActivity(new Intent(InfoActivity.this, MainActivity.class));
            finish();
        });




    }

    private void getGender() {
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        RadioButton genderBtn = findViewById(selectedId);
        if (selectedId == -1) {
            Toast.makeText(InfoActivity.this,"Please set your gender",Toast.LENGTH_LONG).show();
        }else{
            gender = genderBtn.getText().toString();
        }
    }

    private void getWeight() {
        binding.numPickerWeight.setMaxValue(400);
        binding.numPickerWeight.setMinValue(1);
        binding.numPickerWeight.setValue(60);
        binding.numPickerWeight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                weight = newVal;
            }
        });
    }

    private void setBedTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(InfoActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    bedTimeHour = hourOfDay;
                    bedTimeMin = minute;

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,bedTimeHour,bedTimeMin);
                        binding.bedTimeTv.setText(DateFormat.format("hh:mm aa",calendar));
                    }
                },12,0,false);
        timePickerDialog.updateTime(bedTimeHour,bedTimeMin);
        timePickerDialog.show();

      //  setAlarm();
    }

    private void setWakeupTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(InfoActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        wakeupTimeHour = hourOfDay;
                        wakeupTimeMin = minute;

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,wakeupTimeHour,wakeupTimeMin);
                        binding.wakeupTimeTv.setText(DateFormat.format("hh:mm aa",calendar));
                    }
                },12,0,false);

           timePickerDialog.updateTime(wakeupTimeHour,wakeupTimeMin);
           timePickerDialog.show();

          // setAlarm();
    }

    private void cancelAlarm(){
      PendingIntent  pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm(){
        String currTime = getCurrentTime();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = mUserData.getUserData();
      String  bedTime1 = hashMap.get(UserData.WAKEUP_TIME);
       String wakeupTime1 = hashMap.get(UserData.WAKEUP_TIME);


       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      PendingIntent  alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),1,myIntent,0);


        String [] wakeTime = wakeupTime1.split(":");
        int wakeHour = Integer.parseInt(wakeTime[0]);
        int wakeMin = Integer.parseInt(wakeTime[1]);
        String wakeX = wakeupTime1.substring(wakeupTime1.length()-2);

        String [] sleepTime = bedTime1.split(":");
        int sleepHour = Integer.parseInt(sleepTime[0]);
        int sleepMin = Integer.parseInt(sleepTime[1]);
        String sleepX = bedTime1.substring(bedTime1.length()-2);

        String [] currentTime = currTime.split(":");
        int currHour = Integer.parseInt(currentTime[0]);
        int currMin = Integer.parseInt(currentTime[1]);
        String currX = currTime.substring(currTime.length()-2);

        if(wakeX.equals("pm")){
            wakeHour +=12;
        }
        if(sleepX.equals("pm")){
            sleepHour +=12;
        }
        if(currX.equals("pm")){
            currHour +=12;
        }


        // TYPE OF ALARM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,wakeHour);
        calendar.set(Calendar.MINUTE,wakeMin);

        // here it will work when he wake up and every 90 min
        if( currHour >= wakeHour  &&  currHour< sleepHour ) {
            if(currMin>=wakeMin && currMin<sleepMin)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        1000 * 60 * 90, alarmIntent);
        }

    }

    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}