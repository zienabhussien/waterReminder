package com.example.adapter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_name")
public class Item {

    @PrimaryKey(autoGenerate = true)
   private int id;
   private String currentTime;
   private int cupSize;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getCupSize() {
        return cupSize;
    }

    public void setCupSize(int cupSize) {
        this.cupSize = cupSize;
    }
}
