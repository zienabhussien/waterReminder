package com.example.reminderapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.adapter.Item;

import java.util.List;

@Dao
public interface ItemDao {

     @Insert
      void insert(Item item);

    @Delete
    void delete(Item item);


    @Query("SELECT * FROM table_name")
    List<Item> getAll();


}
