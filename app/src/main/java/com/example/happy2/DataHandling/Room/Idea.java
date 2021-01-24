package com.example.happy2.DataHandling.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "idea_table")
public class Idea {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String what;
    private String adInfo;

    public Idea(String what, String adInfo) {
        this.what = what;
        this.adInfo = adInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWhat() {
        return what;
    }

    public String getAdInfo() {
        return adInfo;
    }
}
