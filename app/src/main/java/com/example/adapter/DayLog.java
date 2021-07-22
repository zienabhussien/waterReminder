package com.example.adapter;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.reminderapp.room.Util;

import java.util.ArrayList;
import java.util.List;
import static com.example.reminderapp.room.Util.getCurrentDate;

@Entity(tableName = "dayLog")
public class DayLog {

    @PrimaryKey
    @NonNull
    private String date;
    //drink when(Long)  and cupSize(int)
    List<Pair<Long, Integer>> cups;

    public DayLog() {
        this.date = Util.getCurrentDate() ;
        this.cups = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Pair<Long, Integer>> getCups() {
        return cups;
    }

    public void setCups(List<Pair<Long, Integer>> cups) {
        this.cups = cups;
    }

    @Override
    public String toString() {
        return "DayLog{" +
                "date='" + date + '\'' +
                ", cups=" + cups +
                '}';
    }
}
