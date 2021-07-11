package com.example.reminderapp.tabFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapter.AlarmAdapter;
import com.example.adapter.Item;
import com.example.reminderapp.ChooseCupFragment;
import com.example.reminderapp.databinding.FragmentHomeBinding;
import com.example.userSession.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
    private int cupSize ;  // taken from selection of any cup
    private long drinkGoal;
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
        userWeight = Integer.parseInt(hashMap.get(UserData.WEIGHT_KEY));
        userGender = hashMap.get(UserData.GENDER_KEY);


        binding.currentTime.setText(getCurrentTime());
        binding.addCupBtn.setOnClickListener(v -> {
            updateProgress();
            addItem();
        });

        cupSize = userData.getCupSize();
        //drinkGoal = (long) ("Male".equals(userGender) ? (userWeight * (0.35)) : (userWeight * (0.31)));
        drinkGoal = 2400;
        percent = (int) (cupSize / drinkGoal) * 100;

        // choose cup
        binding.chooseCup.setOnClickListener( v->{
            ChooseCupFragment fragmentDialog = new ChooseCupFragment();
            fragmentDialog.show(getActivity().getSupportFragmentManager(),"Choose cup size");

        });

        return binding.getRoot();
    }

    private void addItem() {
        dataItem.setCurrentTime(getCurrentTime());
        dataItem.setCupSize(cupSize);
        // add cupSize
        arrayList.add(dataItem);
        alarmAdapter = new AlarmAdapter(arrayList, getActivity());
        binding.recyclerView.setAdapter(alarmAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        // Log.d("AA", "addItem: ");

    }

    private void updateProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (progressStatus < 100) {
                    progressStatus += percent;    // progress status will increase by percentage
                    userData.saveProgressbar_status(progressStatus);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // get saved progressbar status and set it to progressbar
                            binding.progressBar.setProgress(userData.getProgress_status());
                        }
                    });

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
}