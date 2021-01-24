package com.example.happy2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HappyRepository {

    private HappyDao happyDao;
    private LiveData<List<HappyThing>> allHappyThings;

    public HappyRepository(Application application){
        HappyDatabase database = HappyDatabase.getInstance(application);
        happyDao = database.happyDao();
        allHappyThings = happyDao.getAll();
    }


    public void insert(HappyThing happyThing){
        new InsertHappyThingAsyncTask(happyDao).execute(happyThing);
    }

    public void update(HappyThing happyThing){
        new UpdateHappyThingAsyncTask(happyDao).execute(happyThing);
    }

    public void delete(HappyThing happyThing){
        new DeleteHappyThingAsyncTask(happyDao).execute(happyThing);
    }

    public void deleteAllHappyThings(){
        new DeleteAllHappyThingsAsyncTask(happyDao).execute();
    }

    public LiveData<List<HappyThing>> getAllHappyThings(){
        return allHappyThings;
    }


    private static class InsertHappyThingAsyncTask extends AsyncTask<HappyThing, Void, Void> {
        private HappyDao happyDao;
        private InsertHappyThingAsyncTask(HappyDao happyDao) {
            this.happyDao = happyDao;
        }
        @Override
        protected Void doInBackground(HappyThing... happyThings) {
            happyDao.insert(happyThings[0]);
            return null;
        }
    }

    private static class UpdateHappyThingAsyncTask extends AsyncTask<HappyThing, Void, Void> {
        private HappyDao happyDao;
        private UpdateHappyThingAsyncTask(HappyDao happyDao) {
            this.happyDao = happyDao;
        }
        @Override
        protected Void doInBackground(HappyThing... happyThings) {
            happyDao.update(happyThings[0]);
            return null;
        }
    }


    private static class DeleteHappyThingAsyncTask extends AsyncTask<HappyThing, Void, Void> {
        private HappyDao happyDao;
        private DeleteHappyThingAsyncTask(HappyDao happyDao) {
            this.happyDao = happyDao;
        }
        @Override
        protected Void doInBackground(HappyThing... happyThings) {
            happyDao.delete(happyThings[0]);
            return null;
        }
    }


    private static class DeleteAllHappyThingsAsyncTask extends AsyncTask<Void, Void, Void> {
        private HappyDao happyDao;
        private DeleteAllHappyThingsAsyncTask(HappyDao happyDao) {
            this.happyDao = happyDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            happyDao.deleteAllHappyThings();
            return null;
        }
    }
}
