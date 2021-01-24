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

    public HappyViewModel(@NonNull Application application) {
        super(application);
        repository = new HappyRepository(application);
        allHappyThings = repository.getAllHappyThings();
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
}
