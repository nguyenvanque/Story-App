package com.example.apptruyen.model;

import java.util.ArrayList;

public class ListUser {

    public String message;
    public boolean status;
    ArrayList<Users> users;

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
}
