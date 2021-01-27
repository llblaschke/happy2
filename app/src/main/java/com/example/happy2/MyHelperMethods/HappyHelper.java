package com.example.happy2.MyHelperMethods;

import com.example.happy2.DataHandling.Room.HappyThing;

import java.util.List;

public class HappyHelper {

    private int title;
    private int description;
    private List<HappyThing> happyList;
    private List<String> titleList;
    private List<String> descriptionList;

    public HappyHelper(List<HappyThing> happyList, int title, int description) {
        this.happyList = happyList;
        this.title = title;
        this.description = description;
    }


}
