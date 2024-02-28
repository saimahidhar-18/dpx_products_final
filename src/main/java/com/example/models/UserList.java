package com.example.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserList{
    private long id;
    private String username;
    private String stage;
    private String lastviewed;

    public UserList(){

    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserList(long id, String username, String stage) {
        this.id = id;
        this.username = username;
        this.stage = stage;
        this.lastviewed = "28/02/2024";
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getLastviewed() {
        return lastviewed;
    }

    public void setLastviewed() {
        Date curr = new Date();
        this.lastviewed = (String)(new SimpleDateFormat("dd/mm/yyyy").format(curr)); 
    }

    public UserList(String userName) {
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
