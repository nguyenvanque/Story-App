package com.example.apptruyen.model;

import java.util.ArrayList;

public class ListTruyen {
    public boolean status;
    public String message;
    ArrayList<Truyen> truyen;

    public ArrayList<Truyen> getTruyen() {
        return truyen;
    }

    public void setTruyen(ArrayList<Truyen> truyen) {
        this.truyen = truyen;
    }
}
