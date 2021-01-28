package com.example.happy2.DataHandling.Room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "happy_table")
public class HappyThing {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String mWhat;
    private String mWith;
    private String mWhere;
    private String mAdInfo;
    private String mWhen;
    @Ignore
    private String mPicPath;

    public HappyThing(String what, String with, String where, String adInfo, String when) {
        this.mWhat = what;
        this.mWith = with;
        this.mWhere = where;
        this.mAdInfo = adInfo;
        this.mWhen = when;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWhat() {
        return mWhat;
    }

    public String getWith() {
        return mWith;
    }

    public String getWhere() {
        return mWhere;
    }

    public String getAdInfo() {
        return mAdInfo;
    }

    public String getWhen() {
        return mWhen;
    }

    public String getX(int x) {
        switch (x) {
            case 0: return mWhat;
            case 1: return mWith;
            case 2: return mWhere;
            case 3: return mWhen;
            case 4: return mAdInfo;
            default: return mPicPath;
        }
    }

    public String getPicPath() {
        return mPicPath;
    }
}
