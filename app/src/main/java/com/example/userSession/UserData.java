package com.example.userSession;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserData {
 SharedPreferences mSharedPreferences;
 SharedPreferences.Editor editor;
 Context context;
 public static final String FILE_NAME = "ReminderApp";
 public static final String GENDER_KEY = "gender";
 public static final String WEIGHT_KEY = "weight";
 public static final String WAKEUP_TIME = "wakeupTime";
 public static final String BED_TIME = "bedTime";
 public static final String STATUS = "status";

    public UserData(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void saveData(String gender,String weight,String bedTime, String wakeupTime,boolean status){
        editor.putString(GENDER_KEY,gender);
        editor.putString(WEIGHT_KEY,weight);
        editor.putString(BED_TIME,bedTime);
        editor.putString(WAKEUP_TIME,wakeupTime);
        editor.putBoolean(STATUS,status);
        editor.apply();
    }

    public HashMap<String, String> getUserData(){
        HashMap<String , String> user = new HashMap<>();
        user.put(GENDER_KEY,mSharedPreferences.getString(GENDER_KEY,null));
        user.put(WEIGHT_KEY,mSharedPreferences.getString(WEIGHT_KEY,null));
        user.put(BED_TIME,mSharedPreferences.getString(BED_TIME,null));
        user.put(WAKEUP_TIME,mSharedPreferences.getString(WAKEUP_TIME,null));
        return user;
    }

    public boolean isRegistered(){
        return mSharedPreferences.getBoolean(STATUS,false);
    }
}
