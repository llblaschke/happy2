package com.example.happy2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HappyThing.class}, version = 1)
public abstract class HappyDatabase extends RoomDatabase {

    private static HappyDatabase instance;
    public abstract HappyDao happyDao();

    public static synchronized HappyDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    HappyDatabase.class,
                    "happy_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
