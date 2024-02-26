package com.example.models;

public class UserList{
    private String userName;

    public UserList(){

    }
    
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
