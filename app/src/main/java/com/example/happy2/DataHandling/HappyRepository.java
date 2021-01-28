package com.example.happy2.DataHandling;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.happy2.DataHandling.Room.HappyDao;
import com.example.happy2.DataHandling.Room.HappyDatabase;
import com.example.happy2.DataHandling.Room.HappyThing;

import java.util.Arrays;
import java.util.List;

public class HappyRepository {

    private List<String> column_names = Arrays.asList("mWhat", "mWith", "mWhere", "mWhen", "mPicPath");
    private HappyDao happyDao;
    private LiveData<List<HappyThing>> allHappyThings;
    private LiveData<List<String>> allHappyWhat;
    private LiveData<List<String>> allHappyWith;
    private LiveData<List<String>> allHappyWhere;
    private LiveData<List<String>> allHappyAdInfo;

    public HappyRepository(Application application){
        HappyDatabase database = HappyDatabase.getInstance(application);
        happyDao = database.happyDao();
        allHappyThings = happyDao.getAll();
        allHappyWhat = happyDao.getAllWhat();
        allHappyWith = happyDao.getAllWith();
        allHappyWhere = happyDao.getAllWhere();
        allHappyAdInfo = happyDao.getAllAdInfo();
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

    public LiveData<List<String>> getAllHappyWhat() {
        return allHappyWhat;
    }

    public LiveData<List<String>> getAllHappyWith() {
        return allHappyWith;
    }

    public LiveData<List<String>> getAllHappyWhere() {
        return allHappyWhere;
    }

    public LiveData<List<String>> getAllHappyAdInfo() {
        return allHappyAdInfo;
    }

    public LiveData<List<HappyThing>> getAllHappyThingsWhereXis(int x, String xIs) {
        SimpleSQLiteQuery query = getAllHappyTHingsWhereXisQuery(x, xIs);
        return happyDao.getAllWhereXis(query);
    }

    public LiveData<List<String>> getDistinctX(int x) {
        SimpleSQLiteQuery query = getDistinctXQuery(x);
        return happyDao.getDistinctX(query);
    }

    public LiveData<List<String>> getXwhereYis(int x, int y, String yIs){
        SimpleSQLiteQuery query = getXwhereYisQuery(x, y, yIs);
        return happyDao.getXwhereYis(query);
    }

    private SimpleSQLiteQuery getAllHappyTHingsWhereXisQuery(int x, String xIs) {
        String queryString =
                "SELECT * FROM happy_table WHERE "
                        + column_names.get(x)
                        + " LIKE '"
                        + xIs
                        + "';";
        return new SimpleSQLiteQuery(queryString);
    }

    private SimpleSQLiteQuery getXwhereYisQuery(int x, int y, String yIs) {
        String queryString =
                "SELECT "
                        + column_names.get(x)
                        + " FROM happy_table WHERE "
                        + column_names.get(y)
                        + " LIKE '"
                        + yIs
                        + "';";
        return new SimpleSQLiteQuery(queryString);
    }


    private SimpleSQLiteQuery getDistinctXQuery(int x) {
        // Beginning of query string
        String queryString =
                "SELECT DISTINCT "
                        + column_names.get(x)
                        + " FROM happy_table;";
        return new SimpleSQLiteQuery(queryString);
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
