package com.example.reminderapp.main;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.reminderapp.R;
import com.example.reminderapp.databinding.FragmentChooseCupBinding;
import com.example.userSession.UserData;

public class ChooseCupFragment extends DialogFragment {
 FragmentChooseCupBinding binding;
 UserData userData;
  public static int cupSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      binding = FragmentChooseCupBinding.inflate(getLayoutInflater());
      userData = new UserData(getActivity());

       binding.okBtn.setOnClickListener( v->{
           int selectedId = binding.radioGroup.getCheckedRadioButtonId();
          // RadioButton selected_rBtn = binding.getRoot().findViewById(selectedId);
          // String cup = selected_rBtn.getText().toString();
           switch (selectedId){
               case R.id.size_50ml :
                   cupSize = 50;
                 break;
               case R.id.size_100ml :
                   cupSize = 100;
                   break;
               case R.id.size_200ml :
                   cupSize = 200;
                   break;
               case R.id.size_300ml :
                   cupSize = 300;
                   break;
               default:
                   cupSize = 150;
           }
           // save choosen cup size to shared preferences
           userData.saveCupSize(cupSize);
           Toast.makeText(getActivity(),userData.getCupSize()+"ml",Toast.LENGTH_LONG).show();
             dismiss();
       });

      return binding.getRoot();
    }
}