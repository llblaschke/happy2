package com.example.happy2;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.example.happy2.R.string.empty_list;

@Database(entities = {Idea.class}, version = 1)
public abstract class IdeaDatabase extends RoomDatabase {

    private static IdeaDatabase instance;
    public abstract IdeaDao ideaDao();
    private static String emptyList = Resources.getSystem().getString(empty_list);

    public static synchronized IdeaDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    IdeaDatabase.class,
                    "idea_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private IdeaDao ideaDao;
        private PopulateDbAsyncTask(IdeaDatabase db) {
            ideaDao = db.ideaDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ideaDao.insert(new Idea(emptyList, ""));
            return null;
        }
    }
}
