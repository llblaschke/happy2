package com.example.happy2;

import android.util.Log;

import java.util.Date;


public class myList {
    private Object[][] list;
    private int dimOne;
    private String listType;
    private int dimTwo;

    public myList() {}

    public myList(String listType) {
        dimTwo =11;
        this.listType = listType;
        if(listType == "Happy") {
            dimOne = 4; // What, With, Where, Date
            list = new Object[][]{{"Programmieren", "allein", "Daheim", 182783},
                    {"Telefonieren", "Manu", "Draußen", 37234234},
                    {"Telefonieren", "Anna", "Draußen", 1237849},
                    {"Flügelschlag", "Dominik", "Online", 139247},
                    {"Flügelschlag", "Dominik", "Daheim", 23417},
                    {"Flügelschlag", "Dominik", "Daheim", 1837},
                    {"Telefonieren", "Manu", "Daheim", 194343},
                    {"Programmieren", "allein", "Daheim", 140192},
                    {"Lesen", "allein", "Daheim", 11028374},
                    {"Telefonieren", "Manu", "Daheim", 131243},
                    {"Telefonieren", "Manu", "Daheim", 18273},
                    {"Telefonieren", "Anna", "Draußen", 172840}};

        }else if(listType == "Idea") {
            dimOne = 3; // What, With, Where
            list = new Object[][]{{"Programmieren", "allein", "Daheim"},
                    {"Telefonieren", "Manu", "Draußen"},
                    {"Telefonieren", "Anna", "Draußen"},
                    {"Flügelschlag", "Dominik", "Online"},
                    {"Flügelschlag", "Dominik", "Daheim"},
                    {"Flügelschlag", "Dominik", "Daheim"},
                    {"Telefonieren", "Manu", "Daheim"},
                    {"Programmieren", "allein", "Daheim"},
                    {"Lesen", "allein", "Daheim"},
                    {"Telefonieren", "Manu", "Daheim"},
                    {"Telefonieren", "Manu", "Daheim"},
                    {"Telefonieren", "Anna", "Draußen"}};
        }else {
            dimOne = 0;
            Log.d("lana", "myList nColumns not definable!");
        }
    };

    public myList(String listType, int listLength){
        dimTwo =listLength;
        this.listType = listType;
        if(listType == "Happy") {
            dimOne = 4; // What, With, Where, Date
        }else if(listType == "Idea") {
            dimOne = 3; // What, With, Where
        }else {
            dimOne = 0;
            Log.d("lana", "myList nColumns not definable!");
        }
        list = new Object[dimOne][dimTwo];
    }

    public Object[][] getList() { return list; }
    public int length(){ return dimTwo;}

}
