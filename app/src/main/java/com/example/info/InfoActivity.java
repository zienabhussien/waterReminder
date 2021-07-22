package com.example.info;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarm.AlarmReceiver;
import com.example.reminderapp.main.MainActivity;
import com.example.reminderapp.databinding.ActivityInfoBinding;
import com.example.reminderapp.room.Util;
import com.example.userSession.UserData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InfoActivity extends AppCompatActivity {
 ActivityInfoBinding binding;
 UserData mUserData;
 AlarmManager alarmManager;
 Intent myIntent;

 int weight = 60;
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

         // getGender();
          getWeight();

          cancelAlarm();


        binding.wakeupTimeTv.setOnClickListener(v ->{
                  setWakeupTime();
          });

        binding.bedTimeTv.setOnClickListener(v ->{
            setBedTime();
        });

        binding.startBtn.setOnClickListener(v ->{
            // save data to shared preferences
            wakeupTime = binding.wakeupTimeTv.getText().toString();
            bedTime = binding.bedTimeTv.getText().toString();
            getGender();
           mUserData.saveData( gender,weight+"", bedTime, wakeupTime,true);
            //Toast.makeText(getApplicationContext(),"wakeupTime  ="+ wakeupTime +", bedTime ="+bedTime, Toast.LENGTH_SHORT).show();
            setAlarm();
            startActivity(new Intent(InfoActivity.this, MainActivity.class));
            finish();
        });


    }

    private void getGender() {
        int selectedId = binding.radioGroup.getCheckedRadioButtonId();
        RadioButton genderBtn = findViewById(selectedId);
      if(selectedId == -1){
          Toast.makeText(getApplicationContext(),"Please set your gender",Toast.LENGTH_SHORT).show();
      }else{
          gender = genderBtn.getText().toString();
          Toast.makeText(getApplicationContext(),genderBtn.getText().toString(),Toast.LENGTH_SHORT).show();

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
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                bedTimeHour = hourOfDay;
                bedTimeMin = minute;
                binding.bedTimeTv.setText(String.format(Locale.getDefault(),
                        "%02d:%02d",bedTimeHour,bedTimeMin));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,
                bedTimeHour,bedTimeMin,true);
        timePickerDialog.show();

    }

    private void setWakeupTime(){
     TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
         @Override
         public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            wakeupTimeHour = hourOfDay;
            wakeupTimeMin = minute;
            binding.wakeupTimeTv.setText(String.format(Locale.getDefault(),
                    "%02d:%02d",wakeupTimeHour,wakeupTimeMin));

         }
     };
    TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,
            wakeupTimeHour,wakeupTimeMin,true);
          timePickerDialog.show();
    }

    private void cancelAlarm(){
      PendingIntent  pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 1, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm(){
       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      PendingIntent  alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),1,myIntent,0);

        // TYPE OF ALARM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,wakeupTimeHour);
        calendar.set(Calendar.MINUTE,wakeupTimeMin);

        // here it will work when he wake up and every 90 min
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        1000 * 60 * 90, alarmIntent);

    }


  }