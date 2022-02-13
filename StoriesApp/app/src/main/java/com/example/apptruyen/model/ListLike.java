package com.example.apptruyen.model;

import java.util.ArrayList;

public class ListLike {
    public boolean status;
    public String message;
    ArrayList<Like> liketruyen;

    public ArrayList<Like> getLiketruyen() {
        return liketruyen;
    }

    public void setLiketruyen(ArrayList<Like> liketruyen) {
        this.liketruyen = liketruyen;
    }
}

