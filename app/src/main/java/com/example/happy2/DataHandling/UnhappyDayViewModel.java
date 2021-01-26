package com.example.happy2.DataHandling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.happy2.DataHandling.Room.UnhappyDay;

import java.util.List;

public class UnhappyDayViewModel extends AndroidViewModel {

    private UnhappyDayRepository repository;
    private LiveData<List<UnhappyDay>> allUnhappyDays;
    private LiveData<List<String>> allUnhappyDaysDates;

    public UnhappyDayViewModel(@NonNull Application application) {
        super(application);
        repository = new UnhappyDayRepository(application);
        allUnhappyDays = repository.getAllUnhappyDays();
        allUnhappyDaysDates = repository.getAllUnhappyDaysDates();
    }

    public void insert(UnhappyDay unhappyDay){
        repository.insert(unhappyDay);
    }

    public LiveData<List<UnhappyDay>> getAllUnhappyDays() {
        return allUnhappyDays;
    }

    public LiveData<List<String>> getAllUnhappyDaysDates() {
        return allUnhappyDaysDates;
    }
}
