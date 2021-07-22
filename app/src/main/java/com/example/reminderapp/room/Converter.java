package com.example.reminderapp.room;


import android.util.Pair;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {

    @TypeConverter
    public static List<Pair<Long,Integer>> getFromString(String value){
        Type listType = new TypeToken<List<Pair<Long,Integer>>>() {}.getType();
        return new Gson().fromJson(value, listType);
   }


    @TypeConverter
    public static String fromArrayList(List<Pair<Long,Integer>> list) {
        return new Gson().toJson(list);
    }
}
