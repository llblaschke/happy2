package com.example.happy2.DataHandling;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.happy2.DataHandling.Room.UnhappyDay;
import com.example.happy2.DataHandling.Room.UnhappyDayDao;
import com.example.happy2.DataHandling.Room.UnhappyDayDatabase;

import java.util.List;

public class UnhappyDayRepository {

    private UnhappyDayDao unhappyDayDao;
    private LiveData<List<UnhappyDay>> allUnhappyDays;
    private LiveData<List<String>> allUnhappyDaysDates;

    public UnhappyDayRepository(Application application){
        UnhappyDayDatabase database = UnhappyDayDatabase.getInstance(application);
        unhappyDayDao = database.unhappyDayDao();
        allUnhappyDays = unhappyDayDao.getAll();
        allUnhappyDaysDates = unhappyDayDao.getAllDates();
    }

    public void insert(UnhappyDay unhappyDay){
        new InsertUnhappyDayAsyncTask(unhappyDayDao).execute(unhappyDay);
    }

    public LiveData<List<UnhappyDay>> getAllUnhappyDays(){
        return allUnhappyDays;
    }

    public LiveData<List<String>> getAllUnhappyDaysDates() {
        return allUnhappyDaysDates;
    }

    private static class InsertUnhappyDayAsyncTask extends AsyncTask<UnhappyDay, Void, Void> {
        private UnhappyDayDao unhappyDayDao;
        private InsertUnhappyDayAsyncTask(UnhappyDayDao unhappyDayDao) {
            this.unhappyDayDao = unhappyDayDao;
        }
        @Override
        protected Void doInBackground(UnhappyDay... unhappyDays) {
            unhappyDayDao.insert(unhappyDays[0]);
            return null;
        }
    }

}
