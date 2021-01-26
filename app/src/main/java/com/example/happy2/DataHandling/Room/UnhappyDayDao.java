package com.example.happy2.DataHandling.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UnhappyDayDao {

    @Insert
    void insert(UnhappyDay unhappyDay);

    @Query("SELECT * FROM unhappy_days_table")
    LiveData<List<UnhappyDay>> getAll();
}
