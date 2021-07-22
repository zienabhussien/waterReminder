package com.example.reminderapp.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.adapter.DayLog;

@Database(entities = {DayLog.class},version = 1)
@TypeConverters(Converter.class)
public abstract class RoomDB extends RoomDatabase {
    // create database instance
   private static RoomDB database;
    public abstract ItemDao itemDao();


    private static String  DATABASE_NAME ="database";

  public synchronized static RoomDB getInstance(Context context){
      if(database == null){
          database = Room.databaseBuilder(context.getApplicationContext()
                  ,RoomDB.class,DATABASE_NAME)
                  .allowMainThreadQueries()
                  .fallbackToDestructiveMigration()
                  .build();
      }
      return database;
  }


}
