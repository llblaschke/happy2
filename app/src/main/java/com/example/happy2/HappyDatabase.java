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

@Database(entities = {HappyThing.class}, version = 1)
public abstract class HappyDatabase extends RoomDatabase {

    private static HappyDatabase instance;
    public abstract HappyDao happyDao();
    private static String emptyList = Resources.getSystem().getString(empty_list);


    public static synchronized HappyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    HappyDatabase.class,
                    "happy_database")
                    .fallbackToDestructiveMigration()
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
        private HappyDao happyDao;

        private PopulateDbAsyncTask(HappyDatabase db) {
            happyDao = db.happyDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            happyDao.insert(new HappyThing(emptyList, "", "", "", null));
            return null;
        }
    }
}
