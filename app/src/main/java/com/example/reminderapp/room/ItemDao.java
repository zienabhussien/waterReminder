package com.example.reminderapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.adapter.DayLog;

import java.util.List;

@Dao
public interface ItemDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DayLog dayLog);

    @Delete
    void delete(DayLog item);


    @Query("SELECT * FROM DayLog")
    List<DayLog> getAll();

    @Query("SELECT * from dayLog WHERE date = :date")
    DayLog getDayLog(String date);
}
