package com.example.reminderapp.tabFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.AlarmAdapter;
import com.example.adapter.DayLog;
import com.example.reminderapp.main.ChooseCupFragment;
import com.example.reminderapp.databinding.FragmentHomeBinding;
import com.example.reminderapp.room.RoomDB;
import com.example.reminderapp.room.Util;
import com.example.userSession.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    FragmentHomeBinding binding;
    AlarmAdapter alarmAdapter;
    UserData userData;
    DayLog dataDayLog;
    RoomDB database;

    private int userWeight;
    private String userGender;
    private float percent = 0;
    private float progressStatus = 0;
    private int cupSize;  // taken from selection of any cup
    private float drinkGoal;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private DayLog todayLog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        dataDayLog = new DayLog();

        // initialize database
        database = RoomDB.getInstance(getActivity());


        // get stored user data
        userData = new UserData(getActivity());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = userData.getUserData();
        userWeight = Integer.parseInt(hashMap.get(UserData.WEIGHT_KEY));
        userGender = hashMap.get(UserData.GENDER_KEY);


        binding.currentTime.setText(Util.getCurrentTime());
        binding.addCupBtn.setOnClickListener(v -> {
            updateProgress(percent);
            addItem();
        });

        cupSize = userData.getCupSize();

        drinkGoal = "Male".equals(userGender) ? (userWeight * (35)) : (userWeight * (31));
        // drinkGoal = 2400;
       percent = (cupSize / drinkGoal)*100;

        // choose cup from the dialog
        binding.chooseCup.setOnClickListener(v -> {
            ChooseCupFragment fragmentDialog = new ChooseCupFragment();
            fragmentDialog.show(getActivity().getSupportFragmentManager(), "Choose cup size");

        });


        Log.d(TAG, "onCreateView: " + database.itemDao().getAll());
        // get stored data from database
        todayLog = database.itemDao().getDayLog(Util.getCurrentDate());
        if (todayLog == null) todayLog = new DayLog();
        int sum = 0;
        for (Pair<Long, Integer> cup : todayLog.getCups()) {
            sum += cup.second;
        }
       // progressStatus = (sum / drinkGoal)*100;
       updateProgress( (sum / drinkGoal)*100);
        alarmAdapter = new AlarmAdapter(todayLog.getCups(), getActivity());
        binding.recyclerView.setAdapter(alarmAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return binding.getRoot();
    }

    private void addItem() {
        cupSize = userData.getCupSize();
        todayLog.getCups().add(new Pair<>(System.currentTimeMillis(), cupSize));
        database.itemDao().insert(todayLog);
        alarmAdapter.notifyDataSetChanged();
    }

    private void updateProgress(float percent) {
        if (progressStatus < 100) {
            progressStatus += percent;    // progress status will increase by percentage
            userData.saveProgressbar_status((int) progressStatus);
            handler.post(() -> binding.progressBar.setProgress(userData.getProgress_status()));

        }
    }


}

