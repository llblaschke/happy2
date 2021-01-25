package com.example.happy2.DataHandling;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.happy2.DataHandling.Room.Idea;

import java.util.List;

public class IdeaViewModel extends AndroidViewModel {

    private IdeaRepository repository;
    private LiveData<List<Idea>> allIdeas;
    private LiveData<List<String>> allIdeasWhat;
    private LiveData<List<String>> allIdeasDesc;

    public IdeaViewModel(@NonNull Application application) {
        super(application);
        repository = new IdeaRepository(application);
        allIdeas = repository.getAllIdeas();
        allIdeasWhat = repository.getAllIdeasWhat();
        allIdeasDesc = repository.getAllIdeasDesc();
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

    public LiveData<List<String>> getAllIdeasWhat() {
        return allIdeasWhat;
    }

    public LiveData<List<String>> getAllIdeasDesc() {
        return allIdeasDesc;
    }
}
