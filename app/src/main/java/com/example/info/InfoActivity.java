package com.example.info;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.text.format.DateFormat;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminderapp.MainActivity;
import com.example.reminderapp.R;
import com.example.reminderapp.databinding.ActivityInfoBinding;
import com.example.userSession.UserData;

import java.util.Calendar;

import static com.example.reminderapp.R.color.purple_200;
import static com.example.reminderapp.R.color.purple_700;

public class InfoActivity extends AppCompatActivity {
 ActivityInfoBinding binding;
 UserData mUserData;
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

          getGender();
          //  store gender
         getWeight();
        //  store weight

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
    }
}