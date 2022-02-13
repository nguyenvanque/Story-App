package com.example.apptruyen.model;

import java.util.ArrayList;

public class ListGioHang {


    public String message;
    public boolean status;
    private ArrayList<GioHang> giohang;

    public ListGioHang() {
    }

    public ArrayList<GioHang> getGiohang() {
        return giohang;
    }

    public void setGiohang(ArrayList<GioHang> giohang) {
        this.giohang = giohang;
    }

}

