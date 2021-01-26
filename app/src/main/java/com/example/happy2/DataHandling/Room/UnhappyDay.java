package com.example.happy2.DataHandling.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "unhappy_days_table")
public class UnhappyDay {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String unhappyDay;

    public UnhappyDay(String unhappyDay) {
        this.unhappyDay = unhappyDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUnhappyDay() {
        return unhappyDay;
    }
}
