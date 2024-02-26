package com.example.models;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
    private long id;
    private String name;
    private String description;
    private String domain;
    private Date date;
    private String status;
    private String producer;
   // private List<String> urls;
    private List<DataList> dataLists;
    private List<UserList> users;

    public Product(){
    }
    
    public Product(long id, String name, String description, String domain, String status,
            String producer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.domain = domain;
        this.date = new Date();
        this.status = status;
       
       // this.urls = urls;
       // this.users= users;
    }

    public Product(long id , String name , String description, String domain, String producer, List<DataList> dataLists){
        this.id = id;
        this.name = name;
        this.description = description;
        this.domain = domain;
        this.producer = producer;
        this.dataLists = dataLists;
       
    }

    public Product(long id, String name,  List<UserList> users){
        this.id = id;
        this.name = name;
        this.users= users;
    }
    
    public String getProducer() {
        return producer;
    }
    public void setProducer(String producer) {
        this.producer = producer;
    }
    // public List<String> getUrls() {
    //     return urls;
    // }
    // public void setUrls(List<String> urls) {
    //     this.urls = urls;
    // }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String Description) {
        this.description = Description;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String Domain) {
        this.domain = Domain;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String Status) {
        this.status = Status;
    }
    public List<UserList> getUsers() {
        return users;
    }
    public void setUsers(List<UserList> users) {
        this.users = users;
    }
    public List<DataList> getDataLists() {
        return dataLists;
    }
    public void setDataLists(List<DataList> dataLists) {
        this.dataLists = dataLists;
    }
}