package com.example.happy2;

import android.util.Log;


public class MyList {
    private String[][] list;
    private String listType;
    private int dimTwo;

    public MyList() {}

    public MyList(String lt) {
        dimTwo =11;
        listType = lt;
        this.listType = listType;
        if(listType == "Happy") {
            dimTwo = 4; // What, With, Where, Date
            list = new String[][]{{"Telefonieren", "Telefonieren", "Flügelschlag", "Flügelschlag", "Flügelschlag",
                    "Telefonieren", "Programmieren", "Lesen", "Telefonieren", "Telefonieren", "Telefonieren", "Programmieren"},

                    {"Manu",  "Anna", "Dominik", "Dominik", "Dominik", "Manu", "allein", "allein", "Manu", "Manu", "Anna", "allein"},

                    {"Draußen", "Draußen", "Online", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Draußen", "Daheim"},

                    {"37234234", "1237849", "139247", "23417", "1837", "194343", "140192", "11028374", "131243", "18273", "172840", "3982839"}};

        }else if(listType == "Idea") {
            dimTwo = 3; // What, With, Where
            list = new String[][]{{"Telefonieren", "Telefonieren", "Flügelschlag", "Flügelschlag", "Flügelschlag",
                    "Telefonieren", "Programmieren", "Lesen", "Telefonieren", "Telefonieren", "Telefonieren", "Programmieren"},

                    {"Manu",  "Anna", "Dominik", "Dominik", "Dominik", "Manu", "allein", "allein", "Manu", "Manu", "Anna", "allein"},

                    {"Draußen", "Draußen", "Online", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Draußen", "Daheim"}};

        }else {
            dimTwo = 0;
            Log.d("lana", "myList nColumns not definable!");
        }
    };



    public String[][] getList() { return list; }
    public int length(){ return list[0].length;}

}
