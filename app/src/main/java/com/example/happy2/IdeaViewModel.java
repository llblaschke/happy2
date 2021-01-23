package com.example.happy2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class IdeaViewModel extends AndroidViewModel {

    private IdeaRepository repository;
    private LiveData<List<Idea>> allIdeas;

    public IdeaViewModel(@NonNull Application application) {
        super(application);
        repository = new IdeaRepository(application);
        allIdeas = repository.getAllIdeas();
    }

    public void insert(Idea idea){
        repository.insert(idea);
    }

    public void update(Idea idea){
        repository.update(idea);
    }

    public void delete(Idea idea){
        repository.delete(idea);
    }

    public void deleteAllIdeas(){
        repository.deleteAllIdeas();
    }

    public LiveData<List<Idea>> getAllIdeas() {
        return allIdeas;
    }
}
