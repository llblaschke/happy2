package com.example.happy2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Idea.class}, version = 1)
public abstract class IdeaDatabase extends RoomDatabase {

    private static IdeaDatabase instance;
    public abstract IdeaDao ideaDao();

    public static synchronized IdeaDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    IdeaDatabase.class,
                    "idea_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
