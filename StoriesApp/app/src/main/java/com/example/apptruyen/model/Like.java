package com.example.apptruyen.model;

public class Like {
    private String idlike;
    private String email;
    private String time;
    private String matruyen;

    public Like() {
    }

    public Like(String idlike, String email, String time, String matruyen) {
        this.idlike = idlike;
        this.email = email;
        this.time = time;
        this.matruyen = matruyen;
    }

    public String getIdlike() {
        return idlike;
    }

    public void setIdlike(String idlike) {
        this.idlike = idlike;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatruyen() {
        return matruyen;
    }

    public void setMatruyen(String matruyen) {
        this.matruyen = matruyen;
    }
}
