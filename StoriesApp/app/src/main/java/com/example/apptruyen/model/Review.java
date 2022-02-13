package com.example.apptruyen.model;

public class Review {
    String idreview;
    String email;
    String name;
    String hinh;
    String time;
    String message;
    String ratenumber;
    String matruyen;

    public Review() {
    }

    public Review(String idreview, String email, String name, String hinh, String time, String message, String matruyen) {
        this.idreview = idreview;
        this.email = email;
        this.name = name;
        this.hinh = hinh;
        this.time = time;
        this.message = message;
        this.matruyen = matruyen;
    }

    public String getIdreview() {
        return idreview;
    }

    public void setIdreview(String idreview) {
        this.idreview = idreview;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMatruyen() {
        return matruyen;
    }

    public void setMatruyen(String matruyen) {
        this.matruyen = matruyen;
    }

    public String getRateNumber() {
        return ratenumber;
    }

    public void setRateNumber(String rateNumber) {
        this.ratenumber = rateNumber;
    }
}
