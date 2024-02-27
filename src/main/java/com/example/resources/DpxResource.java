package com.example.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import com.example.Filters.CredentialFilter;
import com.example.models.Product;
import com.example.services.CredentialServices;
import com.example.services.DpxServices;
import com.mongodb.MongoException;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Path("/data_products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DpxResource {


    DpxServices dpxservice1=new DpxServices();


    @GET
    public Response getProducts(@HeaderParam("Username") String username){
        try{
            List<Product> products = dpxservice1.getAllProducts(username);
            if(products.isEmpty()){
                Responsebody responsebody= new Responsebody();
                responsebody.setCode("204 No content");
                responsebody.setMessage("products collection is empty");
                return Response.status(Response.Status.NOT_FOUND).entity(responsebody).build();
            }
            else
                return Response.ok(products).build();
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error getting the products.").build();
        }
        
    }

    @GET
    @Path("/drafts")
    public Response getDraftProducts(@HeaderParam("Username") String username){
        try{
            List<Product> products = dpxservice1.getDraftProducts(username);
            if(products.size()==0)
                return Response.ok("products collection is empty").build();
            else
                return Response.ok(products).build();
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error getting the products.").build();
        }
        
    }
    
    @POST
   // @RolesAllowed("producer")
    public Response addProduct(Product product){
        try{
            Product p = dpxservice1.addProduct(product);
            return Response.ok(p).build();
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting the product.").build();
        }
    }


    @GET
    @Path("/{productid}")
    public Response getProduct(@PathParam("productid") long id){
        try{
            Product product = dpxservice1.getProduct(id);
            if (product != null) 
                return Response.ok(product).build();
            else {
                Responsebody responsebody= new Responsebody();
                responsebody.setCode("404 Not Found");
                responsebody.setMessage("The Product id is invalid!");
                return Response.status(Response.Status.NOT_FOUND).entity(responsebody).build();
            }
            
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error in adding the product.").build();
        }
    }

    @PUT
    @Path("/{productId}")
    public Response updateProduct (@PathParam("productId") long id, Product product) {
        product.setId(id);
        try{
            UpdateResult result = dpxservice1.updateProduct(product);
            if(result.wasAcknowledged()){
                if(result.getMatchedCount()==0){
                    Responsebody responsebody= new Responsebody();
                    responsebody.setCode("404 Not Found");
                    responsebody.setMessage("The Product id is invalid!");
                    return Response.status(Response.Status.NOT_FOUND).entity(responsebody).build();
                }
                   // return Response.status(Response.Status.NOT_FOUND).entity("The Product id is invalid!").build();
                else 
                        return Response.ok(product).build();
                }
            else{
                Responsebody responsebody= new Responsebody();
                responsebody.setCode("503 Service Unavailable");
                responsebody.setMessage("Server couldn't acknowledge the update operation.");
                return Response.status(Response.Status.NOT_MODIFIED).entity(responsebody).build();
            }
        }
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updatinging the product.").build();
        }
    }

    
    @DELETE
    @Path("/{productId}")
    public Response deleteProduct(@PathParam("productId") long id) {
        try {
            DeleteResult result = dpxservice1.deleteProduct(id);
            if (result==null) {
                Responsebody responsebody= new Responsebody();
                responsebody.setCode("404 Not found");
                responsebody.setMessage("The product id is invalid");
                return Response.status(Response.Status.NOT_FOUND).entity(responsebody).build();
            }           
            else {
                Responsebody responsebody= new Responsebody();
                responsebody.setCode("200 OK");
                responsebody.setMessage("Deletion Successful");
                return Response.ok(responsebody).build();  

            }     
        } 
        catch (MongoException e) {
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting the product.").build();
        }

    }

    @Path("/{productid}/datalist")
    public DataListResource getDataList(){
        return new DataListResource();
    }

    @Path("/{productid}/userlist")
    public UserListResource getUserList(){
        return new UserListResource();
    }

    @GET
    @Path("/{productid}/publish")
    public String publishProduct(@PathParam("productid") long id){
        dpxservice1.publishProduct(id);
        return id + "Successfull!!!!";
    }


}