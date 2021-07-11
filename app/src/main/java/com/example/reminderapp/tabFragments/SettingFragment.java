package com.example.reminderapp.tabFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.reminderapp.R;
import com.example.reminderapp.databinding.FragmentSettingBinding;
import com.example.userSession.UserData;

import java.util.HashMap;

public class SettingFragment extends Fragment {
   FragmentSettingBinding binding;
   UserData userData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(getLayoutInflater());
        userData = new UserData(getActivity());
        HashMap<String , String> hashMap = userData.getUserData();
        binding.genderSetting.setText(hashMap.get(UserData.GENDER_KEY));
        binding.wakeUpTimeSetting.setText(hashMap.get(UserData.WAKEUP_TIME));
        binding.bedTimeSetting.setText(hashMap.get(UserData.BED_TIME));
        binding.genderWeight.setText(hashMap.get(UserData.WEIGHT_KEY));

        return binding.getRoot();
    }
}