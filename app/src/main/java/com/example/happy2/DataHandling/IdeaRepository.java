package com.example.happy2.DataHandling;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.happy2.DataHandling.Room.Idea;
import com.example.happy2.DataHandling.Room.IdeaDao;
import com.example.happy2.DataHandling.Room.IdeaDatabase;

import java.util.List;

public class IdeaRepository {

    private IdeaDao ideaDao;
    private LiveData<List<Idea>> allIdeas;

    public IdeaRepository(Application application){
        IdeaDatabase database = IdeaDatabase.getInstance(application);
        ideaDao = database.ideaDao();
        allIdeas = ideaDao.getAll();
    }


    public void insert(Idea idea){
        new InsertIdeaAsyncTask(ideaDao).execute(idea);
    }

    public void update(Idea idea){
        new UpdateIdeaAsyncTask(ideaDao).execute(idea);
    }

    public void delete(Idea idea){
        new DeleteIdeaAsyncTask(ideaDao).execute(idea);
    }

    public void deleteAllIdeas(){
        new DeleteAllIdeasAsyncTask(ideaDao).execute();
    }

    public LiveData<List<Idea>> getAllIdeas(){
        return allIdeas;
    }


    private static class InsertIdeaAsyncTask extends AsyncTask<Idea, Void, Void> {
        private IdeaDao ideaDao;
        private InsertIdeaAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }
        @Override
        protected Void doInBackground(Idea... ideas) {
            ideaDao.insert(ideas[0]);
            return null;
        }
    }

    private static class UpdateIdeaAsyncTask extends AsyncTask<Idea, Void, Void> {
        private IdeaDao ideaDao;

        private UpdateIdeaAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }

        @Override
        protected Void doInBackground(Idea... ideas) {
            ideaDao.update(ideas[0]);
            return null;
        }
    }


    private static class DeleteIdeaAsyncTask extends AsyncTask<Idea, Void, Void> {
        private IdeaDao ideaDao;
        private DeleteIdeaAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }
        @Override
        protected Void doInBackground(Idea... ideas) {
            ideaDao.delete(ideas[0]);
            return null;
        }
    }


    private static class DeleteAllIdeasAsyncTask extends AsyncTask<Void, Void, Void> {
        private IdeaDao ideaDao;
        private DeleteAllIdeasAsyncTask(IdeaDao ideaDao) {
            this.ideaDao = ideaDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ideaDao.deleteAllIdeas();
            return null;
        }
    }
}
