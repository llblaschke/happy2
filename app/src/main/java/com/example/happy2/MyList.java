package com.example.happy2;

import android.util.Log;


public class MyList {
    private String[][] list;
    private String listType;
    private int dimTwo;

    public MyList() {}

    public MyList(String lt) {
        listType = lt;
        if(listType.equals("Happy")) {
            dimTwo = 4; // What, With, Where, Date
            list = new String[][]{{"Telefonieren", "Telefonieren", "Flügelschlag", "Flügelschlag", "Flügelschlag",
                    "Telefonieren", "Programmieren", "Lesen", "Telefonieren", "Telefonieren", "Telefonieren", "Programmieren"},

                    {"Manu",  "Anna", "Dominik", "Dominik", "Dominik", "Manu", "allein", "allein", "Manu", "Manu", "Anna", "allein"},

                    {"Draußen", "Draußen", "Online", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Daheim", "Draußen", "Daheim"},

                    {"37234234", "1237849", "139247", "23417", "1837", "194343", "140192", "11028374", "131243", "18273", "172840", "3982839"}};

        }else if(listType.equals("Idea")) {
            dimTwo = 2; // Idea, Description
            list = new String[][]{{"Telefonieren", "Flügelschlag", "Programmieren", "Lesen"},
                    {"zB mit Anna, Manu, Lukas, Mama, Mara, Jürgen oder anderen Leuten",  "mit Dominik übers Internet", "", ""}};

        }else {
            dimTwo = 0;
            Log.d("lana", "myList nColumns not definable!");
        }
    }



    public String[][] getList() { return list; }
    public int length(){ return list[0].length;}

}
