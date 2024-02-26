package com.example.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.models.Product;
import com.example.models.UserList;
import com.example.services.CredentialServices;
import com.example.services.UserListServices;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class UserListResource {

    UserListServices userListServices = new UserListServices();

    
    @GET
    public Product getUsers(@PathParam("productid") long productId){
        return userListServices.getUsers(productId);
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void addUser(@PathParam("productid") long productid, String newUser){
        userListServices.addUser(productid, newUser);
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public List<UserList> deleteUser(@PathParam("productid") long productid, String userName){
        return userListServices.deleteUser(productid, userName);
    }


    
}
