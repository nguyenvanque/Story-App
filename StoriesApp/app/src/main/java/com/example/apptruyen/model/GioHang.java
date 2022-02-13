package com.example.apptruyen.model;

public class GioHang {
    private String id;
    private int matruyen;
    private String tentruyen;
    private String hinhtruyen;
    private long gia;
    private String email;
    private String name;
    private String timeoder;
    private int soluong;

    public GioHang() {
    }

    public GioHang(String id, int matruyen, String tentruyen, String hinhtruyen, long gia, String email, String name, String timeoder, int soluong) {
        this.id = id;
        this.matruyen = matruyen;
        this.tentruyen = tentruyen;
        this.hinhtruyen = hinhtruyen;
        this.gia = gia;
        this.email = email;
        this.name = name;
        this.timeoder = timeoder;
        this.soluong = soluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeoder() {
        return timeoder;
    }

    public void setTimeoder(String timeoder) {
        this.timeoder = timeoder;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
