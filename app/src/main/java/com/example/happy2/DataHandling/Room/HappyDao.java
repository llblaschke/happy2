package com.example.happy2.DataHandling.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

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

    @Query("SELECT * FROM happy_table ORDER BY mWhen DESC")
    LiveData<List<HappyThing>> getAll();

    @Query("SELECT mWhat FROM happy_table")
    LiveData<List<String>> getAllWhat();

    @Query("SELECT mWith FROM happy_table")
    LiveData<List<String>> getAllWith();

    @Query("SELECT mWhere FROM happy_table")
    LiveData<List<String>> getAllWhere();

    @Query("SELECT mAdInfo FROM happy_table")
    LiveData<List<String>> getAllAdInfo();

    @RawQuery(observedEntities = HappyThing.class)
    LiveData<List<HappyThing>> getAllWhereXis(SupportSQLiteQuery query);

    @RawQuery(observedEntities = HappyThing.class)
    LiveData<List<String>> getXwhereYis(SupportSQLiteQuery query);

    @RawQuery(observedEntities = HappyThing.class)
    LiveData<List<String>> getDistinctX(SupportSQLiteQuery query);
}
