package com.example.apptruyen.model;

public class Truyen {
    int matruyen;
    String tentruyen;
    String hinhtruyen;
    String mota;
    String gia;
    String luottruycap;
    int maloai;

    public Truyen() {
    }

    public Truyen(int matruyen, String tentruyen, String hinhtruyen, String mota, String gia, int maloai) {
        this.matruyen = matruyen;
        this.tentruyen = tentruyen;
        this.hinhtruyen = hinhtruyen;
        this.mota = mota;
        this.gia = gia;
        this.maloai = maloai;
    }

    public int getMatruyen() {
        return matruyen;
    }

    public void setMatruyen(int matruyen) {
        this.matruyen = matruyen;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getHinhtruyen() {
        return hinhtruyen;
    }

    public void setHinhtruyen(String hinhtruyen) {
        this.hinhtruyen = hinhtruyen;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getLuottruycap() {
        return luottruycap;
    }

    public void setLuottruycap(String luottruycap) {
        this.luottruycap = luottruycap;
    }
}
