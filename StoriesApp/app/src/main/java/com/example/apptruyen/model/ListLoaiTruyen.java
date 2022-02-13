package com.example.apptruyen.model;

import java.util.ArrayList;

public class ListLoaiTruyen {
    public String message;
    public boolean status;
    private ArrayList<LoaiTruyen> loaitruyen;

    public ListLoaiTruyen() {
    }

    public ArrayList<LoaiTruyen> getLoaitruyen() {
        return loaitruyen;
    }

    public void setLoaitruyen(ArrayList<LoaiTruyen> loaitruyen) {
        this.loaitruyen = loaitruyen;
    }
}
