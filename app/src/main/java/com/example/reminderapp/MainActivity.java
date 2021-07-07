package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.reminderapp.databinding.ActivityMainBinding;
import com.example.reminderapp.tabFragments.HomeFragment;
import com.example.reminderapp.tabFragments.SettingFragment;
import com.example.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
 ViewPagerAdapter viewPagerAdapter ;
 ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupTabs();




    }


    private void setupTabs() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new HomeFragment(),"Home");
        viewPagerAdapter.addFragments(new SettingFragment(),"Settings");

        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        binding.tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_settings_24);
    }


}
