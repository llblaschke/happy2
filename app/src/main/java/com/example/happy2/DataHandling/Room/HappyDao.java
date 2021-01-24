package com.example.happy2.DataHandling.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HappyDao {

    @Insert
    void insert(HappyThing happyThing);

    @Update
    void update(HappyThing happyThing);

    @Delete
    void delete(HappyThing happyThing);

    @Query("DELETE FROM happy_table")
    void deleteAllHappyThings();

    @Query("SELECT * FROM happy_table ORDER BY what DESC")
    LiveData<List<HappyThing>> getAll();
}
