package com.example.services;


import javax.ws.rs.HttpMethod;

import org.bson.Document;

//import org.apache.commons.codec.digest.DigestUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.example.models.UserCredentials;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class CredentialServices {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> credCollection = database.getCollection("credentials");

    UserCredentials user0 = new UserCredentials("Ginny",BCrypt.hashpw("pass123", BCrypt.gensalt()),"consumer");

    

    public CredentialServices(){
        if (credCollection.countDocuments() == 0) {
            Document user1 = new Document("username","Harry")
            .append("password", BCrypt.hashpw("pass123", BCrypt.gensalt()))
            .append("role", "producer")
            .append("state", "inactive");

            Document user2 = new Document("username","Ron")
            .append("password", BCrypt.hashpw("pass1234", BCrypt.gensalt()))
            .append("role", "consumer")
            .append("state", "inactive");

            Document user3 = new Document("username","Hermoine")
            .append("password", BCrypt.hashpw("password123", BCrypt.gensalt()))
            .append("role", "consumer")
            .append("state", "inactive");


            credCollection.insertOne(user1);
            credCollection.insertOne(user2);
            credCollection.insertOne(user3);
        }
        
    }

    public boolean isValid(String username, String password){
        FindIterable<Document> documents = credCollection.find(Filters.eq("username", username));
        MongoCursor<Document> cursor = documents.iterator();        
        while (cursor.hasNext()) {
            Document doc = cursor.next();
    
            String hashedPassword = doc.getString("password");
            if (BCrypt.checkpw(password, hashedPassword)) {
                return true; 
            }
        }
        return false;
    }

    public UserCredentials addUser(UserCredentials newUser){
        Document user = new Document("username",newUser.getUsername())
        .append("password", BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()))
        .append("role", newUser.getRole());

        credCollection.insertOne(user);
        return newUser;
    }
    
    public String getUserRole(String username){
        Document document = credCollection.find(Filters.eq("username", username)).first();
        String role = document.getString("role");
        return role;
    }

    public void setState(String username){
        Document document = credCollection.find(Filters.eq("username", username)).first();
        
        if (document != null) {
            credCollection.updateOne(
                Filters.eq("username", username),
                Updates.set("state", "active")
            );
        }
    }

    public boolean isUserAuthorized(String username){
        Document document = credCollection.find(Filters.eq("username", username)).first();
        if (document != null){
            String currentState= document.getString("state");
            return currentState.equals("active");
        }
        return false;
    }

    public boolean isRestrictedMethod(String method) {
        //return true;
        return "POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method);
        // return HttpMethod.POST.equals(method) || HttpMethod.PUT.equals(method) || HttpMethod.DELETE.equals(method);
    }


    public boolean userLogout(){
        // FindIterable<Document> docs = credCollection.find();
        // MongoCursor<Document> cursor = docs.iterator();
        // while(cursor.hasNext()){
        //     Document doc = cursor.next();
        //     if(doc.getString("state").equals("inactive")){
        //         String username = doc.getString("userName");
        //         credCollection.updateOne(Filters.eq("username", username), Updates.set("state", "inactive"));
        //     }
        // }

        FindIterable<Document> docs = credCollection.find();
        MongoCursor<Document> cursor = docs.iterator();
        
        while(cursor.hasNext()) {
            Document doc = cursor.next();
            
            
            String state = doc.getString("state");
            if (state != null && state.equals("active")) {
                String username = doc.getString("username");
                credCollection.updateOne(Filters.eq("username", username), Updates.set("state", "inactive"));
                return true;
            }
        }
        return false;

    }

    
}
