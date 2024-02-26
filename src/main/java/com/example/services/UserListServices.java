package com.example.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.example.models.Product;
import com.example.models.UserList;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class UserListServices {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> collection = database.getCollection("products");
        
    public UserListServices(){

    }
  
    public Product getUsers(long productId){
        Document document = collection.find(Filters.eq("id", productId)).first();
        
        if(document!=null){
            long id = document.getLong("id");
            String name = document.getString("name");
            List<Document> doclist = document.getList("users", Document.class);
            List<UserList> userlist = new ArrayList<>();

            for (Document doc : doclist){
                UserList userList = new UserList();
                userList.setUserName(doc.getString("userName"));
                userlist.add(userList);
            }
            Product p = new Product(id, name, userlist);
            return p;
        }
        return new Product();

    }

    public void addUser(long productid, String newUser ){
        Document document = collection.find(Filters.eq("id", productid)).first();

        if (document != null){
            List<Document> doclist = document.getList("users", Document.class);
            if (doclist == null) {
                doclist = new ArrayList<>();
            }

            Document newobj = new Document("userName", newUser);
            doclist.add(newobj);
            collection.updateOne(Filters.eq("id", productid), Updates.set("users", doclist));

        }
    }

    public List<UserList> deleteUser(long productid, String userName){
        //String user = userName.getUserName();
        Document document = collection.find(Filters.eq("id", productid)).first();

        if (document != null){
            List<Document> doclist = document.getList("users", Document.class);
            Iterator<Document> iterator = doclist.iterator();


            List<UserList> userlist = new ArrayList<>();

            while (iterator.hasNext()) {
                Document doc = iterator.next();
                String currentuser = doc.getString("userName");
    
                if (currentuser.equals(userName)) {
                    iterator.remove();
                    collection.updateOne(Filters.eq("id", productid), Updates.set("users", doclist));
                    break;
                }
                else{
                    UserList userList = new UserList();
                    userList.setUserName(doc.getString("userName"));
                    userlist.add(userList);
                }
            }
            return userlist;
        }
        return Collections.emptyList();
    }

}
