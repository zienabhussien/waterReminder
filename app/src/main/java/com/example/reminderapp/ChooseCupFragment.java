package com.example.reminderapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reminderapp.databinding.FragmentChooseCupBinding;

public class ChooseCupFragment extends Fragment {
 FragmentChooseCupBinding binding;
    public ChooseCupFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      binding = FragmentChooseCupBinding.inflate(getLayoutInflater());
      return binding.getRoot();
    }
}