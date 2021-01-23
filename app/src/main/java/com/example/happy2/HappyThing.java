package com.example.happy2;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "happy_table")
public class HappyThing {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String what;
    private String with;
    private String where;
    private String adInfo;
    private String when;
    @Ignore
    private String picPath;

    public HappyThing(String what, String with, String where, String adInfo, String when) {
        this.what = what;
        this.with = with;
        this.where = where;
        this.adInfo = adInfo;
        this.when = when;
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

    public String getWith() {
        return with;
    }

    public String getWhere() {
        return where;
    }

    public String getAdInfo() {
        return adInfo;
    }

    public String getWhen() {
        return when;
    }

    public String getPicPath() {
        return picPath;
    }
}
