package com.example.happy2;

import android.graphics.Path;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "happy_table")
public class HappyThing {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String what;
    private String with;
    private String where;
    private String adInfo;
    private Date when;
    private Path picPath;

    public HappyThing(String what, String with, String where, String adInfo, Date when) {
        this.what = what;
        this.with = with;
        this.where = where;
        this.adInfo = adInfo;
        this.when = when;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getWhen() {
        return when;
    }

    public Path getPicPath() {
        return picPath;
    }
}
