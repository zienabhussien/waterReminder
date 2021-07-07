package com.example.reminderapp.tabFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.adapter.AlarmAdapter;
import com.example.adapter.Item;
import com.example.reminderapp.R;
import com.example.reminderapp.databinding.FragmentHomeBinding;
import com.example.userSession.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static java.lang.String.format;


public class HomeFragment extends Fragment {
 FragmentHomeBinding binding;
 AlarmAdapter alarmAdapter;
 UserData userData;
 Item dataItem;
 ArrayList<Item> arrayList;
    private int userWeight;
    private String userGender;
    private int percent = 0;
    private int progressStatus = 0;
    private int cupSize = 120;  // taken from selection of any cup
    private long drinkGoal ;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        dataItem = new Item();
        arrayList = new ArrayList<>();

        // get stored user data
        userData = new UserData(getActivity());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap = userData.getUserData();
        userWeight =Integer.parseInt( hashMap.get(UserData.WEIGHT_KEY)) ;
        userGender = hashMap.get(UserData.GENDER_KEY);
        if(userGender.equals("Male")){
            drinkGoal = (long) (userWeight*(0.35));
        }else{
            drinkGoal = (long) (userWeight*(0.31));
        }

        percent = (int)(cupSize/drinkGoal)*100;


        binding.currentTime.setText(getCurrentTime());
        binding.addCupBtn.setOnClickListener(v -> {
            updateProgress();
            addItem();
        } );






        return binding.getRoot();
    }

    private void addItem() {
        dataItem.setCurrentTime(getCurrentTime());
        // add cupSize
       arrayList.add(dataItem);
       alarmAdapter = new AlarmAdapter(arrayList,getActivity());
       binding.recyclerView.setAdapter(alarmAdapter);
       binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));

       // Log.d("AA", "addItem: ");

    }

    private void updateProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(progressStatus<100){
                    progressStatus += 5;    // progress status will increase by percentage
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressBar.setProgress(progressStatus);
                        }
                    });

                    try {
                        Thread.sleep(200);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(new Date());
    }
}