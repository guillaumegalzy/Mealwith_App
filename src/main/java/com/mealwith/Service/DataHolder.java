package com.mealwith.Service;

import java.util.ArrayList;

public final class DataHolder {

    private final static DataHolder INSTANCE = new DataHolder();

    private DataHolder(){};

    private ArrayList<Object> list = new ArrayList<>();

     public static DataHolder getINSTANCE(){
        return INSTANCE;
    }

    public void setList(ArrayList<Object> list){
         this.list = list;
    }

    public ArrayList<Object> getList(){
         return list;
    }
}
