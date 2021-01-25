package com.example.happy2.DataHandling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.happy2.DataHandling.Room.HappyThing;

import java.util.List;

public class HappyViewModel extends AndroidViewModel {

    private HappyRepository repository;
    private LiveData<List<HappyThing>> allHappyThings;
    private LiveData<List<String>> allHappyWhat;
    private LiveData<List<String>> allHappyWith;
    private LiveData<List<String>> allHappyWhere;
    private LiveData<List<String>> allHappyAdInfo;

    public HappyViewModel(@NonNull Application application) {
        super(application);
        repository = new HappyRepository(application);
        allHappyThings = repository.getAllHappyThings();
        allHappyWhat = repository.getAllHappyWhat();
        allHappyWith = repository.getAllHappyWith();
        allHappyWhere = repository.getAllHappyWhere();
        allHappyAdInfo = repository.getAllHappyAdInfo();
    }

    public void insert(HappyThing happyThing){
        repository.insert(happyThing);
    }

    public void update(HappyThing happyThing){
        repository.update(happyThing);
    }

    public void delete(HappyThing happyThing){
        repository.delete(happyThing);
    }

    public void deleteAllHappyThings(){
        repository.deleteAllHappyThings();
    }

    public LiveData<List<HappyThing>> getAllHappyThings() {
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
}
