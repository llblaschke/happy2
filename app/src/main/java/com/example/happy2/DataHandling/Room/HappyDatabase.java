package com.example.happy2.DataHandling.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HappyThing.class}, version = 1)
public abstract class HappyDatabase extends RoomDatabase {

    private static HappyDatabase instance;
    public abstract HappyDao happyDao();


    public static synchronized HappyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    HappyDatabase.class,
                    "happy_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
}
