package com.example.happy2.DataHandling.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UnhappyDay.class}, version = 1)
public abstract class UnhappyDayDatabase extends RoomDatabase {

    private static UnhappyDayDatabase instance;
    public abstract UnhappyDayDao unhappyDayDao();

    public static synchronized UnhappyDayDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    UnhappyDayDatabase.class,
                    "unhappy_days_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
