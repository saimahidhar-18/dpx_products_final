package com.example.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserList{
    private long id;
    private String userName;
    private String stage;

    public UserList(){

    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserList(long id, String userName, String stage) {
        this.id = id;
        this.userName = userName;
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    // public String getLastviewed() {
    //     return lastviewed;
    // }

    // public void setLastviewed() {
    //     Date curr = new Date();
    //     this.lastviewed = (String)(new SimpleDateFormat("dd/mm/yyyy").format(curr)); 
    // }

    public UserList(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    
}
