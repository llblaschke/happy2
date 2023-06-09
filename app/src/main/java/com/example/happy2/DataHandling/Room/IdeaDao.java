package com.example.happy2.DataHandling.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IdeaDao {

    @Insert
    void insert(Idea idea);

    @Update
    void update(Idea idea);

    @Delete
    void delete(Idea idea);

    @Query("DELETE FROM idea_table")
    void deleteAllIdeas();

    @Query("SELECT * FROM idea_table")
    LiveData<List<Idea>> getAll();

    @Query("SELECT what FROM idea_table")
    LiveData<List<String>> getAllWhat();

    @Query("SELECT adInfo FROM idea_table")
    LiveData<List<String>> getAllDesc();
}
